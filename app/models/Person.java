package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.db.ebean.Model;

/**
 * Person model class.<br>
 * Management login user.
 */
@Entity
@Table(name = "persons")
public final class Person extends Model {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * Login name.<br>
	 * Require unique.
	 */
	@Column(unique = true)
	@NotNull
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * User name.
	 */
	@Column(unique = true)
	public String fullName; // SUPPRESS CHECKSTYLE

	/**
	 * Password hash.
	 */
	@Column
	public String passwordHash; // SUPPRESS CHECKSTYLE

	/**
	 * title.<br>
	 * e.g. CEO, manager, officer, etc.
	 */
	@Column
	public String title; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, Person> find = new Model.Finder<Long, Person>( // SUPPRESS CHECKSTYLE
			Long.class, Person.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -6872558365573836112L;

	/**
	 * Build new user.
	 * 
	 * @param person
	 *            New person property
	 * @return New user
	 */
	public static Person build(final Person person) {
		person.save();

		return person;
	}

}

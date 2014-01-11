package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.partversions.PartVersion;
import play.db.ebean.Model;

/**
 * Document file model class.
 */
@Entity
@Table(name = "documents")
public final class Document extends Model {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * Part name.
	 */
	@Column
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * File binary data.
	 */
	@Lob
	public byte[] file; // SUPPRESS CHECKSTYLE

	/**
	 * Related part version.
	 */
	@Column
	@ManyToOne
	public PartVersion partVersion; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, Part> find = new Model.Finder<Long, Part>( // SUPPRESS CHECKSTYLE
			Long.class, Part.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -1695193238431975776L;
}

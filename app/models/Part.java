package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;

/**
 * Part model class.
 */
@Entity
@Table(name = "parts")
public final class Part extends Model {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	@Column
	@NotNull
	@CreatedTimestamp
	public Date createDate;

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -7556779886341618589L;

}

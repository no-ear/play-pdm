package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;

/**
 * PartVersion model class.<br>
 * NOTE EBean support only single table strategy.<br>
 * =========================<br>
 * Design Philosophy<br>
 * http://www.avaje.org/limitation.html<br>
 * Only Single Table Inheritance<br>
 * Current there is only support for single table inheritance.<br>
 * The other two inheritance strategies are considered <br>
 * Enhancement requests and will be introduced in a feature release.<br>
 * =========================<br>
 * So, Subclass cann't use @@Notnull.<br>
 */
@Entity
@Table(name = "part_versions")
@Inheritance
@DiscriminatorColumn(name = "discriminator")
public abstract class AbstractPartVersion extends Model {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * Version number.
	 */
	@Column
	public int version; // SUPPRESS CHECKSTYLE

	/**
	 * Create date.
	 */
	@Column
	@NotNull
	@CreatedTimestamp
	public Date createDate; // SUPPRESS CHECKSTYLE

	/**
	 * Create user.
	 */
	@Column
	@NotNull
	public Person createPerson; // SUPPRESS CHECKSTYLE

	/**
	 * Modify date.
	 */
	@Column
	public Date modifyDate; // SUPPRESS CHECKSTYLE

	/**
	 * Modify user.
	 */
	@Column
	public Person modifyPerson; // SUPPRESS CHECKSTYLE

	/**
	 * Lifecycle state const value set.
	 */
	enum LIFECYCLE_STATE_NAME {
		/**
		 * Work in progress.
		 */
		IN_WORK,
		/**
		 * Wait until approval.
		 */
		IN_APPROVE,
		/**
		 * Approved part.
		 */
		APPROVED,
		/**
		 * Released part, document, plan etc.
		 */
		RELEASED,
		/**
		 * Dispose part.
		 */
		DISPOSED
	}

	/**
	 * Life cycle state name.
	 */
	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	public LIFECYCLE_STATE_NAME lifecycleStateName = LIFECYCLE_STATE_NAME.IN_WORK; // SUPPRESS CHECKSTYLE

	/**
	 * Origin part relation.
	 */
	@Column
	@NotNull
	@ManyToOne
	public Part part; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, AbstractPartVersion> find = new Model.Finder<Long, AbstractPartVersion>( // SUPPRESS CHECKSTYLE
			Long.class, AbstractPartVersion.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 8112656005314996071L;

	/**
	 * Build new part and part version.<br>
	 * セーブの責任は基本的にモデル側にあるので呼び出し元で呼ばないでね。<br>
	 * This method is "save" responsibility. So client doesn't need it.
	 * 
	 * @param part
	 *            New create part property
	 * @param partVersion
	 *            New create part version property
	 * @return New PartVersion
	 */
	public static AbstractPartVersion buildPartVersion(final Part part,
			final AbstractPartVersion partVersion) {

		part.save();

		partVersion.part = part;
		partVersion.version = 1;

		partVersion.save();

		return partVersion;
	}
}

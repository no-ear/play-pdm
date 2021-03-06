package models.partversions;

import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import models.Document;
import models.ModelsUtility;
import models.Packetable;
import models.Part;
import models.Person;
import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.api.libs.json.JsValue;
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
 * So, NOTE that subclass cann't use @@Notnull.
 */
@Entity
@Table(name = "part_versions")
@Inheritance
@DiscriminatorColumn(name = "discriminator")
public class PartVersion extends Model implements Packetable {

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
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public int version = 1; // SUPPRESS CHECKSTYLE

	/**
	 * Create date.
	 */
	@Column
	@NotNull
	@CreatedTimestamp
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public Date createDate; // SUPPRESS CHECKSTYLE

	/**
	 * Create user.
	 */
	@Column
	@NotNull
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public Person createPerson; // SUPPRESS CHECKSTYLE

	/**
	 * Modify date.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public Date modifyDate; // SUPPRESS CHECKSTYLE

	/**
	 * Modify user.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
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
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public LIFECYCLE_STATE_NAME lifecycleStateName = LIFECYCLE_STATE_NAME.IN_WORK; // SUPPRESS CHECKSTYLE

	/**
	 * Origin part relation.
	 */
	@Column
	@NotNull
	@ManyToOne
	public Part part; // SUPPRESS CHECKSTYLE

	/**
	 * Related document.
	 */
	@Column
	@OneToMany
	public List<Document> documents; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, PartVersion> find = new Model.Finder<Long, PartVersion>( // SUPPRESS CHECKSTYLE
			Long.class, PartVersion.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 8112656005314996071L;

	@Override
	public final ObjectNode toJsonNode() {
		ObjectNode jsonNode = ModelsUtility.toJsonNode(this);
		jsonNode.put("id", id);
		return jsonNode;
	}

	@Override
	public final JsValue toJsValue() {
		ObjectNode jsonObject = toJsonNode();
		return play.api.libs.json.Json.parse(jsonObject.toString());
	}
}

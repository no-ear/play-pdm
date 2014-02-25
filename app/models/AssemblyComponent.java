package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.partversions.PartVersion;
import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.api.libs.json.JsValue;
import play.db.ebean.Model;

/**
 * Assembly component model class.<br>
 * Part assembly.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assembly_components")
public final class AssemblyComponent extends Model implements Packetable {
	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * Parent part version.
	 */
	@Column
	public PartVersion assemblyVersion; // SUPPRESS CHECKSTYLE

	/**
	 * Child part version.
	 */
	@Column
	public PartVersion componentVersion; // SUPPRESS CHECKSTYLE

	/**
	 * Part number.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT, isCreate = false)
	public long quantityOfComponent; // SUPPRESS CHECKSTYLE

	@Override
	public ObjectNode toJsonNode() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public JsValue toJsValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}

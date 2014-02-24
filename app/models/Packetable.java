package models;

import play.api.libs.json.JsValue;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Ajax Packet creatable interface.
 */
public interface Packetable {
	/**
	 * Return Json object.
	 * 
	 * @return Json object
	 */
	ObjectNode toJsonNode();

	/**
	 * Return Json object.<br>
	 * java1.8 default implements?
	 * 
	 * @return Json object(Scala object)
	 */
	JsValue toJsValue();
}

package models;

import java.util.List;

import play.api.libs.json.JsValue;
import play.libs.Json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * パケットユーティリティクラス.
 */
public final class PacketUtility {

	/**
	 * コンストラクタキル.
	 */
	private PacketUtility() {
	}

	/**
	 * リストをJsonにしてかえす.
	 * 
	 * @param list
	 *            リスト
	 * @param <T>
	 *            パケット可能なクラス
	 * @return Jsonオブジェクト
	 */
	public static <T extends Packetable> ArrayNode toArrayNode(
			final List<T> list) {

		ObjectNode json = Json.newObject();
		ArrayNode array = json.arrayNode();

		for (T comment : list) {
			array.add(comment.toJsonNode());
		}

		return array;
	}

	/**
	 * リストをJsonにしてかえす.
	 * 
	 * @param list
	 *            リスト
	 * @param <T>
	 *            パケット可能なクラス
	 * @return Jsonオブジェクト
	 */
	public static <T extends Packetable> ArrayNode toArrayNode(final T[] list) {

		ObjectNode json = Json.newObject();
		ArrayNode array = json.arrayNode();

		for (T comment : list) {
			array.add(comment.toJsonNode());
		}

		return array;
	}

	/**
	 * リストをJsonにしてかえす.
	 * 
	 * @param list
	 *            リスト
	 * @param <T>
	 *            パケット可能なクラス
	 * @return スカラ用Jsonオブジェクト、JsValueだが配列のはず
	 */
	public static <T extends Packetable> JsValue toJsArray(final List<T> list) {

		ArrayNode jsonObject = toArrayNode(list);

		return play.api.libs.json.Json.parse(jsonObject.toString());
	}
}

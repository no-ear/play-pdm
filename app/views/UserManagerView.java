package views;

import models.AttributeDefinition;

/**
 * usermanager.scala.html support class.
 */
public final class UserManagerView {

	/**
	 * Kill constructor.
	 */
	private UserManagerView() {
	}

	/**
	 * Create property form html piece. <br>
	 * TODO htmlではなくて必要なデータ構造体の配列かえしてscala側でレンダルできない？<br>
	 * そのばあいcontrolにおくべき？
	 * 
	 * @param attributeDefinitions
	 *            class field definition
	 * @return html piece
	 */
	public static String generatePropertiesForm(
			final AttributeDefinition[] attributeDefinitions) {

		String resultHtml = "";

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			String inputHtml = "<div class='form-group'>"
					+ "<label class='col-sm-2 control-label'>"
					+ attributeDefinition.displayName + "</label>"
					+ "<div class='col-sm-10'>" + "<input type='"
					+ attributeDefinition.inputType.toString()
					+ "' class='form-control' name='"
					+ attributeDefinition.name + "' ";

			if (attributeDefinition.isRequired) {
				inputHtml += "required";
			}

			inputHtml += " >" + "</div>" + "</div>";

			resultHtml += inputHtml;
		}

		return resultHtml;
	}
}

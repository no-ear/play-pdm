package views;

import java.lang.reflect.Field;

import javax.validation.constraints.NotNull;

import models.Person;
import annotation.PropertyType;

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
	 * @return html piece
	 */
	public static String generatePropertiesForm() {

		String resultHtml = "";

		Field[] fields = Person.class.getFields();

		for (Field field : fields) {
			PropertyType propertyType = field.getAnnotation(PropertyType.class);
			if (propertyType == null) {
				continue;
			}

			boolean isRequired = false;
			if (field.getAnnotation(NotNull.class) != null) {
				isRequired = true;
			}

			String inputHtml = "<div class='form-group'>"
					+ "<label class='col-sm-2 control-label'>"
					+ field.getName() + "</label>" + "<div class='col-sm-10'>"
					+ "<input type='" + propertyType.type()
					+ "' class='form-control' name='" + field.getName() + "' ";

			if (isRequired) {
				inputHtml += "required";
			}

			inputHtml += " >" + "</div>" + "</div>";

			resultHtml += inputHtml;

		}

		return resultHtml;
	}
}

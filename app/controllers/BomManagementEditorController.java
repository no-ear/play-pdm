package controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import models.AssemblyComponent;
import models.AttributeDefinition;
import models.ClassDefinition;
import models.Part;
import models.partversions.PartVersion;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.BomManagementEditor;

/**
 * BOM management editor class.
 */
@Security.Authenticated(Secured.class)
public class BomManagementEditorController extends Controller {

	/**
	 * Index page. BOM management editor.
	 * 
	 * @return Http response
	 */
	public static Result index() {
		ArrayList<AttributeDefinition> arrayList = new ArrayList<AttributeDefinition>();

		AttributeDefinition[] attribute = getAttribute(Part.class);
		arrayList.addAll(Arrays.asList(attribute));

		attribute = getAttribute(PartVersion.class);
		arrayList.addAll(Arrays.asList(attribute));

		attribute = getAttribute(AssemblyComponent.class);
		arrayList.addAll(Arrays.asList(attribute));

		AttributeDefinition[] attributeDefinitions = new AttributeDefinition[arrayList
				.size()];

		return ok(BomManagementEditor.render(arrayList
				.toArray(attributeDefinitions)));
	}

	private static AttributeDefinition[] getAttribute(Class<?> clazz) {
		ClassDefinition classDefinition = new ClassDefinition(clazz);

		Field[] fields = clazz.getFields();
		AttributeDefinition[] attributeDefinitions = AttributeDefinition
				.getAttributeDefinitions(classDefinition, fields);

		return attributeDefinitions;
	}
}

package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.partmanager;

/**
 * Part manager controller class.
 */
@Security.Authenticated(Secured.class)
public class PartManagerController extends Controller {
	/**
	 * Index page. User manager.
	 * 
	 * @return Http response
	 */
	public static Result index() {
		return ok(partmanager.render());
	}
};

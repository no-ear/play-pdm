package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.usermanager;

/**
 * User manager controller class.
 */
@Security.Authenticated(Secured.class)
public class UserManagerController extends Controller {
	/**
	 * Index page. Pdm main dashboad.
	 * 
	 * @return Http response
	 */
	public static Result index() {
		return ok(usermanager.render());
	}
}
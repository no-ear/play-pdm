package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import play.mvc.Security;

/**
 * Pdm main dashboad controller class.
 */
@Security.Authenticated(Secured.class)
public class Application extends Controller {

	/**
	 * Index page. Pdm main dashboad.
	 * 
	 * @return Http response
	 */
	public static Result index() {
		return ok(index.render());
	}

}

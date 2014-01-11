package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Part manager controller class.
 */
@Security.Authenticated(Secured.class)
public class PartVersionDetailController extends Controller {
	/**
	 * Index page. Part version detail manager.
	 * 
	 * @param id
	 *            Part version id
	 * @return Http response
	 */
	public static Result index(final long id) {
		return TODO;
	}
}

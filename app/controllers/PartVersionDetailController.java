package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.PartVersionDetail;

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
		return ok(PartVersionDetail.render(id));
	}

	public static Result upload(final long id) {
		return TODO;
	}

}

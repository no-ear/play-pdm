package controllers;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Index;
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
		return ok(Index.render());
	}

	/**
	 * Create JavaScript router.
	 * 
	 * @return Http response. JavaScript router file.
	 */
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
				controllers.routes.javascript.UserManagerController.create(),
				controllers.routes.javascript.UserManagerController.readLike(),
				controllers.routes.javascript.PartManagerController
						.getPartIndex(),
				controllers.routes.javascript.BoltPartVersionController
						.readLike()));
	}
}

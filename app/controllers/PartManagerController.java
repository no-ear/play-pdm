package controllers;

import models.partversions.BoltPartVersion;
import models.partversions.FramePartVersion;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.PartManager;

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

		Class<?>[] classArray = { BoltPartVersion.class, FramePartVersion.class };

		return ok(PartManager.render(classArray));
	}
};

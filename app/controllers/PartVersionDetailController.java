package controllers;

import java.io.File;
import java.util.HashMap;

import models.Document;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
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
		MultipartFormData body = request().body().asMultipartFormData();

		FilePart picture = body.getFile("upfile");

		if (picture != null) {
			// String fileName = picture.getFilename();
			// String contentType = picture.getContentType();
			File file = picture.getFile();

			HashMap<String, Object> properties = new HashMap<String, Object>();
			try {
				Document.buildDocument(properties, file);
			} catch (Exception e) {
				e.printStackTrace();
				return badRequest();
			}

			return ok("File uploaded");
		} else {
			flash("error", "Missing file");
			return redirect(routes.Application.index());
		}
	}

}

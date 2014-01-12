package controllers;

import java.io.File;
import java.util.HashMap;

import models.Document;
import models.partversions.PartVersion;
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

		PartVersion partVersion = PartVersion.find.byId(id);

		return ok(PartVersionDetail.render(id, partVersion.documents));
	}

	public static Result upload(final long id) {
		MultipartFormData body = request().body().asMultipartFormData();

		FilePart picture = body.getFile("upfile");

		if (picture != null) {
			String fileName = picture.getFilename();
			// String contentType = picture.getContentType();
			File file = picture.getFile();

			PartVersion partVersion = PartVersion.find.byId(id);

			HashMap<String, Object> properties = new HashMap<String, Object>();

			properties.put("Document.fileName", fileName);
			properties.put("Document.fileExtension", getSuffix(file.getPath()));

			try {
				Document.buildDocument(properties, file, partVersion);
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

	/**
	 * ファイル名から拡張子を返します。
	 * 
	 * @param fileName
	 *            ファイル名
	 * @return ファイルの拡張子
	 */
	private static String getSuffix(final String fileName) {
		if (fileName == null) {
			return null;
		}

		int point = fileName.lastIndexOf(".");
		if (point != -1) {
			return fileName.substring(point + 1);
		}

		return fileName;
	}
}

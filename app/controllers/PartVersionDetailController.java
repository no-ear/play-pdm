package controllers;

import java.io.File;
import java.io.IOException;
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

	/**
	 * Document upload.
	 * 
	 * @param id
	 *            Part version id
	 * @return Http response
	 */
	public static Result upload(final long id) {
		MultipartFormData body = request().body().asMultipartFormData();

		FilePart filePart = body.getFile("upfile");

		if (filePart == null) {
			return internalServerError();
		}

		String fileName = filePart.getFilename();
		@SuppressWarnings("unused")
		String contentType = filePart.getContentType();
		File file = filePart.getFile();

		// TODO contentType, file extension, MIME check.

		PartVersion partVersion = PartVersion.find.byId(id);

		HashMap<String, Object> properties = new HashMap<String, Object>();

		properties.put("Document.fileName", fileName);
		properties.put("Document.fileExtension", getExtension(file.getPath()));

		try {
			Document.buildDocument(properties, file, partVersion);
		} catch (IOException e) {
			e.printStackTrace();
			return internalServerError();
		}

		return ok();
	}

	/**
	 * Get extension from file name string.
	 * 
	 * @param fileName
	 *            file name
	 * @return file name extension
	 */
	private static String getExtension(final String fileName) {
		if (fileName == null) {
			return null;
		}

		int point = fileName.lastIndexOf(".");
		if (point != -1) {
			return fileName.substring(point + 1);
		}

		return null;
	}
}

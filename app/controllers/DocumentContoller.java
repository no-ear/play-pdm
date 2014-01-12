package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import models.Document;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Part manager controller class.
 */
@Security.Authenticated(Secured.class)
public class DocumentContoller extends Controller {
	/**
	 * Document download.
	 * 
	 * @return Http response
	 */
	public static Result download(String fileName) {

		String path = "public/cache/" + fileName;
		File file = new File(path);

		Document document = Document.find.where().eq("fileHash", fileName)
				.findUnique();

		if (!file.exists()) {
			// Create cache file
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(path);
				fos.write(document.file);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				return badRequest();
			}
		}

		response().setHeader("Content-Disposition",
				"attachment; filename=" + document.fileName);
		return ok(file);
	}
}

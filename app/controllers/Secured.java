package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Play 認証用クラス.
 */
public final class Secured extends Security.Authenticator {

	@Override
	public String getUsername(final Context ctx) {
		return ctx.session().get("name");
	}

	@Override
	public Result onUnauthorized(final Context ctx) {
		return redirect(routes.LoginController.login());
	}

}

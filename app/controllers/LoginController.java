package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.signin;

/**
 * Login/Logout session controller class.
 */
public class LoginController extends Controller {
	/**
	 * ログイン画面ページエントリ.
	 * 
	 * @return Httpレスポンス
	 */
	public static Result login() {
		return ok(signin.render());
	}
}

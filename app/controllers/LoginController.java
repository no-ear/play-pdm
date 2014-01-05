package controllers;

import static play.data.Form.form;
import models.Person;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.SignIn;

/**
 * Login/Logout session controller class.
 */
public class LoginController extends Controller {
	/**
	 * Login view.
	 * 
	 * @return Http response
	 */
	public static Result login() {
		return ok(SignIn.render());
	}

	/**
	 * Authenticate with name and password.
	 * 
	 * @return Http response
	 */
	public static Result authenticate() {

		Form<LoginProperty> loginForm = form(LoginProperty.class)
				.bindFromRequest();
		LoginProperty loginUser = loginForm.get();

		Person person = Person.authenticate(loginUser.name, loginUser.password);

		if (person == null) {
			return badRequest(SignIn.render());
		} else {
			session().clear();
			session("name", person.name);

			return redirect(routes.Application.index());
		}
	}

	/**
	 * Logout.
	 * 
	 * @return Http response
	 */
	public static Result logout() {
		session().clear();
		return redirect(routes.LoginController.login());
	}

	/**
	 * Login form data class.
	 */
	public static final class LoginProperty {

		/**
		 * Input name.
		 */
		public String name; // SUPPRESS CHECKSTYLE getter/setter create play framework.

		/**
		 * Input password.
		 */
		public String password; // SUPPRESS CHECKSTYLE
	}
}

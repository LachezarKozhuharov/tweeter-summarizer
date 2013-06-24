package controllers;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() {

		String message = "Hi.";

		return ok(index.render(message));
	}

	public static Result statusesSample() {
		return ok("asd");
	}

}
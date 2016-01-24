package controllers

import persistence.UserPersistenceComponentImpl
import play.api.mvc.Action
import services.UserServiceComponentImpl


class Application extends UserController
	with UserServiceComponentImpl
	with UserPersistenceComponentImpl {


	def index = Action {
		Ok(views.html.index("Your new appl2ication is ready."))
	}


}

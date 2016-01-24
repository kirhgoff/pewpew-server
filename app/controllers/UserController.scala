package controllers

import common.JsonImplicits
import entity.User
import play.api.libs.json.{Json}
import play.api.mvc._
import services.UserServiceComponent
import scala.concurrent.Future

/**
	* Controller to handle rest api for user get, save and delete
	*/
class UserController extends Controller with JsonImplicits {
	self: UserServiceComponent =>

	import scala.concurrent.ExecutionContext.Implicits.global

	def save = Action.async(parse.json) { implicit request =>
		request.body.asOpt[User] match {
			case Some(user) => userService.save(user).map{user => Created(Json.toJson(user))}
			case None => Future{BadRequest}
		}
	}


	def get(id: String) = Action.async {
		userService.get(id).map {
			case Some(user) => Ok(Json.toJson(user))
			case None => NotFound
		}
	}

	def delete(id: String) = Action.async {
		userService.delete(id).map {
			case Some(user) => Ok(Json.toJson(user))
			case None => NotFound
		}

	}
}

package controllers

import common.JsonImplicits
import entity.User
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification.BeforeEach
import play.api.libs.json.Json
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test._


/**
 * UserController tests using FakeRequest
 */
@RunWith(classOf[JUnitRunner])
class UserControllerSpec extends Specification with Results with BeforeEach with JsonImplicits {

	private var application: Application = null
	private val user = User("tom", "test")

	override def before {
		application = new Application
	}


	"UserController" should {

		"respond to get user" in {
			application.userService.save(user)
			val requestResult = application.get(user.email)(FakeRequest())

			status(requestResult) must equalTo(OK)
			contentType(requestResult) must beSome("application/json")
			contentAsJson(requestResult) must beEqualTo(Json.toJson(user))
		}

		"respond to get non-existant user" in new WithApplication {
			val requestResult = application.get(user.email)(FakeRequest())

			status(requestResult) must equalTo(NOT_FOUND)
		}

		"respond to save existing user" in new WithApplication {
			application.userService.save(user)

			val requestResult = call(application.save, FakeRequest().withJsonBody(Json.toJson(user)))

			status(requestResult) must equalTo(OK)
			contentType(requestResult) must beSome("application/json")
			contentAsJson(requestResult) must beEqualTo(Json.toJson(user))
		}

		"respond to save a new user with bad format" in new WithApplication {

			val requestResult = call(application.save, FakeRequest().withJsonBody(Json.parse("""{ "field": "value" }""")))

			status(requestResult) must equalTo(BAD_REQUEST)
		}

		"respond to save new user" in new WithApplication {
			val requestResult = call(application.save, FakeRequest().withJsonBody(Json.toJson(user)))

			status(requestResult) must equalTo(OK)
			contentType(requestResult) must beSome("application/json")
			contentAsJson(requestResult) must beEqualTo(Json.toJson(user))
		}

		"respond to delete user" in new WithApplication {
			application.userService.save(user)
			val requestResult = application.delete(user.email)(FakeRequest())

			status(requestResult) must equalTo(OK)
			contentType(requestResult) must beSome("application/json")
			contentAsJson(requestResult) must beEqualTo(Json.toJson(user))
		}

		"respond to delete non-existant user" in new WithApplication {
			val requestResult = application.delete(user.email)(FakeRequest())

			status(requestResult) must equalTo(NOT_FOUND)
		}
	}
}

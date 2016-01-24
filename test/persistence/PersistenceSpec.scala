package persistence

import entity.User
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification.BeforeEach
import scala.concurrent.{Await}
import scala.concurrent.duration._


@RunWith(classOf[JUnitRunner])
class PersistenceSpec extends Specification with BeforeEach with UserPersistenceComponentImpl {

	var userPersist = new UserPersistenceImpl

	override def before = {
		userPersist = new UserPersistenceImpl
	}

	"Adding a user should" >> {

		"add a new user" >> {
			val user = User("tom@gmail.com", "Tom")
			userPersist.save(user)

			val user2 = Await.result(userPersist.get(user.email), 5.seconds)
			user2 shouldEqual(Some(user))
		}
	}

	"Saving a user should" >> {

		"save a user" >> {
				val user = User("tom@gmail.com", "Tom")
				userPersist.save(user)
				val user2 = Await.result(userPersist.get(user.email), 5.seconds)
				user2 shouldEqual(Some(user))
		}

		"update a user" >> {
			val user = User("tom@gmail.com", "Tom")
			userPersist.save(user)
			val user2 = Await.result(userPersist.get(user.email), 5.seconds)
			user2 shouldEqual(Some(user))

			userPersist.save(user.copy(name = "NEWNAME"))

			val user3 = Await.result(userPersist.get(user.email), 5.seconds)
			user3 shouldEqual(Some(user.copy(name = "NEWNAME")))
		}
	}

	"Deleting a user should" >> {

		"delete a user" >> {
			val user = User("tom@gmail.com", "Tom")
			userPersist.save(user)
			val user2 = Await.result(userPersist.get(user.email), 5.seconds)
			user2 shouldEqual(Some(user))

			userPersist.delete(user.email)
			val user3 = Await.result(userPersist.get(user.email), 5.seconds)
			user3 shouldEqual(None)
		}

		"delete a non existant user" >> {
			val user = User("tom@gmail.com", "Tom")
			val user2 = Await.result(userPersist.get(user.email), 5.seconds)
			user2 shouldEqual(None)

			userPersist.delete(user.email)

			val user3 = Await.result(userPersist.get(user.email), 5.seconds)
			user3 shouldEqual(None)
		}
	}

	"Getting a user should" >> {

		"get a user" >> {
			val user = User("tom@gmail.com", "Tom")
			userPersist.save(user)

			val user2 = Await.result(userPersist.get(user.email), 5.seconds)
			user2 shouldEqual(Some(user))
		}

		"return None for a non-existant user" >> {
			val user = User("tom@gmail.com", "Tom")

			val user2 = Await.result(userPersist.get(user.email), 5.seconds)
			user2 shouldEqual(None)
		}
	}

}

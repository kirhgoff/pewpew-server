package services

import common.CrudService
import entity.User
import persistence.UserPersistenceComponent
import scala.concurrent.Future

/**
	* User service abstraction.
	*/
trait UserServiceComponent {

	val userService: CrudService[String, User]

}


trait UserServiceComponentImpl extends UserServiceComponent {
	self: UserPersistenceComponent =>

	val userService = new UserServiceImpl

	class UserServiceImpl extends CrudService[String, User] {

		def save(entity: User): Future[User] = {
			userPersistence.save(entity)
		}

		def get(id: String): Future[Option[User]] = {
			userPersistence.get(id)
		}

		def delete(id: String): Future[Option[User]] = {
			userPersistence.delete(id)
		}
	}


}
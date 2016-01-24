package persistence

import common.CrudService
import entity.User
import scala.concurrent.Future

/**
	* Handles user persistence
	*/
trait UserPersistenceComponent {

	val userPersistence: CrudService[String, User]

}


trait UserPersistenceComponentImpl extends UserPersistenceComponent {
	val userPersistence = new UserPersistenceImpl

	class UserPersistenceImpl extends CrudService[String, User] {

		// For now we have an in-memory database
		private var users = Map[String, User]()

		override def save(entity: User): Future[User] = {
			Future.successful {
				users = users + (entity.email -> entity)
				entity
			}
		}

		override def get(id: String): Future[Option[User]] = {
			Future.successful {users.get(id)}
		}

		override def delete(id: String): Future[Option[User]] = {
			Future.successful {
				users.get(id) match {
					case Some(user) =>
						users = users - id
						Some(user)
					case None => None
				}
			}
		}
	}
}

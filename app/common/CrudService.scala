package common

import scala.concurrent.Future

/**
	* Abstract representation of a CRUD service
	* Returns futures representing blocking calls
	* Simplified with the use of save
	* @tparam T
	*/
trait CrudService[S, T] {

	def save(entity: T): Future[T]

	def get(id: S): Future[Option[T]]

	def delete(id: S): Future[Option[T]]

}

package entity


/**
	* Represents a user in the database
	* @param email - also the UUID
	*/
case class User(email: String, name: String)
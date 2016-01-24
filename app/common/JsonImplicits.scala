package common

import entity.User
import play.api.libs.json.Json

/**
	* Using the play json libs makes marshalling to and from Case classes very easy :)
	*/
trait JsonImplicits {

	implicit val MyJsonReads = Json.reads[User]
	implicit val MyJsonWrites = Json.writes[User]
}

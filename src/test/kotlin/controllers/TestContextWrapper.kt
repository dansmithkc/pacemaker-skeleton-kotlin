package controllers

import models.Activity
import models.Location
import models.Message
import models.User

class TestContextWrapper() : ContextWrapper {
	var json = ""
	var status = 0
	var returnedUser = User()
	var returnedActivity = Activity()
	var returnedLocation = Location()
	var returnedMessage = Message()
	var params = HashMap<String, String>()
	var queryParams = HashMap<String, String>()
	var resultText = ""

	override fun json(theObject: Any) {
		json = theObject.toString()
	}

	override fun <T> bodyAsClass(theClass: Class<T>): T {
		var theObject = theClass.newInstance()

		if (theObject is User) {
			theObject.firstname = returnedUser.firstname
			theObject.lastname = returnedUser.lastname
			theObject.email = returnedUser.email
			theObject.password = returnedUser.password
			theObject.id = returnedUser.id
		}
		if (theObject is Activity) {
			theObject.type = returnedActivity.type
			theObject.location = returnedActivity.location
			theObject.distance = returnedActivity.distance
		}
		if (theObject is Location) {
			theObject.latitude = returnedLocation.latitude
			theObject.longitude = returnedLocation.longitude
		}
		if (theObject is Message) {
			theObject.text = returnedMessage.text
		}

		return theObject
	}

	override fun status(code: Int) {
		status = code
	}

	override fun param(name: String): String? {
		return params.get(name)
	}

	override fun queryParam(name: String): String? {
		return queryParams.get(name)
	}

	override fun result(result: String) {
		resultText = result
	}

}
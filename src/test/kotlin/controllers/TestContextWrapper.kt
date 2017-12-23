package controllers

import models.Activity
import models.User

class TestContextWrapper() : ContextWrapper {
	var result = ""
	var status = 0
	var returnedUser = User()
	var returnedActivity = Activity()
	var params = HashMap<String, String>()

	override fun json(theObject: Any) {
		result = theObject.toString()
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

		return theObject
	}

	override fun status(code: Int) {
		status = code
	}

	override fun param(name: String): String? {
		return params.get(name)
	}
}
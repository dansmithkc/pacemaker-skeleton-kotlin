package controllers

import models.User

class TestContextWrapper() : ContextWrapper {
	var result = ""
	var status = 0
	var returnedUser: User = User()

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

		return theObject
	}

	override fun status(code: Int) {
		status = code
	}
}
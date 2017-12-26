package controllers

import models.Activity
import models.Fixtures
import models.User

class PacemakerRestServiceTestUtilities(
		var service: PacemakerRestService,
		var context: TestContextWrapper,
		var fixtures: Fixtures = Fixtures()) {

	fun setupCreateUser(user: User = fixtures.users[3]): String {
		context.returnedUser = user
		service.createUser(context)
		service.listUsers(context)
		return findIdInResult(context.json)
	}

	fun setupCreateActivity(userId: String, activity: Activity = fixtures.activities[0]): String {
		context.params.put("id", userId)
		context.returnedActivity = activity
		service.createActivity(context)
		return findIdInResult(context.json)
	}

	fun findIdInResult(result: String): String {
		var id = result.substring(result.indexOf(", id=") + 5)
		return id.substring(0, id.indexOf(","))
	}

	fun removeIdFromResult(result: String): String {
		return result.replace(Regex(", id=.+?,"), ", id=,")
	}

	fun listOfActivityFields(result: String, fieldName: String): List<String> {
		return result.split(fieldName)
				.filterIndexed { index, value -> index > 0 }
				.map { it.replace(Regex(",.*"), "") }
	}

}

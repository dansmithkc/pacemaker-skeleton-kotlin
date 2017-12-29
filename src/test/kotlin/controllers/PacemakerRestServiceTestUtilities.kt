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

	fun setupCreateFriend() {
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)

		var user2 = fixtures.users[2]
		context.returnedUser = user2
		service.createUser(context)
		var user2Id = StringTestUtilities.findIdInResult(context.json)

		context.params.put("id", user1Id)
		context.params.put("friendId", user2Id)
		service.followFriend(context)
	}

	fun findIdInResult(result: String): String {
		return StringTestUtilities.findIdInResult(result)
	}

	fun removeIdFromResult(result: String): String {
		return StringTestUtilities.removeIdFromResult(result)
	}

	fun listOfActivityFields(result: String, fieldName: String): List<String> {
		return StringTestUtilities.listOfActivityFields(result, fieldName)
	}

}

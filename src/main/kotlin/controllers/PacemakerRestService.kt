package controllers

import models.Activity
import models.Location
import models.Message
import models.User

class PacemakerRestService {
	val pacemaker = PacemakerAPI()

	fun listUsers(ctx: ContextWrapper) {
		ctx.json(pacemaker.users)
	}

	fun createUser(ctx: ContextWrapper) {
		val user = ctx.bodyAsClass(User::class.java)
		val newUser = pacemaker.createUser(user.firstname, user.lastname, user.email, user.password)
		ctx.json(newUser)
	}

	fun deleteUsers(ctx: ContextWrapper) {
		pacemaker.deleteUsers()
		ctx.json("ok")
		ctx.status(204)
	}

	fun getActivities(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		val sortBy: String = ctx.queryParam("sortBy") ?: ""
		var activities = pacemaker.listActivities(id!!, sortBy)
		if (activities != null) {
			ctx.json(activities)
		} else {
			ctx.status(404)
		}
	}

	fun createActivity(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		val user = pacemaker.getUser(id!!)
		if (user != null) {
			val activity = ctx.bodyAsClass(Activity::class.java)
			val newActivity = pacemaker.createActivity(user.id, activity.type, activity.location, activity.distance)
			ctx.json(newActivity!!)
		} else {
			ctx.status(404)
		}
	}

	fun deleteActivities(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		pacemaker.deleteActivities(id!!)
		ctx.json("ok")
		ctx.status(204)
	}

	fun getActivity(ctx: ContextWrapper) {
		val activity = getActivityImpl(ctx)
		if (activity == null) {
			return
		}
		ctx.json(activity)
	}

	fun getActivityImpl(ctx: ContextWrapper): Activity? {
		val id: String? = ctx.param("id")
		val user = pacemaker.getUser(id!!)
		if (user == null) {
			ctx.result("user id not found")
			ctx.status(404)
			return null
		}
		val activityId: String? = ctx.param("activityId")
		val activity = user.activities.get(activityId!!)
		if (activity == null) {
			ctx.result("no activity id associated with that user")
			ctx.status(404)
			return null
		}
		return activity
	}

	fun addLocation(ctx: ContextWrapper) {
		val activity = getActivityImpl(ctx)
		if (activity == null) {
			return
		}
		val location = ctx.bodyAsClass(Location::class.java)
		activity.route.add(location)
		ctx.json(activity)
	}

	fun getActivityLocations(ctx: ContextWrapper) {
		val activity = getActivityImpl(ctx)
		if (activity == null) {
			return
		}
		ctx.json(activity.route)
	}

	fun followFriend(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		val friendId: String? = ctx.param("friendId")
		pacemaker.followFriend(id!!, friendId!!)
		ctx.json("ok")
		ctx.status(200)
	}

	fun listFriends(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		val friends = pacemaker.listFriends(id!!)
		ctx.json(friends)
	}

	fun unfollowFriends(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		pacemaker.unfollowFriends(id!!)
		ctx.json("ok")
		ctx.status(204)
	}

	fun messageFriend(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		val friendId: String? = ctx.param("friendId")
		val message = ctx.bodyAsClass(Message::class.java)
		pacemaker.messageFriend(id!!, friendId!!, message)
		ctx.json("ok")
		ctx.status(200)
	}

	fun listMessages(ctx: ContextWrapper) {
		val id: String? = ctx.param("id")
		val messages = pacemaker.listMessages(id!!)
		ctx.json(messages)
		ctx.status(200)
	}

}
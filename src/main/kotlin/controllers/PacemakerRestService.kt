package controllers

import io.javalin.Context
import models.Activity
import models.Location
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

	fun deleteUsers(ctx: Context) {
		pacemaker.deleteUsers()
		ctx.status(200)
	}

	fun getActivity(ctx: Context) {
		// val userId: String? = ctx.param("id")
		val activityId: String? = ctx.param("activityId")
		val activity = pacemaker.getActivity(activityId!!)
		if (activity != null) {
			ctx.json(activity)
		} else {
			ctx.status(404)
		}
	}

	fun getActivities(ctx: Context) {
		val id: String? = ctx.param("id")
		val user = pacemaker.getUser(id!!)
		if (user != null) {
			ctx.json(user.activities)
		} else {
			ctx.status(404)
		}
	}

	fun createActivity(ctx: Context) {
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

	fun deleteActivites(ctx: Context) {
		val id: String? = ctx.param("id")
		pacemaker.deleteActivities(id!!);
		ctx.status(204)
	}

	fun findActivity(ctx: Context): Activity? {
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

	fun addLocation(ctx: Context) {
		val activity = findActivity(ctx)
		if (activity == null) {
			return
		}
		val location = ctx.bodyAsClass(Location::class.java)
		activity.route.add(location)
		ctx.json(activity)
	}

	fun getActivityLocations(ctx: Context) {
		val activity = findActivity(ctx)
		if (activity == null) {
			return
		}
		ctx.json(activity.route)
	}

}
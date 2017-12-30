package utils

import controllers.JavalinContextWrapper
import controllers.PacemakerRestService
import io.javalin.Javalin

class JavalinUtils {

	fun configRoutes(app: Javalin, service: PacemakerRestService) {
		app.get("/users") { ctx -> service.listUsers(JavalinContextWrapper(ctx)) }
		app.post("/users") { ctx -> service.createUser(JavalinContextWrapper(ctx)) }
		app.delete("/users") { ctx -> service.deleteUsers(JavalinContextWrapper(ctx)) }
		app.get("/users/:id/activities") { ctx -> service.getActivities(JavalinContextWrapper(ctx)) }
		app.get("/users/:id/activities/:activityId") { ctx -> service.getActivity(JavalinContextWrapper(ctx)) }
		app.post("/users/:id/activities") { ctx -> service.createActivity(JavalinContextWrapper(ctx)) }
		app.delete("/users/:id/activities") { ctx -> service.deleteActivities(JavalinContextWrapper(ctx)) }
		app.get("/users/:id/activities/:activityId/locations") { ctx -> service.getActivityLocations(JavalinContextWrapper(ctx)) }
		app.post("/users/:id/activities/:activityId/locations") { ctx -> service.addLocation(JavalinContextWrapper(ctx)) }
		app.get("/users/:id/friends") { ctx -> service.listFriends(JavalinContextWrapper(ctx)) }
		app.post("/users/:id/friends/:friendId") { ctx -> service.followFriend(JavalinContextWrapper(ctx)) }
		app.delete("/users/:id/friends") { ctx -> service.unfollowFriends(JavalinContextWrapper(ctx)) }
		app.post("/users/:id/friends/:friendId/message") { ctx -> service.messageFriend(JavalinContextWrapper(ctx)) }
		app.get("/users/:id/messages") { ctx -> service.listMessages(JavalinContextWrapper(ctx)) }
	}

}
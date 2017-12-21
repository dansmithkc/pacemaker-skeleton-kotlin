package controllers

import io.javalin.Javalin
import utils.HerokuUtils

fun main(args: Array<String>) {
	val herokuUtils = HerokuUtils()
	val port = herokuUtils.getHerokuAssignedPort(ProcessBuilder())
	val app = Javalin.create().port(port).start()
	val service = PacemakerRestService()
	configRoutes(app, service)
}

fun configRoutes(app: Javalin, service: PacemakerRestService) {
	app.get("/users") { ctx -> service.listUsers(JavalinContextWrapper(ctx)) }
	app.post("/users") { ctx -> service.createUser(JavalinContextWrapper(ctx)) }
	app.delete("/users") { ctx -> service.deleteUsers(JavalinContextWrapper(ctx)) }
	app.get("/users/:id/activities") { ctx -> service.getActivities(ctx) }
	app.get("/users/:id/activities/:activityId") { ctx -> service.getActivity(ctx) }
	app.post("/users/:id/activities") { ctx -> service.createActivity(ctx) }
	app.delete("/users/:id/activities") { ctx -> service.deleteActivites(ctx) }
	app.get("/users/:id/activities/:activityId/locations") { ctx -> service.getActivityLocations(ctx) }
	app.post("/users/:id/activities/:activityId/locations") { ctx -> service.addLocation(ctx) }
}
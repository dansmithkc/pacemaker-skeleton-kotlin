package controllers

import io.javalin.Javalin
import utils.HerokuUtils
import utils.JavalinUtils

fun main(args: Array<String>) {
	val herokuUtils = HerokuUtils()
	val port = herokuUtils.getHerokuAssignedPort(ProcessBuilder())
	val app = Javalin.create().port(port).start()
	val service = PacemakerRestService()
	val javalinUtils = JavalinUtils()
	javalinUtils.configRoutes(app, service)
}

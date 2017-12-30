package utils

import controllers.PacemakerRestService
import io.javalin.Javalin
import org.junit.After
import org.junit.Before
import org.junit.Test

class JavalinUtilsTest {

	lateinit var javalinUtils: JavalinUtils

	@Before
	fun setup() {
		javalinUtils = JavalinUtils()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun testConfigRoutes() {
		val app = Javalin.create()
		val service = PacemakerRestService()
		// Exercise
		javalinUtils.configRoutes(app, service)
	}

}
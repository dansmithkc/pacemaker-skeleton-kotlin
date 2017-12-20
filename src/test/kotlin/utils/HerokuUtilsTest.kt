package controllers

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import utils.HerokuUtils

class HerokuUtilsTest {

	lateinit var herokuUtils: HerokuUtils

	@Before
	fun setup() {
		herokuUtils = HerokuUtils()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun testGetHerokuAssignedPortNotDefined() {
		val processBuilder = ProcessBuilder()
		val port = herokuUtils.getHerokuAssignedPort(processBuilder);
		assertEquals(7000, port);
	}

	@Test
	fun testGetHerokuAssignedPortWhenDefined() {
		val processBuilder = ProcessBuilder()
		processBuilder.environment().put("PORT", "1234")
		val port = herokuUtils.getHerokuAssignedPort(processBuilder);
		assertEquals(1234, port);
	}
}
package controllers

import models.Fixtures
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class PacemakerRestServiceTest {
	lateinit var service: PacemakerRestService

	@Before
	fun setup() {
		service = PacemakerRestService()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun testGetter() {
		// Exercise
		service.pacemaker
	}

}


package controllers

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PacemakerResetServiceTest {
	lateinit var service: PacemakerRestService
	lateinit var context: TestContextWrapper

	@Before
	fun setup() {
		service = PacemakerRestService()
		context = TestContextWrapper()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun testListUsers() {
		service.listUsers(context)
		assertEquals(context.result, "[]")
	}

}

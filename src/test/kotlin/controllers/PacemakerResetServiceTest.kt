package controllers

import models.User
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
		assertEquals("[]", context.result)
	}

	@Test
	fun testCreateUser() {
		context.returnedUser = User("the", "test", "for", "me")
		service.createUser(context)
		// remove the ID since we cannot match on that
		var truncatedResult = context.result.substring(0, context.result.indexOf(", id"))
		assertEquals("User(firstname=the, lastname=test, email=for, password=me", truncatedResult)
	}
}

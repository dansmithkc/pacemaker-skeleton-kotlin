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
		// Exercise
		service.listUsers(context)
		// Verify
		assertEquals("[]", context.result)
	}

	@Test
	fun testCreateUser() {
		// Setup
		context.returnedUser = User("the", "test", "for", "me")
		// Exercise
		service.createUser(context)
		// Verify
		// remove the ID since we cannot match on that
		var truncatedResult = context.result.substring(0, context.result.indexOf(", id"))
		assertEquals("User(firstname=the, lastname=test, email=for, password=me", truncatedResult)
	}

	@Test
	fun testListShowsCreatedUser() {
		// Setup
		context.returnedUser = User("and", "what", "was", "true")
		service.createUser(context)
		// Exercise
		service.listUsers(context)
		// Verify
		var truncatedResult = context.result.substring(0, context.result.indexOf(", id"))
		assertEquals("[User(firstname=and, lastname=what, email=was, password=true", truncatedResult)
	}

	@Test
	fun testDeleteUsers() {
		// Exercise
		service.deleteUsers(context)
		// Verify
		assertEquals(200, context.status)
	}

	@Test
	fun testCreateThenDeleteUsers() {
		// Setup
		context.returnedUser = User("and", "what", "was", "true")
		service.createUser(context)
		// Exercise
		service.deleteUsers(context)
		// Verify
		assertEquals(200, context.status)
		// Exercise
		service.listUsers(context)
		// Verify
		assertEquals("[]", context.result)
	}

}

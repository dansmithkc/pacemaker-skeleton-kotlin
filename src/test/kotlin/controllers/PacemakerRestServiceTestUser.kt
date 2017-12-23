package controllers

import models.Activity
import models.Fixtures
import models.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class PacemakerRestServiceTest {
	lateinit var service: PacemakerRestService
	lateinit var context: TestContextWrapper
	lateinit var fixtures: Fixtures

	@Before
	fun setup() {
		service = PacemakerRestService()
		context = TestContextWrapper()
		fixtures = Fixtures()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun testListUsers() {
		// Exercise
		service.listUsers(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testCreateUser() {
		// Setup
		context.returnedUser = fixtures.users[0]
		// Exercise
		service.createUser(context)
		// Verify
		// remove the ID since we cannot match on that
		var truncatedResult = context.json.substring(0, context.json.indexOf(", id"))
		assertEquals("User(firstname=marge, lastname=simpson, email=marge@simpson.com, password=secret", truncatedResult)
	}

	@Test
	fun testListShowsCreatedUser() {
		// Setup
		context.returnedUser = fixtures.users[1]
		service.createUser(context)
		// Exercise
		service.listUsers(context)
		// Verify
		var truncatedResult = context.json.substring(0, context.json.indexOf(", id"))
		assertEquals("[User(firstname=lisa, lastname=simpson, email=lisa@simpson.com, password=secret", truncatedResult)
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
		context.returnedUser = fixtures.users[2]
		service.createUser(context)
		// Exercise
		service.deleteUsers(context)
		// Verify
		assertEquals(200, context.status)
		// Exercise
		service.listUsers(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testGetActivitiesNoneForUser() {
		// Setup
		var id = setupCreateUser()
		context.params.put("id", id)
		// Exercise
		service.getActivities(context)
		// Verify
		assertEquals("{}", context.json)
	}

	@Test
	fun testCreateActivity() {
		// Setup
		var id = setupCreateUser()
		context.params.put("id", id)
		context.returnedActivity = fixtures.activities[0]
		// Exercise
		service.createActivity(context)
		// Verify
		var truncatedResult = context.json.substring(0, context.json.indexOf(", id"))
		assertEquals("Activity(type=walk, location=fridge, distance=0.001", truncatedResult)
	}

	@Test
	fun testGetActivitiesNoUser() {
		// Setup
		context.params.put("id", "invalidUserId")
		// Exercise
		service.getActivities(context)
		// Verify
		assertEquals(404, context.status)
	}

	@Test
	fun testGetActivitiesNullUser() {
		// Exercise
		try {
			service.getActivities(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testCreateActivityNoUser() {
		// Setup
		context.params.put("id", "invalidUserId")
		// Exercise
		service.createActivity(context)
		// Verify
		assertEquals(404, context.status)
	}

	@Test
	fun testCreateActivityNullUser() {
		// Exercise
		try {
			service.createActivity(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testDeleteActivitiesNoneForUser() {
		// Setup
		var id = setupCreateUser()
		context.params.put("id", id)
		// Exercise
		service.deleteActivities(context)
		// Verify
		assertEquals(204, context.status)
		service.getActivities(context)
		assertEquals("{}", context.json)
	}

	@Test
	fun testDeleteActivitiesOneForUser() {
		// Setup
		var userId = setupCreateUser()
		setupCreateActivity(userId)
		context.params.put("id", userId)
		// Exercise
		service.deleteActivities(context)
		// Verify
		assertEquals(204, context.status)
		service.getActivities(context)
		assertEquals("{}", context.json)
	}

	@Test
	fun testDeleteActivitiesNoUser() {
		// Setup
		context.params.put("id", "invalidUserId")
		// Exercise
		try {
			service.deleteActivities(context)
			fail("Did not throw an exception on an invalid user")
		} catch (e: IllegalArgumentException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testDeleteActivitiesNullUser() {
		// Exercise
		try {
			service.deleteActivities(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testGetActivityNoUser() {
		// Setup
		context.params.put("id", "invalidUserId")
		// Exercise
		service.getActivity(context)
		// Verify
		assertEquals(404, context.status)
		assertEquals("user id not found", context.resultText)
	}

	@Test
	fun testGetActivityNullUser() {
		// Exercise
		try {
			service.getActivity(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	fun setupCreateUser(user: User = fixtures.users[3]): String {
		context.returnedUser = user
		service.createUser(context)
		service.listUsers(context)
		return findIdInResult(context.json)
	}

	fun setupCreateActivity(userId: String, activity: Activity = fixtures.activities[0]): String {
		context.params.put("id", userId)
		context.returnedActivity = activity
		service.createActivity(context)
		return findIdInResult(context.json)
	}

	fun findIdInResult(result: String): String {
		var id = result.substring(result.indexOf(", id=") + 5)
		return id.substring(0, id.indexOf(","))
	}

}

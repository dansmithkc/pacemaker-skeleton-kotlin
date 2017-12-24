package controllers

import models.Fixtures
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class PacemakerRestServiceLocationTest {
	lateinit var service: PacemakerRestService
	lateinit var context: TestContextWrapper
	lateinit var fixtures: Fixtures
	lateinit var utility: PacemakerRestServiceTestUtilities

	@Before
	fun setup() {
		service = PacemakerRestService()
		context = TestContextWrapper()
		fixtures = Fixtures()
		utility = PacemakerRestServiceTestUtilities(service, context, fixtures)
	}

	@After
	fun tearDown() {
	}

	@Test
	fun testAddLocationNullUser() {
		// Exercise
		try {
			service.addLocation(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testAddLocationNoActivity() {
		// Setup
		var userId = utility.setupCreateUser()
		context.params.put("id", userId)
		context.params.put("activityId", "no such activity")
		// Exercise
		service.addLocation(context)
		// Verify
		assertEquals(404, context.status)
		assertEquals("no activity id associated with that user", context.resultText)
	}

	@Test
	fun testAddLocationNullActivity() {
		// Setup
		var userId = utility.setupCreateUser()
		context.params.put("id", userId)
		// Exercise
		try {
			service.addLocation(context)
			fail("Did not throw an exception on a null activity")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testAddLocationOneActivityNoLocation() {
		// Setup
		var userId = utility.setupCreateUser()
		var activityId = utility.setupCreateActivity(userId)
		context.params.put("id", userId)
		context.params.put("activityId", activityId)
		// Exercise
		service.addLocation(context)
		// Verify
		assertEquals("{}", context.json)
	}

	@Test
	fun testGetActivityLocationsNullUser() {
		// Exercise
		try {
			service.getActivityLocations(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testGetActivityLocationsNoActivity() {
		// Setup
		var userId = utility.setupCreateUser()
		context.params.put("id", userId)
		context.params.put("activityId", "no such activity")
		// Exercise
		service.getActivityLocations(context)
		// Verify
		assertEquals(404, context.status)
		assertEquals("no activity id associated with that user", context.resultText)
	}

	@Test
	fun testGetActivityLocationsNullActivity() {
		// Setup
		var userId = utility.setupCreateUser()
		context.params.put("id", userId)
		// Exercise
		try {
			service.getActivityLocations(context)
			fail("Did not throw an exception on a null activity")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testGetActivityLocationsOneActivityNoLocation() {
		// Setup
		var userId = utility.setupCreateUser()
		var activityId = utility.setupCreateActivity(userId)
		context.params.put("id", userId)
		context.params.put("activityId", activityId)
		// Exercise
		service.getActivityLocations(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testGetActivityLocationsTwoActivitiesNoLocation() {
		// Setup
		var userId = utility.setupCreateUser()
		var activityId1 = utility.setupCreateActivity(userId, fixtures.activities[0])
		var activityId2 = utility.setupCreateActivity(userId, fixtures.activities[1])
		context.params.put("id", userId)
		context.params.put("activityId", activityId1)
		// Exercise
		service.getActivityLocations(context)
		// Verify
		assertEquals("[]", context.json)
	}

}

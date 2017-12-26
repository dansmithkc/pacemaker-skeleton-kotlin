package controllers

import models.Fixtures
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class PacemakerRestServiceActivityTest {
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
	fun testGetActivitiesNoneForUser() {
		// Setup
		var id = utility.setupCreateUser()
		context.params.put("id", id)
		// Exercise
		service.getActivities(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testGetActivitiesOne() {
		// Setup
		var id = utility.setupCreateUser()
		context.params.put("id", id)
		context.returnedActivity = fixtures.activities[0]
		service.createActivity(context)
		// Exercise
		service.getActivities(context)
		// Verify
		var truncatedResult = utility.removeIdFromResult(context.json)
		assertEquals("[Activity(type=walk, location=fridge, distance=0.001, id=, route=[])]", truncatedResult)
	}

	fun getActivitiesSorted(sortBy: String = "") {
		// Setup
		var id = utility.setupCreateUser()
		context.params.put("id", id)
		fixtures.activities.forEach {
			context.returnedActivity = it
			service.createActivity(context)
		}
		context.queryParams.put("sortBy", sortBy)
		// Exercise
		service.getActivities(context)
	}

	@Test
	fun testGetActivitiesAllNotSorted() {
		// Setup and Exercise
		getActivitiesSorted()
		// Verify
		var activityTypes = utility.listOfActivityFields(context.json, "type=")
		assertEquals(5, activityTypes.size)
	}

	@Test
	fun testGetActivitiesAllSortByType() {
		// Setup and Exercise
		getActivitiesSorted("type")
		// Verify
		var activityTypes = utility.listOfActivityFields(context.json, "type=")
		assertEquals("[cycle, run, walk, walk, walk]", activityTypes.toString())
	}

	@Test
	fun testGetActivitiesAllSortByLocation() {
		// Setup and Exercise
		getActivitiesSorted("location")
		// Verify
		var activityTypes = utility.listOfActivityFields(context.json, "location=")
		assertEquals("[bar, fridge, school, shop, work]", activityTypes.toString())
	}

	@Test
	fun testGetActivitiesAllSortByDistance() {
		// Setup and Exercise
		getActivitiesSorted("distance")
		// Verify
		var activityTypes = utility.listOfActivityFields(context.json, "distance=")
		assertEquals("[0.001, 1.0, 2.2, 2.5, 4.5]", activityTypes.toString())
	}

	@Test
	fun testCreateActivity() {
		// Setup
		var id = utility.setupCreateUser()
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
		var id = utility.setupCreateUser()
		context.params.put("id", id)
		// Exercise
		service.deleteActivities(context)
		// Verify
		assertEquals(204, context.status)
		service.getActivities(context)
		assertEquals("[]", context.json)
	}

	@Test
	fun testDeleteActivitiesOneForUser() {
		// Setup
		var userId = utility.setupCreateUser()
		utility.setupCreateActivity(userId)
		context.params.put("id", userId)
		// Exercise
		service.deleteActivities(context)
		// Verify
		assertEquals(204, context.status)
		service.getActivities(context)
		assertEquals("[]", context.json)
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

	@Test
	fun testGetActivityNoActivity() {
		// Setup
		var userId = utility.setupCreateUser()
		context.params.put("id", userId)
		context.params.put("activityId", "no such activity")
		// Exercise
		service.getActivity(context)
		// Verify
		assertEquals(404, context.status)
		assertEquals("no activity id associated with that user", context.resultText)
	}

	@Test
	fun testGetActivityNullActivity() {
		// Setup
		var userId = utility.setupCreateUser()
		context.params.put("id", userId)
		// Exercise
		try {
			service.getActivity(context)
			fail("Did not throw an exception on a null activity")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testGetActivityOneActivity() {
		// Setup
		var userId = utility.setupCreateUser()
		var activityId = utility.setupCreateActivity(userId)
		context.params.put("id", userId)
		context.params.put("activityId", activityId)
		// Exercise
		service.getActivity(context)
		// Verify
		var truncatedResult = context.json.substring(0, context.json.indexOf(", id"))
		assertEquals("Activity(type=walk, location=fridge, distance=0.001", truncatedResult)
	}

	@Test
	fun testGetActivityTwoActivities() {
		// Setup
		var userId = utility.setupCreateUser()
		var activityId1 = utility.setupCreateActivity(userId, fixtures.activities[0])
		var activityId2 = utility.setupCreateActivity(userId, fixtures.activities[1])
		context.params.put("id", userId)
		context.params.put("activityId", activityId1)
		// Exercise
		service.getActivity(context)
		// Verify
		var truncatedResult = context.json.substring(0, context.json.indexOf(", id"))
		assertEquals("Activity(type=walk, location=fridge, distance=0.001", truncatedResult)
	}

}

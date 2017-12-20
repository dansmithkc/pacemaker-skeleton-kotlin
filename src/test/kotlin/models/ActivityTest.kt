package test.models

import models.Activity
import models.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ActivityTest {
	var activity = Activity()
	var activity2 = Activity()

	@Before
	fun setup() {
		activity = Activity()
		activity2 = Activity()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun constructorDefault() {
		val activity3 = Activity()
		activity3.id = "123"
		activity3.type = "aType"
		activity3.location = "ALocation"
		activity3.distance = 1.2f
		assertEquals(activity3.id, "123")
		assertEquals(activity3.type, "aType")
		assertEquals(activity3.location, "ALocation")

		assertEquals(activity.type, "")
		assertEquals(activity.location, "")
		assertEquals(0.0f, activity.distance, 0.01f)

		// just exercise the accessor for id
		val id = activity.id
		val route = activity.route
	}

	@Test
	fun testToString() {
		assertEquals("Activity(type=, location=, distance=0.0, id=" + activity.id + ", route=[])", activity.toString())
	}

	@Test
	fun useEquals() {
		assertEquals(activity, activity)
		assertNotEquals(activity, activity2)
		assertFalse(activity.equals(activity2))
		assertTrue(activity.equals(activity))
		val wrongObjectType = Any()
		assertFalse(activity.equals(wrongObjectType))
	}

	@Test
	fun useCopy() {
		val activity3 = activity.copy()
	}

	@Test
	fun useHashCode() {
		val code = activity.hashCode()
	}
}
package models

import models.Location
import models.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LocationTest {
	lateinit var location: Location
	lateinit var location2: Location

	@Before
	fun setup() {
		location = Location()
		location2 = Location()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun constructorDefault() {
		val location3 = Location()

		assertEquals(0.0, location.latitude, 0.01)
		assertEquals(0.0, location.longitude, 0.01)
	}

	@Test
	fun testToString() {
		assertEquals("Location(latitude=0.0, longitude=0.0)", location.toString())
	}

	@Test
	fun useEquals() {
		assertEquals(location, location)
		assertEquals(location, location2)
		assertTrue(location.equals(location2))
		assertTrue(location.equals(location))
		val wrongObjectType = Any()
		assertFalse(location.equals(wrongObjectType))
	}

	@Test
	fun useCopy() {
		val location3 = location.copy()
	}

	@Test
	fun useHashCode() {
		val code = location.hashCode()
	}
}
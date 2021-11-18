import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

internal class PatternTest {

    @Test
    fun `test pattern validation`() {
        lateinit var exception: Exception

        exception = assertThrows(IllegalArgumentException::class.java) {
            Pattern("123")
        }
        assertEquals(exception.message, "Pattern should consist of letters or wildcard characters ('*') only: 123")

        exception = assertThrows(IllegalArgumentException::class.java) {
            Pattern("ab*s abs")
        }
        assertEquals(exception.message, "Pattern should consist of letters or wildcard characters ('*') only: ab*s abs")

        exception = assertThrows(IllegalArgumentException::class.java) {
            Pattern("Ha*Ha*Ha  ")
        }
        assertEquals(exception.message, "Pattern should consist of letters or wildcard characters ('*') only: Ha*Ha*Ha  ")

        exception = assertThrows(IllegalArgumentException::class.java) {
            Pattern("123")
        }
        assertEquals(exception.message, "Pattern should consist of letters or wildcard characters ('*') only: 123")

        exception = assertThrows(IllegalArgumentException::class.java) {
            Pattern("    ")
        }
        assertEquals(exception.message, "Pattern is blank")
    }

    @Test
    fun `pattern parsing positive workflow test`() {
        var pattern = Pattern("FoBa")
        assertAll(
            { assertEquals(listOf("Fo", "Ba"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("Fo*Ba")
        assertAll(
            { assertEquals(listOf("Fo*", "Ba"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("fbz")
        assertAll(
            { assertEquals(listOf("F", "B", "Z"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("FBZ")
        assertAll(
            { assertEquals(listOf("F", "B", "Z"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("fBb")
        assertAll(
            { assertEquals(listOf("F", "Bb"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("fooBar")
        assertAll(
            { assertEquals(listOf("Foo", "Bar"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("****")
        assertAll(
            { assertEquals(listOf("****"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("**Foo**Bar*Z*ar")
        assertAll(
            { assertEquals(listOf("**", "Foo**", "Bar*", "Z*ar"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("f*b*b*")
        assertAll(
            { assertEquals(listOf("F*", "B*", "B*"), pattern.patternParsed) },
            { assertFalse(pattern.matchLast) }
        )

        pattern = Pattern("Fo*Ba ")
        assertAll(
            { assertEquals(listOf("Fo*", "Ba"), pattern.patternParsed) },
            { assertTrue(pattern.matchLast) }
        )
    }

    @Test
    fun `pattern matching tests`() {
        val classNameReader = ClassNameReader()

        var pattern = Pattern("FoBa")
        assertTrue(pattern.matches(classNameReader.parseClassName("BooFooBar")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FoBa")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FoaoaoaoaBar")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooZooBar")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBar")))

        assertFalse(pattern.matches(classNameReader.parseClassName("HaHaHA")))
        assertFalse(pattern.matches(classNameReader.parseClassName("FoBBB")))
        assertFalse(pattern.matches(classNameReader.parseClassName("FoBBB")))
        assertFalse(pattern.matches(classNameReader.parseClassName("FoB")))
        assertFalse(pattern.matches(classNameReader.parseClassName("BaFo")))
        assertFalse(pattern.matches(classNameReader.parseClassName("BarFoo")))

        pattern = Pattern("B*rBas*")
        assertTrue(pattern.matches(classNameReader.parseClassName("BarBase")))
        assertTrue(pattern.matches(classNameReader.parseClassName("BarBasBase")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FrBarBrBasBase")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBarBaseTest")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBaaarrbarBasbaseTest")))

        assertFalse(pattern.matches(classNameReader.parseClassName("BarBas")))
        assertFalse(pattern.matches(classNameReader.parseClassName("BrBase")))
        assertFalse(pattern.matches(classNameReader.parseClassName("BarBasBar")))
        assertFalse(pattern.matches(classNameReader.parseClassName("BaseBare")))

        pattern = Pattern("B***")
        assertTrue(pattern.matches(classNameReader.parseClassName("Barbarian")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBarbarianBar")))

        assertFalse(pattern.matches(classNameReader.parseClassName("Bar")))
        assertFalse(pattern.matches(classNameReader.parseClassName("BarBarBar")))

        pattern = Pattern("fbb")
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBarBaz")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FBB")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FBFB")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBB")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBBar")))
        assertTrue(pattern.matches(classNameReader.parseClassName("ZooFooFooBarZooBar")))

        assertFalse(pattern.matches(classNameReader.parseClassName("FB")))
        assertFalse(pattern.matches(classNameReader.parseClassName("BBF")))
        assertFalse(pattern.matches(classNameReader.parseClassName("FfbbBar")))
        assertFalse(pattern.matches(classNameReader.parseClassName("FooBarGaz")))

        pattern = Pattern("f*b*b*")
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBarBaz")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FoBaBa")))
        assertTrue(pattern.matches(classNameReader.parseClassName("GoFoGoBaGoBa")))

        assertFalse(pattern.matches(classNameReader.parseClassName("FBB")))
        assertFalse(pattern.matches(classNameReader.parseClassName("FoBBa")))
        assertFalse(pattern.matches(classNameReader.parseClassName("FoBaGa")))

        pattern = Pattern("BarBaz ")
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBarBaz")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBarFooBaz")))

        assertFalse(pattern.matches(classNameReader.parseClassName("BarBazFoo")))

        pattern = Pattern("***Bar")
        assertTrue(pattern.matches(classNameReader.parseClassName("FooBarBaz")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FFFooBarFooBaz")))
        assertTrue(pattern.matches(classNameReader.parseClassName("FoooooFFFFBarFooBaz")))

        assertFalse(pattern.matches(classNameReader.parseClassName("FFFaFaBarBaz")))
        assertFalse(pattern.matches(classNameReader.parseClassName("Bar")))
        assertFalse(pattern.matches(classNameReader.parseClassName("Barrrrr")))
    }
}
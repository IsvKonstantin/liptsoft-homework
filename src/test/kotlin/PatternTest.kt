import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

internal class PatternTest {

    @Test
    fun `test Pattern validation`() {
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
    fun `parsePattern positive workflow test`() {
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

        pattern = Pattern("Fo*Ba ")
        assertAll(
            { assertEquals(listOf("Fo*", "Ba"), pattern.patternParsed) },
            { assertTrue(pattern.matchLast) }
        )
    }
}
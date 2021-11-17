import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import kotlin.Exception

internal class ClassNameReaderTest {
    private val classNameReader: ClassNameReader = ClassNameReader()
    private val inputPath: String = "src/test/resources/input_sample.txt"

    @Test
    fun `readFromFile positive workflow on valid input`() {
        val expected = listOf(
            ClassName("FooBarBaz", "a.b.FooBarBaz", listOf("Foo", "Bar", "Baz")),
            ClassName("FooBar", "c.d.FooBar", listOf("Foo", "Bar")),
            ClassName("WishMaker", "liptsoft.WishMaker", listOf("Wish", "Maker")),
            ClassName("MindReader", "liptsoft.MindReader", listOf("Mind", "Reader")),
            ClassName("TelephoneOperator", "TelephoneOperator", listOf("Telephone", "Operator")),
            ClassName("ScubaArgentineOperator", "ScubaArgentineOperator", listOf("Scuba", "Argentine", "Operator")),
            ClassName("YoureLeavingUsHere", "YoureLeavingUsHere", listOf("Youre", "Leaving", "Us", "Here")),
            ClassName("YouveComeToThisPoint", "YouveComeToThisPoint", listOf("Youve", "Come", "To", "This", "Point")),
        )
        assertEquals(expected, classNameReader.readFromFile(inputPath))
    }

    @Test
    fun `parseClassName positive workflow test`() {
        assertEquals(
            ClassName("FooBarBaz", "a.b.FooBarBaz", listOf("Foo", "Bar", "Baz")),
            classNameReader.parseClassName("a.b.FooBarBaz")
        )

        assertEquals(
            ClassName("WishMaker", "liptsoft.WishMaker", listOf("Wish", "Maker")),
            classNameReader.parseClassName("liptsoft.WishMaker")
        )

        assertEquals(
            ClassName("FooBarBaz", "FooBarBaz", listOf("Foo", "Bar", "Baz")),
            classNameReader.parseClassName("FooBarBaz")
        )

        assertEquals(
            ClassName("F", "a.b.F", listOf("F")),
            classNameReader.parseClassName("a.b.F")
        )

        assertEquals(
            ClassName("F", "F", listOf("F")),
            classNameReader.parseClassName("F")
        )

        assertEquals(
            ClassName("FBZ", "FBZ", listOf("F", "B", "Z")),
            classNameReader.parseClassName("FBZ")
        )

        assertEquals(
            ClassName("FoBZo", "a.FoBZo", listOf("Fo", "B", "Zo")),
            classNameReader.parseClassName("a.FoBZo")
        )
    }

    @Test
    fun `parseClassName should throw exception on non-valid class name`() {
        lateinit var exception: Exception
        exception = assertThrows(IllegalArgumentException::class.java) {
            classNameReader.parseClassName("a.FoB Zo")
        }
        assertEquals(exception.message, "Class name should consist of letters only: a.FoB Zo")

        exception = assertThrows(IllegalArgumentException::class.java) {
            classNameReader.parseClassName("a.notCamelCase")
        }
        assertEquals(exception.message, "Class name should be in CamelCase: a.notCamelCase")

        exception = assertThrows(IllegalArgumentException::class.java) {
            classNameReader.parseClassName("a.b.c.Cl@ss")
        }
        assertEquals(exception.message, "Class name should consist of letters only: a.b.c.Cl@ss")
    }
}
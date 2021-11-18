import java.io.BufferedReader
import java.io.File

class ClassNameReader {
    fun readFromFile(filepath: String): List<ClassName> {
        return File(filepath)
            .bufferedReader()
            .use(BufferedReader::readLines)
            .filter { it.isNotBlank() }
            .map { parseClassName(it.trim()) }
    }

    fun parseClassName(classNameFull: String): ClassName {
        val lastDotIndex: Int = classNameFull.reversed().indexOf('.')
        val className: String = if (lastDotIndex != -1) classNameFull.takeLast(lastDotIndex) else classNameFull

        validateClassName(className, classNameFull)

        val parsedNames: MutableList<String> = className.parse()

        return ClassName(className, classNameFull, parsedNames)
    }

    private fun validateClassName(className: String, classNameFull: String) {
        when {
            className.isBlank() -> {
                throw IllegalArgumentException("Class name is blank")
            }
            className.first().isLowerCase() -> {
                throw IllegalArgumentException("Class name should be in CamelCase: $classNameFull")
            }
            !className.all { it.isLetter() } -> {
                throw IllegalArgumentException("Class name should consist of letters only: $classNameFull")
            }
        }
    }
}

import java.io.BufferedReader
import java.io.File

class ClassNameReader {
    fun readFromFile(filepath: String): List<ClassName> {
        return File(filepath)
            .bufferedReader()
            .use(BufferedReader::readLines)
            .filter { it.isNotBlank() }
            .map { parseClassName(it) }
    }

    fun parseClassName(classNameFull: String): ClassName {
        val lastDotIndex: Int = classNameFull.reversed().indexOf('.')
        val classNameSuffix: String = if (lastDotIndex != -1) classNameFull.takeLast(lastDotIndex) else classNameFull

        val parsedNames: MutableList<String> = mutableListOf()
        val builder = StringBuilder().append(classNameSuffix.first())
        for (char in classNameSuffix.drop(1)) {
            if (char.isUpperCase()) {
                parsedNames.add(builder.toString())
                builder.clear()
            }
            builder.append(char)
        }
        parsedNames.add(builder.toString())

        return ClassName(classNameSuffix, classNameFull, parsedNames)
    }
}
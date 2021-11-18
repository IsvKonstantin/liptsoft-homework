import java.util.*

class Pattern(private var pattern: String) {
    var matchLast: Boolean = false
    var patternParsed: MutableList<String> = mutableListOf()

    init {
        validatePattern()
        parsePattern()
    }

    fun matches(className: ClassName): Boolean {
        var classIndex = 0
        var patternIndex = 0

        if (matchLast && !className.nameParsed.last().matchesPrefix(patternParsed.last())) {
            return false
        }

        while (patternIndex < patternParsed.size) {
            val classNameWord = className.nameParsed[classIndex]
            val patternWord = patternParsed[patternIndex]

            if (classNameWord.matchesPrefix(patternWord)) {
                patternIndex++
            }

            if (patternIndex == patternParsed.size) {
                return true
            }

            if (classIndex + 1 < className.nameParsed.size) {
                classIndex++
            } else {
                return false
            }
        }

        return true
    }

    private fun parsePattern() {
        if (pattern.last() == ' ') {
            matchLast = true
            pattern = pattern.dropLast(1)
        }

        if (pattern.all { it.isLowerCase() || it == WILDCARD_CHAR }) {
            pattern = pattern.uppercase(Locale.getDefault())
        }

        patternParsed.addAll(pattern.parse())

        if (patternParsed.first().first().isLowerCase()) {
            patternParsed[0] = patternParsed.first().first().uppercase() + patternParsed.first().drop(1)
        }
    }

    private fun validatePattern() {
        if (pattern.isBlank()) {
            throw IllegalArgumentException("Pattern is blank")
        }

        val patternPrefix: String = if (pattern.last().isWhitespace()) pattern.drop(1) else pattern
        if (!patternPrefix.dropLast(1).all { it.isLetter() or (it == WILDCARD_CHAR) }) {
            throw IllegalArgumentException(
                "Pattern should consist of letters or wildcard characters ('$WILDCARD_CHAR') only: $pattern"
            )
        }
    }
}
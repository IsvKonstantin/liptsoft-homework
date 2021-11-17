import java.util.*

class Pattern(private var pattern: String) {
    var matchLast: Boolean = false
    var patternParsed: MutableList<String> = mutableListOf()

    init {
        validatePattern()
        parsePattern()
    }

    fun parsePattern() {
        if (pattern.last() == ' ') {
            matchLast = true
            pattern = pattern.dropLast(1)
        }

        if (pattern.all { it.isLowerCase() }) {
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

        val patternPrefix: String = if (pattern.last() == ' ') pattern.drop(1) else pattern
        if (!patternPrefix.dropLast(1).all { it.isLetter() or (it == '*') }) {
            throw IllegalArgumentException(
                "Pattern should consist of letters or wildcard characters ('*') only: $pattern"
            )
        }
    }
}
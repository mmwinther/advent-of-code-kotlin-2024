private const val DAY_NUMBER = "05"

class Rule(
    private val left: Int,
    private val right: Int,
) {
    operator fun contains(other: Int): Boolean = this.left == other || this.right == other

    fun passes(update: List<Int>): Boolean {
        val firstMatch = update.indexOfFirst { it == this.left }
        val secondMatch = update.indexOfFirst { it == this.right }
        return secondMatch == -1 || firstMatch <= secondMatch
    }

    fun compare(
        leftPage: Int,
        rightPage: Int,
    ): Int =
        when {
            leftPage == this.left && rightPage == this.right -> 1
            leftPage == this.right && rightPage == this.left -> -1
            else -> 0
        }

    companion object {
        fun fromLine(line: String): Rule =
            line.split("|").let {
                Rule(
                    it[0].toInt(),
                    it[1].toInt(),
                )
            }
    }
}

fun extractRules(input: List<String>): List<Rule> =
    input
        .subList(0, input.indexOfFirst { it == "" })
        .map { Rule.fromLine(it) }

fun extractUpdates(input: List<String>): List<List<Int>> =
    input
        .subList(input.indexOfFirst { it == "" } + 1, input.size)
        .map { update -> update.split(",").map { it.toInt() } }

fun fixUpdate(
    update: List<Int>,
    rules: List<Rule>,
): List<Int> =
    update.sortedWith {
        leftPage,
        rightPage,
        ->
        rules
            .first { leftPage in it && rightPage in it }
            .compare(leftPage, rightPage)
    }

fun main() {
    fun part1(input: List<String>): Int {
        val rules = extractRules(input)
        return extractUpdates(input)
            .filter { update ->
                rules
                    .filter { rule -> update.any { it in rule } }
                    .all { rule ->
                        rule.passes(update)
                    }
            }.sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val rules = extractRules(input)
        return extractUpdates(input)
            .filter { update ->
                rules
                    .filter { rule -> update.any { it in rule } }
                    .any { rule ->
                        !rule.passes(update)
                    }
            }.map { fixUpdate(it, rules) }
            .sumOf { it[it.size / 2] }
    }

    val testInput = readInput("Day${DAY_NUMBER}_test")
    part1(testInput)
        .also { println(it) }
        .also { check(it == 143) }
    part2(testInput)
        .also { println(it) }
        .also { check(it == 123) }

    val input = readInput("Day$DAY_NUMBER")
    part1(input).println()
    part2(input).println()
}

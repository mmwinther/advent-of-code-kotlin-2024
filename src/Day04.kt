private const val DAY_NUMBER = "04"

fun main() {
    fun part1(input: List<String>): Int {
        var numberFound = 0
        val xmas = "XMAS"
        for ((i, row) in input.withIndex()) {
            for ((j, startLetter) in row.withIndex()) {
                val currentWord =
                    if (startLetter == xmas[0]) {
                        xmas
                    } else if (startLetter == xmas.reversed()[0]) {
                        xmas.reversed()
                    } else {
                        continue
                    }
                for (direction in listOf(1 to 0, 1 to 1, 0 to 1, -1 to 1)) {
                    for (letterIndex in xmas.indices) {
                        val letter =
                            input
                                .getOrNull(i + (direction.first * letterIndex))
                                ?.getOrNull(j + (direction.second * letterIndex))
                                ?: break
                        if (letter == currentWord[letterIndex]) {
                            if (letterIndex == currentWord.length - 1) {
                                numberFound++
                                break
                            }
                        } else {
                            break
                        }
                    }
                }
            }
        }
        return numberFound
    }

    fun isInBounds(
        pair: Pair<Pair<Pair<Int, Int>, Pair<Int, Int>>, Pair<Pair<Int, Int>, Pair<Int, Int>>>,
        input: List<String>,
    ): Boolean =
        pair.toList().all { direction ->
            direction.toList().all { it.first in input.indices && it.second in input[0].indices }
        }

    fun checkSides(
        match: List<Pair<Char, Char>>,
        sides: List<Pair<Char, Char>>,
    ): Boolean =
        (
            sides[0].first == match[0].first &&
                sides[0].second == match[0].second &&
                sides[1].first == match[1].first &&
                sides[1].second == match[1].second
        ) ||
            (
                sides[0].first == match[1].first &&
                    sides[0].second == match[1].second &&
                    sides[1].first == match[0].first &&
                    sides[1].second == match[0].second
            )

    fun matchFound(
        i: Int,
        j: Int,
        input: List<String>,
    ): Boolean {
        val matches = listOf(('M' to 'M'), ('S' to 'S'))
        val up = (i - 1 to j - 1) to (i - 1 to j + 1)
        val down = (i + 1 to j - 1) to (i + 1 to j + 1)
        val left = (i - 1 to j - 1) to (i + 1 to j - 1)
        val right = (i - 1 to j + 1) to (i + 1 to j + 1)
        for (pair in listOf(up to down, left to right)) {
            if (!isInBounds(pair, input)) {
                return false
            }
            if (checkSides(
                    matches,
                    listOf(
                        input[pair.first.first.first][pair.first.first.second] to
                            input[pair.first.second.first][pair.first.second.second],
                        input[pair.second.first.first][pair.second.first.second] to
                            input[pair.second.second.first][pair.second.second.second],
                    ),
                )
            ) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>): Int {
        var numberFound = 0
        for ((i, row) in input.withIndex()) {
            for ((j, startLetter) in row.withIndex()) {
                if (startLetter != 'A') continue
                if (matchFound(i, j, input)) numberFound++
            }
        }
        return numberFound
    }

    val testInput = readInput("Day${DAY_NUMBER}_test")
    part1(testInput)
        .also { println(it) }
        .also { check(it == 18) }
    part2(testInput)
        .also { println(it) }
        .also { check(it == 9) }

    val input = readInput("Day$DAY_NUMBER")
    part1(input).println()
    part2(input).println()
}

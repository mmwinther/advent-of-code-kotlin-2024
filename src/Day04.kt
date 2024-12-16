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

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("Day${DAY_NUMBER}_test")
    check(part1(testInput) == 18)
//    check(part2(testInput) == 48)
//
    val input = readInput("Day$DAY_NUMBER")
    part1(input).println()
//    part2(input).println()
}

private const val DAY_NUMBER = "03"

fun main() {
    /**
     * Wade through all the gross nullability
     */
    fun extractMulValues(match: MatchResult?): Pair<Int, Int> =
        match?.groups?.let {
            (
                it[1]
                    ?.value
                    ?.toInt() ?: 1
            ) to
                (
                    it[2]
                        ?.value
                        ?.toInt() ?: 1
                )
        } ?: (1 to 1)

    fun part1(input: List<String>): Int =
        Regex("mul\\((\\d+),(\\d+)\\)")
            .findAll(input.joinToString())
            .map {
                extractMulValues(it)
            }.sumOf {
                it.first * it.second
            }

    fun part2(input: List<String>): Int {
        val muls =
            Regex("mul\\((\\d+),(\\d+)\\)")
                .findAll(input.joinToString())
        val dos =
            Regex("do\\(\\)")
                .findAll(input.joinToString())
        val donts =
            Regex("don't\\(\\)")
                .findAll(input.joinToString())
        return muls
            .filter { mul ->
                val lastDont = donts.findLast { mul.range.first > it.range.first }
                lastDont == null ||
                    dos.any { `do` -> `do`.range.first in lastDont.range.first..mul.range.first }
            }.map {
                extractMulValues(it)
            }.sumOf {
                it.first * it.second
            }
    }

    val testInput = readInput("Day${DAY_NUMBER}_Part02_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("Day$DAY_NUMBER")
    part1(input).println()
    part2(input).println()
}

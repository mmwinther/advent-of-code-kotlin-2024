import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int =
        input
            .map { it.split("   ") }
            .map { it.first().toInt() to it.last().toInt() }
            .unzip()
            .let { columns ->
                columns.first
                    .sorted()
                    .zip(columns.second.sorted())
                    .sumOf { abs(it.first - it.second) }
            }

    fun part2(input: List<String>): Int =
        input
            .map { it.split("   ") }
            .map { it.first().toInt() to it.last().toInt() }
            .unzip()
            .let { columns ->
                columns.first.sumOf { leftValue ->
                    leftValue *
                        columns.second.count { rightValue -> rightValue == leftValue }
                }
            }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

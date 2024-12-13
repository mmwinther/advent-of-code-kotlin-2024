import kotlin.math.abs

const val DAY_NUMBER = "02"

fun main() {
    fun String.parseReport(): List<Int> =
        this
            .split(" ")
            .map { it.toInt() }

    fun List<Int>.isSafe(): Boolean =
        this
            .zipWithNext()
            .map { levelsPair -> levelsPair.first - levelsPair.second }
            .let { differences ->
                differences.all { abs(it) in 1..3 } &&
                    (differences.all { it > 0 } || differences.all { it < 0 })
            }

    fun part1(input: List<String>): Int = input.count { report -> report.parseReport().isSafe() }

    fun part2(input: List<String>): Int =
        input.count { reportString ->
            reportString
                .parseReport()
                .let { report -> List(report.size) { idx -> report.subList(0, idx) + report.subList(idx + 1, report.size) } }
                .any { it.isSafe() }
        }

    val testInput = readInput("Day${DAY_NUMBER}_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day$DAY_NUMBER")
    part1(input).println()
    part2(input).println()
}

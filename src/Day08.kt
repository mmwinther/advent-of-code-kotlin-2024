private const val DAY_NUMBER = "08"

data class Point(
    val x: Int,
    val y: Int,
) {
    operator fun minus(other: Point): Point = Point(this.x - other.x, this.y - other.y)

    operator fun plus(other: Point): Point = Point(this.x + other.x, this.y + other.y)

    operator fun compareTo(other: Point): Int = this.minus(other).let { it.x + it.y }
}

fun main() {
    fun findAntennae(input: List<String>): Map<Char, List<Point>> =
        input
            .flatMapIndexed {
                y: Int,
                row: String,
                ->
                row.mapIndexedNotNull { x, c -> if (c == '.') null else c to Point(x, y) }
            }.groupBy({ it.first }, { it.second })

    fun calculateAntinodes(
        antennae: List<Point>,
        antinodeCreator: (a: Point, b: Point) -> List<Point>,
    ): List<Point> =
        antennae
            .mapIndexed { index, point ->
                antennae
                    .toMutableList()
                    .also { it.removeAt(index) }
                    .map { other ->
                        antinodeCreator(point, other)
                    }.flatten()
            }.flatten()

    fun inside(
        it: Point,
        input: List<String>,
    ): Boolean = it.y in input.indices && it.x in input[0].indices

    fun calculateGCD(
        a: Int,
        b: Int,
    ): Int {
        var num1 = a
        var num2 = b
        while (num2 != 0) {
            val temp = num2
            num2 = num1 % num2
            num1 = temp
        }
        return num1
    }

    fun simplifyDifference(diff: Point): Point = calculateGCD(diff.x, diff.y).let { Point(diff.x / it, diff.y / it) }

    fun part1(input: List<String>): Int =
        findAntennae(input)
            .asSequence()
            .map {
                calculateAntinodes(it.value) { point, other ->
                    (point - other).let { diff ->
                        listOf(
                            point + diff,
                            other - diff,
                        )
                    }
                }
            }.flatten()
            .filter { inside(it, input) }
            .distinct()
            .count()

    fun part2(input: List<String>): Int =
        findAntennae(input)
            .asSequence()
            .map {
                calculateAntinodes(it.value) { point, other ->
                    simplifyDifference((point - other)).let { diff ->
                        println("${it.key} $point $other $diff")
                        var newPoint: Point = point
                        val antinodes: MutableList<Point> = mutableListOf(point)
                        while (inside(newPoint, input)) {
                            antinodes.add(newPoint)
                            newPoint += diff
                        }
                        newPoint = point - diff
                        while (inside(newPoint, input)) {
                            antinodes.add(newPoint)
                            newPoint -= diff
                        }
                        println(antinodes)
                        return@let antinodes
                    }
                }
            }.flatten()
            .filter { inside(it, input) }
            .distinct()
            .count()

    val testInput = readInput("Day${DAY_NUMBER}_test")
    part1(testInput)
        .also { println(it) }
        .also { check(it == 14) }
    part2(testInput)
        .also { println(it) }
        .also { check(it == 34) }

    val input = readInput("Day$DAY_NUMBER")
    part1(input).println()
    part2(input).println()
}

private const val DAY_NUMBER = "06"

enum class Direction(
    val increment: Pair<Int, Int>,
) {
    UP(0 to -1),
    RIGHT(1 to 0),
    DOWN(0 to 1),
    LEFT(-1 to 0),
}

fun turn(direction: Direction) =
    when (direction) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }

fun main() {
    val start = '^'
    val empty = '.'
    val obstacle = '#'
    val visited = "X"

    fun findInitialPosition(input: List<String>): MutableList<Int> =
        mutableListOf(input.first { it.contains(start) }.indexOfFirst { it == start }, input.indexOfFirst { it.contains(start) })

    operator fun List<String>.contains(element: MutableList<Int>): Boolean = element[0] in this.indices && element[1] in this[0].indices

    fun part1(input: List<String>): Int {
        val map = input.toMutableList()
        var direction = Direction.UP
        var position = findInitialPosition(map)
        var nextPosition = position // Initialise, no movement yet
        var numVisited = 1
        while (nextPosition in map) {
//            println("$direction $position ${map[position[1]][position[0]]} $nextPosition ${map[nextPosition[1]][nextPosition[0]]}")
            if (map[nextPosition[1]][nextPosition[0]] == obstacle) {
                direction = turn(direction)
                nextPosition = mutableListOf(position[0] + direction.increment.first, position[1] + direction.increment.second)
                println("Turn to $direction")
            }
            if (map[position[1]][position[0]] in listOf(empty, start)) {
                numVisited++
                map[position[1]] = map[position[1]].replaceRange(position[0], position[0] + 1, visited)
                println("Visited $position")
            }
            position = nextPosition
            nextPosition = mutableListOf(position[0] + direction.increment.first, position[1] + direction.increment.second)
        }
        return numVisited
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("Day${DAY_NUMBER}_test")
    part1(testInput)
        .also { println(it) }
        .also { check(it == 41) }
//    part2(testInput)
//        .also { println(it) }
//        .also { check(it == 123) }

    val input = readInput("Day$DAY_NUMBER")
    part1(input).println()
//    part2(input).println()
}

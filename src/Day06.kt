private const val DAY_NUMBER = "06"

enum class Direction(
    val increment: Pair<Int, Int>,
) {
    UP(0 to -1),
    RIGHT(1 to 0),
    DOWN(0 to 1),
    LEFT(-1 to 0),
}

data class Position(
    val x: Int,
    val y: Int,
) {
    fun stepForward(direction: Direction): Position = Position(this.x + direction.increment.first, this.y + direction.increment.second)
}

fun main() {
    val start = '^'
    val empty = '.'
    val obstacle = '#'
    val visited = "X"

    fun turn(direction: Direction) =
        when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }

    fun findInitialPosition(input: List<String>): Position =
        input
            .zip(input.indices)
            .first { valueIndexPair ->
                valueIndexPair.first.contains(start)
            }.let { valueIndexPair ->
                Position(valueIndexPair.first.indexOfFirst { it == start }, valueIndexPair.second)
            }

    operator fun List<String>.contains(position: Position): Boolean = position.y in this.indices && position.x in this[0].indices

    fun part1(input: List<String>): Int {
        val map = input.toMutableList()
        var direction = Direction.UP
        var position = findInitialPosition(map)
        var nextPosition = position // Initialise, no movement yet
        var numVisited = 1
        while (nextPosition in map) {
//            println("$direction $position ${map[position[1]][position[0]]} $nextPosition ${map[nextPosition[1]][nextPosition[0]]}")
            if (map[nextPosition.y][nextPosition.x] == obstacle) {
                direction = turn(direction)
                nextPosition = position.stepForward(direction)
                println("Turn to $direction")
            }
            if (map[position.y][position.x] in listOf(empty, start)) {
                numVisited++
                map[position.y] = map[position.y].replaceRange(position.x, position.x + 1, visited)
                println("Visited $position")
            }
            position = nextPosition
            nextPosition = position.stepForward(direction)
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

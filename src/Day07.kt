import kotlin.math.max
import kotlin.math.pow

private const val DAY_NUMBER = "07"

val productLambda: (Long, Long) -> Long = { left: Long, right: Long -> left.times(right) }
val sumLambda: (Long, Long) -> Long = { left: Long, right: Long -> left.plus(right) }
val concatLambda: (Long, Long) -> Long = { left: Long, right: Long -> left.toString().plus(right.toString()).toLong() }

class Operator(
    val name: String,
    val op: (Long, Long) -> Long,
    val char: Char,
)

val product = Operator("Product", productLambda, '0')
val sum = Operator("Sum", sumLambda, '1')
val concat = Operator("Concat", concatLambda, '2')

fun main() {
    /**
     * Generate all possible operator combinations.
     *
     * Uses n-ary counting to ensure all combinations are covered
     *
     * @param k the number of operands - 1
     * @param operators the available unique operators
     * @return
     */
    fun generatePossibleOperatorCombinations(
        k: Int,
        operators: Map<Char, Operator>,
    ): List<List<Operator>> =
        List(
            operators.size
                .toDouble()
                .pow(k)
                .toInt(),
        ) { outerIndex ->
            outerIndex.toString(operators.size).padStart(k, '0').map {
                operators[it]!!
            }
        }

    fun equationTrue(
        line: String,
        operators: List<Operator>,
    ): Boolean {
        val equation = line.split(": ")
        val result = equation[0].toLong()
        val operands =
            equation[1]
                .split(" ")
                .map { it.toLong() }
        return generatePossibleOperatorCombinations(operands.size - 1, operators.associateBy { it.char })
            .any { possibleSolution ->
                operands.reduceIndexed { i: Int, sum: Long, operand: Long -> possibleSolution[max(0, i - 1)].op(sum, operand) } ==
                    result
            }
    }

    fun part1(
        input: List<String>,
        operators: List<Operator>,
    ): Long =
        input
            .filter { equationTrue(it, operators) }
            .sumOf { it.split(":")[0].toLong() }

    fun part2(
        input: List<String>,
        operators: List<Operator>,
    ): Long =
        input
            .filter { equationTrue(it, operators) }
            .sumOf { it.split(":")[0].toLong() }

    val testInput = readInput("Day${DAY_NUMBER}_test")
    part1(testInput, listOf(product, sum))
        .also { println(it) }
        .also { check(it == 3749L) }
    part2(testInput, listOf(product, sum, concat))
        .also { println(it) }
        .also { check(it == 11387L) }

    val input = readInput("Day$DAY_NUMBER")
    part1(input, listOf(product, sum)).println()
    part2(input, listOf(product, sum, concat)).println()
}

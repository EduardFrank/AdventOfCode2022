fun main() {

    val scores = mapOf(
        'Y' to 2, // 'B'
        'X' to 1, // 'A'
        'Z' to 3  // 'C'
    )

    fun play(elfe: Char, you: Char): Int {
        return when (elfe) {
            'A' -> {
                when (you) {
                    'X' -> return 3
                    'Y' -> return 6
                    else -> 0
                }
            }
            'B' -> {
                when (you) {
                    'Y' -> return 3
                    'Z' -> return 6
                    else -> 0
                }
            }
            'C' -> {
                when (you) {
                    'Z' -> return 3
                    'X' -> return 6
                    else -> 0
                }
            }
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (elfe, you) = it.split(" ").map { it[0] }
            scores[you]!! + play(elfe, you)
        }
    }

    fun part2(input: List<String>): Int {
        val win = mapOf(
            'A' to 'Y',
            'B' to 'Z',
            'C' to 'X'
        )
        val draw = mapOf(
            'A' to 'X',
            'B' to 'Y',
            'C' to 'Z'
        )
        val loose = mapOf(
            'A' to 'Z',
            'B' to 'X',
            'C' to 'Y'
        )

        return input.sumOf {
            var (elfe, you) = it.split(" ").map { it[0] }

            when (you) {
                'X' -> { // Loose
                    you = loose[elfe]!!
                }
                'Y' -> { // Draw
                    you = draw[elfe]!!
                }
                'Z' -> { // Win
                    you = win[elfe]!!
                }
            }

            scores[you]!! + play(elfe, you)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

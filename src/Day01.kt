fun main() {
    fun parse(input: List<String>): List<Int> {
        val elves = mutableListOf<Int>()

        var calories = 0
        for (line in input) {
            if (line.isEmpty()) {
                elves.add(calories)
                calories = 0
                continue
            }
            calories += line.toInt()
        }
        elves.add(calories)

        return elves
    }

    fun part1(input: List<String>): Int {
        return parse(input).max()
    }

    fun part2(input: List<String>): Int {
        return parse(input).sortedDescending().subList(0, 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

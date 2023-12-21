fun main() {

    fun contains(a: IntRange, b: IntRange): Boolean {
        return (a.first >= b.first && a.first <= b.last && a.last >= b.first && a.last <= b.last)
    }

    fun overlaps(a: IntRange, b: IntRange): Boolean {
        return a.last >= b.first && b.last >= a.first
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (a, b) = it.split(",")
            val (aS, aE) = a.split("-").map { it.toInt() }
            val (bS, bE) = b.split("-").map { it.toInt() }

            if (contains(aS..aE, bS..bE) ||
                contains(bS..bE, aS..aE))
                1.toInt()
            else
                0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (a, b) = it.split(",")
            val (aS, aE) = a.split("-").map { it.toInt() }
            val (bS, bE) = b.split("-").map { it.toInt() }

            if (overlaps(aS..aE, bS..bE) ||
                overlaps(bS..bE, aS..aE))
                1.toInt()
            else
                0
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

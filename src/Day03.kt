fun main() {

    fun commonType(strs: List<String>): Char {
        var ret = strs[0].toSet()
        for (i in 1 until strs.size) {
            ret = ret.intersect(strs[i].toSet())
        }
        return ret.first()
    }

    fun score(c: Char): Int {
        if (c in 'a'..'z') {
            return (c - 'a') + 1
        }
        return (c - 'A') + 27
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            score(commonType(listOf(
                it.substring(0..<it.length / 2), it.substring(it.length / 2)
            )))
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf {
            score(commonType(it))
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

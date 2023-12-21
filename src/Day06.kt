fun main() {

    fun findMarker(s: String, n: Int): Int {
        val set = mutableMapOf<Char, Int>()
        for (i in 0 until n) {
            set[s[i]] = set.getOrDefault(s[i], 0) + 1
        }

        for (i in n until s.length) {
            val c = s[i]

            set[c] = set.getOrDefault(s[i], 0) + 1
            if (set.entries.size == n + 1) {
                return i + 1
            }

            set[s[i - n]] = set[s[i - n]]!! - 1
            if (set[s[i - n]] == 0)
                set.remove(s[i - n])
        }

        return s.length
    }

    fun part1(input: List<String>): Int {
        return findMarker(input[0], 3)
    }

    fun part2(input: List<String>): Int {
        return findMarker(input[0], 13)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

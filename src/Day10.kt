
fun main() {
    fun parseInstructions(input: List<String>): List<String> {
        return buildList {
            for (line in input) {
                if (line.startsWith("add")) {
                    add("noop")
                    add(line)
                } else {
                    add(line)
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val instructions = parseInstructions(input)
        var ret = 0
        var X = 1
        var cycle = 1

        for (instruction in instructions) {
            cycle++

            if (instruction != "noop") {
                val (_, value) = instruction.split(" ")
                X += value.toInt()
            }

            if (cycle == 20 || cycle > 20 && (cycle + 20) % 40 == 0) {
                ret += cycle * X
            }
        }

        return ret
    }

    fun part2(input: List<String>): Int {
        val instructions = parseInstructions(input)
        var X = 1
        var cycle = 1

        var pos = 0
        val lines = mutableListOf<CharArray>()
        var line = CharArray(40)
        for (instruction in instructions) {
            if (pos in (X - 1)..(X + 1)) {
                line[pos] = '#'
            } else {
                line[pos] = '.'
            }

            if (cycle % 40 == 0) {
                lines.add(line)
                line = CharArray(40)
                pos = 0
            } else {
                pos++
            }

            cycle++

            if (instruction != "noop") {
                val (_, value) = instruction.split(" ")
                X += value.toInt()
            }
        }

        lines.forEach {
            println(it.joinToString(""))
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

fun main() {

    fun part1(input: List<String>): String {
        val stacks = mutableListOf<MutableList<Char>>()

        var k = 0
        while (k < input.size) {
            val line = input[k]
            if (line.isEmpty()) {
                k++
                continue
            }

            if (line.startsWith("move")) {
                val splits = line.split(" ")
                val count = splits[1].toInt()
                val from  = splits[3].toInt()
                val to    = splits[5].toInt()

                for (i in 0 until count) {
                    val t = stacks[from - 1].removeLast()
                    stacks[to - 1].add(t)
                }
            } else if (!line.contains("[")) {
                val nums = line.trim().split(" ").filter { it.isNotEmpty() }
                for (i in nums.indices) {
                    val tmp = mutableListOf<Char>()

                    for (j in k - 1 downTo 0) {
                        val c = if (i == 0) 1 else 1 + (i) * 4
                        if (input[j][c] in 'A'..'Z') {
                            tmp.add(input[j][c])
                        }
                    }

                    stacks.add(tmp)
                }
            }

            k++
        }

        return stacks.map { it.last() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val stacks = mutableListOf<MutableList<Char>>()

        var k = 0
        while (k < input.size) {
            val line = input[k]
            if (line.isEmpty()) {
                k++
                continue
            }

            if (line.startsWith("move")) {
                val splits = line.split(" ")
                val count = splits[1].toInt()
                val from  = splits[3].toInt()
                val to    = splits[5].toInt()

                val u = mutableListOf<Char>()
                for (i in 0 until count)
                    u.add(stacks[from - 1].removeLast())
                for (x in u.reversed())
                    stacks[to - 1].add(x)
            } else if (!line.contains("[")) {
                val nums = line.trim().split(" ").filter { it.isNotEmpty() }
                for (i in nums.indices) {
                    val tmp = mutableListOf<Char>()

                    for (j in k - 1 downTo 0) {
                        val c = if (i == 0) 1 else 1 + (i) * 4
                        if (input[j][c] in 'A'..'Z') {
                            tmp.add(input[j][c])
                        }
                    }

                    stacks.add(tmp)
                }
            }

            k++
        }

        return stacks.map { it.last() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

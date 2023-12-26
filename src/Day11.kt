
fun main() {
    fun part(input: List<String>, rounds: Int, worryLevel: Int): Long {
        val items = mutableListOf<MutableList<Long>>()
        val divs = mutableListOf<Long>()
        val ifTrue = mutableListOf<Int>()
        val ifFalse = mutableListOf<Int>()
        val opMul = mutableListOf<Int>()
        val opAdd = mutableListOf<Int>()

        val itemCount = mutableMapOf<Int, Int>()

        var monkeys = 0
        for (i in 0 until input.size) {
            if (input[i].startsWith("Monkey")) {
                val tmp = Regex("[0-9]+").findAll(input[i + 1]).map(MatchResult::value).toList().map { it.toLong() }.toMutableList()
                items.add(tmp)

                if (input[i + 2].contains("old * old")) {
                    opAdd.add(0)
                    opMul.add(-1)
                } else if (input[i + 2].contains("*")) {
                    opAdd.add(0)
                    val value = Regex("[0-9]+").findAll(input[i + 2]).map(MatchResult::value).toList().map { it.toInt() }.first()
                    opMul.add(value)
                } else if (input[i + 2].contains("+")) {
                    opMul.add(1)
                    val value = Regex("[0-9]+").findAll(input[i + 2]).map(MatchResult::value).toList().map { it.toInt() }.first()
                    opAdd.add(value)
                }

                val t2 = Regex("[0-9]+").findAll(input[i + 3]).map(MatchResult::value).toList().map { it.toLong() }.first()
                divs.add(t2)

                val trueMonkey = Regex("[0-9]+").findAll(input[i + 4]).map(MatchResult::value).toList().map { it.toInt() }.first()
                val falseMonkey = Regex("[0-9]+").findAll(input[i + 5]).map(MatchResult::value).toList().map { it.toInt() }.first()

                ifTrue.add(trueMonkey)
                ifFalse.add(falseMonkey)

                itemCount[monkeys++] = 0
            }
        }

        val m = divs.reduce { acc, l -> acc * l }
        for (round in 0 until rounds) {
            for (monkey in 0 until monkeys) {
                for (item in items[monkey]) {
                    itemCount[monkey] = itemCount[monkey]!! + 1

                    var level = item

                    if (opMul[monkey] == -1) {
                        level *= level
                    } else {
                        level += opAdd[monkey]
                        level *= opMul[monkey]
                    }

                    val newLevel = (level / worryLevel) % m
                    if (newLevel % divs[monkey] == 0L) {
                        items[ifTrue[monkey]].add(newLevel)
                    } else {
                        items[ifFalse[monkey]].add(newLevel)
                    }
                }
                items[monkey].clear()
            }
        }

        val ret = itemCount.values.sortedDescending().take(2)
        return ret[0].toLong() * ret[1].toLong()
    }

    fun part1(input: List<String>): Long {
        return part(input, 20, 3)
    }

    fun part2(input: List<String>): Long {
        return part(input, 10000, 1)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}


fun main() {
    class Parser {
        private var pos = 0

        fun parseExpression(s: String): Any? {
            if (pos >= s.length) return null

            if (s[pos].isDigit()) {
                var num = 0
                while (pos < s.length && s[pos].isDigit()) {
                    num = 10 * num + (s[pos] - '0')
                    pos++
                }
                return num
            } else if (s[pos] == '[') {
                var ret = mutableListOf<Any>()
                pos++
                var tmp = parseExpression(s)
                while (tmp != null) {
                    ret.add(tmp)
                    tmp = parseExpression(s)
                }
                pos++
                return ret
            } else if (s[pos] == ',') {
                pos++
                return parseExpression(s)
            }

            return null
        }
    }

    fun compare(a: Any, b: Any): Int {
        if (a is Int && b is Int) {
            return a - b
        } else if (a is List<*> && b is List<*>) {
            for (i in 0 until minOf(a.size, b.size)) {
                val tmp = compare(a[i]!!, b[i]!!)
                if (tmp != 0) {
                    return tmp
                }
            }
            return a.size - b.size
        } else if (a is List<*> && b is Int) {
            return compare(a, listOf(b))
        } else if (a is Int && b is List<*>) {
            return compare(listOf(a), b)
        }

        return 0
    }

    fun part1(input: List<String>): Int {
        var ret = 0
        val pairs = input.filter { it.isNotEmpty() }.chunked(2)

        for (i in pairs.indices) {
            val a = pairs[i][0]
            val b = pairs[i][1]

            val aa = Parser().parseExpression(a)
            val bb = Parser().parseExpression(b)

            val tmp = compare(aa!!, bb!!)
            println("$a vs $b")
            if (tmp < 0) {
                ret += i + 1
                println("right order")
            } else {
                println("not right order")
            }
            println()
        }

        return ret
    }

    fun part2(input: List<String>): Int {
        data class Marker(val a: Any, val mark: Boolean = false)

        val items = input.filter { it.isNotEmpty() }.map { Marker(Parser().parseExpression(it)!!) }.toMutableList()
        items.add(Marker(Parser().parseExpression("[[2]]")!!, true))
        items.add(Marker(Parser().parseExpression("[[6]]")!!, true))

        val sortedItems = items.sortedWith { a, b -> compare(a.a, b.a) }
        var ret = 1
        for (i in sortedItems.indices) {
            if (sortedItems[i].mark) {
                ret *= i + 1
            }
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}

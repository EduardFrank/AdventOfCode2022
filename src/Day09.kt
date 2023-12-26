import kotlin.math.abs

fun main() {

    fun distance(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2).toDouble()).toInt()
    }

    val DX = intArrayOf(-1, 0, 1,  0)
    val DY = intArrayOf( 0, 1, 0, -1)

    val DX2 = intArrayOf(-1, -1, 1, 1)
    val DY2 = intArrayOf(-1,  1, 1,-1)

    fun partHelp(input: List<String>, slope: Int): Int {
        val DXM = mapOf("R" to 1, "L" to -1, "U" to 0, "D" to 0)
        val DYM = mapOf("R" to 0, "L" to  0, "U" to -1, "D" to 1)

        val Y = 100000
        var hX = 0
        var hY = 0
        var tX = 0
        var tY = 0

        var map = mutableMapOf<Int, Boolean>()
        map[tY * Y + tX] = true

        for (line in input) {
            val splits = line.split(" ")
            val dir = splits[0]
            val dist = splits[1].toInt()

            for (k in 0 until dist) {
                hX += DXM[dir]!!
                hY += DYM[dir]!!

                if (distance(hX, hY, tX, tY) > slope) {
                    var ddx = DX2
                    var ddy = DY2
                    if (tX == hX || tY == hY) {
                        ddx = DX
                        ddy = DY
                    }

                    for (i in ddx.indices) {
                        val x = tX + ddx[i]
                        val y = tY + ddy[i]

                        if (distance(hX, hY, x, y) <= slope) {
                            tX = x
                            tY = y
                            break
                        }
                    }
                }

                map[tY * Y + tX] = true
            }
        }

        return map.entries.size
    }

    fun part1(input: List<String>): Int {
        val ret = partHelp(input, 1)
        return ret
    }

    fun part2(input: List<String>): Int {
        val ret = partHelp(input, 9)
        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == 36)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

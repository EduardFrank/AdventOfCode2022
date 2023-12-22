
fun main() {

    fun part1(input: List<String>): Int {
        val G = input.map { it.toCharArray().map { it - '0' } }

        val R = G.size
        val C = G[0].size

        val D = Array(4) { Array(R) { BooleanArray(C)} }
        for (y in 1 until R - 1) {
            var maxL = G[y][0]
            var maxR = G[y][C - 1]
            for (x in 1 until C - 1) {
                D[0][y][x] = G[y][x] > maxL
                D[1][y][C - 1 - x] = G[y][C - 1 - x] > maxR
                maxL = maxOf(maxL, G[y][x])
                maxR = maxOf(maxR, G[y][C - 1 - x])
            }
        }
        for (x in 1 until C - 1) {
            var maxT = G[0][x]
            var maxB = G[R - 1][x]
            for (y in 1 until R - 1) {
                D[2][y][x] = G[y][x] > maxT
                D[3][R - 1 - y][x] = G[R - 1 - y][x] > maxB
                maxT = maxOf(maxT, G[y][x])
                maxB = maxOf(maxB, G[R - 1 - y][x])
            }
        }

        var ret = 2 * R + 2 * (C - 2)
        for (y in 1 until R - 1) {
            for (x in 1 until C - 1) {
                if (D.any { it[y][x] })
                    ret++
            }
        }

        return ret
    }

    fun part2(input: List<String>): Int {
        val G = input.map { it.toCharArray().map { it - '0' } }

        val R = G.size
        val C = G[0].size

        fun leftCount(y: Int, x: Int): Int {
            var ret = 1
            for (xx in x - 1 downTo 1) {
                if (G[y][xx] >= G[y][x]) break
                ret++
            }
            return ret
        }

        fun rightCount(y: Int, x: Int): Int {
            var ret = 1
            for (xx in x + 1 until C - 1) {
                if (G[y][xx] >= G[y][x]) break
                ret++
            }
            return ret
        }

        fun topCount(y: Int, x: Int): Int {
            var ret = 1
            for (yy in y - 1 downTo 1) {
                if (G[yy][x] >= G[y][x]) break
                ret++
            }
            return ret
        }

        fun bottomCount(y: Int, x: Int): Int {
            var ret = 1
            for (yy in y + 1 until R - 1) {
                if (G[yy][x] >= G[y][x]) break
                ret++
            }
            return ret
        }

        var ret = Int.MIN_VALUE
        for (y in 1 until R - 1) {
            for (x in 1 until C - 1) {
                val score = (leftCount(y, x) * rightCount(y, x) * topCount(y, x) * bottomCount(y, x))
                ret = maxOf(ret, score)
            }
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

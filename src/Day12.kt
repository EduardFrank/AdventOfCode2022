import java.util.PriorityQueue

fun main() {

    val DX = intArrayOf(-1, 0, 1, 0)
    val DY = intArrayOf( 0, 1, 0,-1)

    fun shortest(G: List<CharArray>, sX: Int, sY: Int, eX: Int, eY: Int): Int {
        data class Node(val x: Int, val y: Int, val d: Int, val px: Int, val py: Int)
        var D = Array(G.size) { IntArray(G[0].size) {Int.MAX_VALUE} }

        val pq = PriorityQueue<Node> { a, b -> a.d - b.d }
        pq.add(Node(sX, sY, 0, -1, -1))

        while (pq.isNotEmpty()) {
            val n = pq.poll()

            if (D[n.y][n.x] <= n.d) continue

            D[n.y][n.x] = n.d

            if (n.x == eX && n.y == eY) {
                return n.d
            }

            for (i in DX.indices) {
                val x = n.x + DX[i]
                val y = n.y + DY[i]

                if (x < 0 || x >= G[0].size || y < 0 || y >= G.size) continue

                val c = G[n.y][n.x]
                val c2 = G[y][x]

                if (c + 1 >= c2) {
                    pq.add(Node(x, y, n.d + 1, n.x, n.y))
                }
            }
        }

        return Int.MAX_VALUE
    }

    fun part1(input: List<String>): Int {
        var eX = 0
        var eY = 0
        var sX = 0
        var sY = 0

        var G = input.map { it.toCharArray() }

        for (i in 0 until G.size) {
            for (j in 0 until G[i].size) {
                if (G[i][j] == 'S') {
                    G[i][j] = 'a'
                    sX = j
                    sY= i
                } else if (G[i][j] == 'E') {
                    eX = j
                    eY = i
                    G[i][j] = 'z'
                }
            }
        }

        return shortest(G, sX, sY, eX, eY)
    }

    fun part2(input: List<String>): Int {
        var eX = 0
        var eY = 0

        var sP = mutableListOf<Int>()

        var G = input.map { it.toCharArray() }

        for (i in 0 until G.size) {
            for (j in 0 until G[i].size) {
                if (G[i][j] == 'S' || G[i][j] == 'a') {
                    G[i][j] = 'a'
                    sP.add(i * G[0].size + j)
                } else if (G[i][j] == 'E') {
                    eX = j
                    eY = i
                    G[i][j] = 'z'
                }
            }
        }

        var ret = Int.MAX_VALUE
        for (s in sP) {
            val x = s % G[0].size
            val y = s / G[0].size

            ret = minOf(ret, shortest(G, x, y, eX, eY))
        }
        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}

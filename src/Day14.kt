
fun main() {

    data class Line(val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        var ret = 0
        var maxX = Int.MIN_VALUE
        var minX = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        var lines = mutableListOf<MutableList<Line>>()

        for (line in input) {
            val splits = line.split("->")
            var tmp = mutableListOf<Line>()
            for (split in splits) {
                val (x, y) = split.trim().split(",").map { it.toInt() }

                maxX = maxOf(maxX, x)
                minX = minOf(minX, x)
                maxY = maxOf(maxY, y)

                tmp.add(Line(x, y))
            }
            lines.add(tmp)
        }

        val newLines = lines.map { it.map { Line(it.x - minX, it.y) } }

        val R = maxY + 1
        val C = maxX - minX + 1

        val G = Array(R) { CharArray(C) {'.'} }
        for (tmp in newLines) {
            for (i in 1 until tmp.size) {
                if (tmp[i - 1].x == tmp[i].x) {
                    for (y in minOf(tmp[i].y, tmp[i - 1].y) .. maxOf(tmp[i].y, tmp[i -1].y)) {
                        G[y][tmp[i].x] = '#'
                    }
                } else {
                    for (x in minOf(tmp[i].x, tmp[i - 1].x) .. maxOf(tmp[i].x, tmp[i - 1].x)) {
                        G[tmp[i].y][x] = '#'
                    }
                }
            }
        }

        val sX = 500 - minX
        val sY = 0
        G[sY][sX] = '+'

        while (true) {
            var x = sX
            var y = sY

            var finish = false
            while (true) {
                if (y + 1 >= R || x - 1 < 0 || x + 1 >= C) {
                    finish = true
                    break
                }

                if (G[y + 1][x] == '.') {
                    y++
                } else if (G[y + 1][x - 1] == '.') {
                    x--
                } else if (G[y + 1][x + 1] == '.') {
                    x++
                } else {
                    break
                }
            }

            if (finish) {
                break
            }

            if (y >= R || x < 0 || x >= C) {
                break
            }

            G[y][x] = '0'
        }

        for (g in G) {
            println(g.joinToString(""))
        }

        for (g in G) {
            ret += g.count { it == '0' }
        }

        return ret
    }

    fun part2(input: List<String>): Int {
        var ret = 0
        var maxX = Int.MIN_VALUE
        var minX = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        var lines = mutableListOf<MutableList<Line>>()

        val paddingX = 160

        for (line in input) {
            val splits = line.split("->")
            var tmp = mutableListOf<Line>()
            for (split in splits) {
                val (x, y) = split.trim().split(",").map { it.toInt() }

                maxX = maxOf(maxX, x)
                minX = minOf(minX, x)
                maxY = maxOf(maxY, y)

                tmp.add(Line(x, y))
            }
            lines.add(tmp)
        }

        val newLines = lines.map { it.map { Line(it.x - minX, it.y) } }

        val R = maxY + 3
        val C = maxX - minX + 1 + 2 * paddingX

        val G = Array(R) { CharArray(C) {'.'} }
        for (tmp in newLines) {
            for (i in 1 until tmp.size) {
                if (tmp[i - 1].x == tmp[i].x) {
                    for (y in minOf(tmp[i].y, tmp[i - 1].y) .. maxOf(tmp[i].y, tmp[i -1].y)) {
                        G[y][tmp[i].x+paddingX] = '#'
                    }
                } else {
                    for (x in minOf(tmp[i].x, tmp[i - 1].x) .. maxOf(tmp[i].x, tmp[i - 1].x)) {
                        G[tmp[i].y][x+paddingX] = '#'
                    }
                }
            }
        }

        for (x in 0 until G[0].size) {
            G[G.size - 1][x] = '#'
        }

        val sX = 500 - minX + paddingX
        val sY = 0
        G[sY][sX] = '0'

        while (true) {
            var x = sX
            var y = sY

            var finish = false
            while (true) {
                if (y + 1 >= R || x - 1 < 0 || x + 1 >= C) {
                    finish = true
                    break
                }

                if (G[y + 1][x] == '.') {
                    y++
                } else if (G[y + 1][x - 1] == '.') {
                    x--
                } else if (G[y + 1][x + 1] == '.') {
                    x++
                } else {
                    break
                }
            }

            if (x == sX && y == sY) {
                break
            }

            if (finish) {
                break
            }

            if (y >= R || x < 0 || x >= C) {
                break
            }

            G[y][x] = '0'
        }

        for (g in G) {
            println(g.joinToString(""))
        }

        for (g in G) {
            ret += g.count { it == '0' }
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}


sealed class Item(val name: String);

class Dir(val dirName: String) : Item(dirName) {
    var items = mutableMapOf<String, Item>()

    fun add(item: Item) {
        if (items.containsKey(item.name)) return

        items[item.name] = item
    }
}

class File(val fileName: String, val size: Int): Item(fileName) {}

fun main() {

    fun sizes(item: Item, dirSizes: MutableMap<String, Int>): Int {
        if (item is Dir) {
            var ret = 0

            for (i in item.items.values) {
                ret += sizes(i, dirSizes)
            }

            dirSizes[item.dirName] = ret

            return ret
        } else if (item is File) {
            return item.size
        }
        return 0
    }

    fun partHelp(input: List<String>): MutableMap<String, Int> {
        val dirsMap = mutableMapOf<String, Dir>()

        val dirs = mutableListOf<String>()
        var curDir: Dir? = null

        for (line in input) {
            if (line[0] == '$') {
                val splits = line.split(" ")
                if (splits[1] == "cd") {
                    if (splits[2] == "..") {
                        dirs.removeLast()
                        val path = dirs.joinToString("/")
                        curDir = dirsMap[path]
                    } else {
                        dirs.add(splits[2])
                        val path = dirs.joinToString("/")

                        var newDir: Dir?
                        if (dirsMap.containsKey(path)) {
                            newDir = dirsMap[path]
                        } else {
                            newDir = Dir(path)
                            dirsMap[path] = newDir
                        }
                        curDir?.add(newDir!!)
                        curDir = newDir
                    }
                } else if (splits[1] == "ls") {
                    // skip
                }
            } else {
                val splits = line.split(" ")
                if (splits[0] == "dir") {
                    val path = (dirs + splits[1]).joinToString("/")
                    var newDir: Dir?
                    if (dirsMap.containsKey(path)) {
                        newDir = dirsMap[path]
                    } else {
                        newDir = Dir(path)
                        dirsMap[path] = newDir
                    }

                    // DIR
                    curDir?.add(newDir!!)
                } else {
                    // FILE
                    val size = splits[0].toInt()
                    val name = splits[1]

                    curDir?.add(File(name, size))
                }
            }
        }

        val sizes = mutableMapOf<String, Int>()
        sizes(dirsMap["/"]!!, sizes)

        return sizes
    }

    fun part1(input: List<String>): Int {
        return partHelp(input).values.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val sizes = partHelp(input)
        val neededSize = 30000000 - (70000000 - sizes["/"]!!)

        return sizes.entries
            .filter { it.value >= neededSize }
            .sortedBy { it.value }
            .map { it.value }
            .first()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

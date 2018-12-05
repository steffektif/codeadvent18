import java.io.File

fun main(args: Array<String>) {
    val input = File("resources/day05").readLines().map { it.trim() }[0]
    val whole = StringBuilder(input)

    println("part one: ${partOne(whole)}")
    println("part two: ${partTwo(whole)}")
}

fun partTwo(whole: StringBuilder): Int {
    var char = 'a'
    var smallest = Int.MAX_VALUE
    while(char <= 'z') {
        val length = partOne(StringBuilder(whole.toString().replace(char.toString(), "", true)))
        if(length < smallest) {
            smallest = length
        }
        ++char
    }
    return smallest
}

private fun partOne(whole: StringBuilder): Int {
    while (true) {
        var changes = false

        for (i in (0 until whole.length - 1)) {
            if (whole[i].toLowerCase() == whole[i + 1].toLowerCase() && whole[i] != whole[i + 1]) {
                whole.delete(i, i + 2)
                changes = true
                break
            }
        }

        if (!changes) {
            break
        }
    }

    return whole.length
}


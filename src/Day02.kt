import java.io.File

var twos = 0
var threes = 0

fun checksum(input :List<String>): Int {
    input.map { it.trim() }.map { it.split("") }.map { find(it) }
    return twos * threes
}

fun find(chars: List<String>) {
    val sequence = chars.toString()
    var foundTwo = false
    var foundThree = false
    for (char in chars) {
        val count = "($char)".toRegex().findAll(sequence).count()
        when (count) {
            2 -> {
                foundTwo = true
            }
            3 -> {
                foundThree = true
            }
            else -> {
                // do nothing
            }
        }
        if (foundThree && foundTwo) {
            break
        }
    }
    if (foundTwo) {
        twos++
    }
    if (foundThree) {
        threes++
    }
}

fun main(args: Array<String>) {
    val input = File("resources/day02").readLines()
    println(checksum(input)) //4712
}
import java.io.File

private var twos = 0
private var threes = 0

private var solution = ""

fun checksum(input :List<String>): Int {
    input.map { it.trim() }.map { it.split("") }.map { findTwosAndThrees(it) }
    return twos * threes
}

fun findTwosAndThrees(chars: List<String>) {
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


private fun findPartner(input: List<String>) {
    for (s in input) {
        input.find { compare(s, it) }.orEmpty()
    }
}

private fun compare(one: String, another: String): Boolean {
    var mismatches = 0
    var misMatchIndex = 0
    for (index in one.indices) {
        if (one[index] != another[index]) {
            misMatchIndex = index
            mismatches++
        }
        if (mismatches > 1) {
            return false
        }
    }
    if (mismatches == 0) {
        return false
    }
    solution = one.removeRange(misMatchIndex, misMatchIndex + 1)
    return true // exactly one mismatch
}

fun main(args: Array<String>) {
    val input = File("resources/day02").readLines().map { it.trim() }
//    println(checksum(input)) //4712
    findPartner(input)
    println(solution)
}
import java.io.File

val input = File("resources/day01").readLines()

fun frequency() {
    println(input.map { number -> number.toInt() }.reduce { sum, element -> sum + element })
}

val seenFrequencies = mutableSetOf<Int>()

fun extended_frequency() {
    var frequency = 0
    for (next in input.map { it.toInt() }) {
        frequency =+ next
    }

}

fun main(args: Array<String>) {
    frequency()
}
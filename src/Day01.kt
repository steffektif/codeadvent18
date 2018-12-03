import java.io.File

private var frequency = 0

private fun frequency(input: List<Int>) {
    println(input.reduce { sum, element -> sum + element })
}

private val seenFrequencies = mutableSetOf<Int>()

fun extendedFrequency(input: List<Int>) : Int {
    for (next in input) {
        println("current frequency = $frequency , change of $next ")
        if (!seenFrequencies.contains(frequency)) {
          seenFrequencies.add(frequency)
        } else {
          return frequency
        }
        frequency += next
    }
    return Int.MAX_VALUE
}

fun main(args: Array<String>) {
    val input = File("resources/day01").readLines().map { it.toInt() }
        var seens = Int.MAX_VALUE
    while (seens == Int.MAX_VALUE) {
        seens = extendedFrequency(input)
    }
    println(seens)
}
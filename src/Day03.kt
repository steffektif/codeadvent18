import java.io.File

private val idRegex = "(#\\d+)".toRegex()
private val coordinatesRegex = "(\\d){1,3},(\\d){1,3}".toRegex()
private val spaceRegex = "(\\d){1,3}x(\\d){1,3}".toRegex()

private val fabric = mutableMapOf<Pair<Int, Int>, MutableSet<String>>()

//#130 @ 501,636: 21x12

fun main(args: Array<String>) {
    val input = File("resources/day03").readLines().map { it.trim() }
    val map = input.map {
        Claim(
            idRegex.find(it)!!.value,
            buildPair(coordinatesRegex.find(it), ','),
            buildPair(spaceRegex.find(it), 'x')
        )
    }.map {
        runOverFabric(it)
    }.filter {
        checkClaim(it)
    }
    println(fabric.filterValues { it.size > 1 }.count()) //solution a
    println(map) // b
}

fun checkClaim(claim: Claim): Boolean {
    for (x in claim.position.first until claim.position.first + claim.size.first) {
        for (y in claim.position.second until claim.position.second + claim.size.second) {
            if (fabric[Pair(x, y)]!!.count() > 1) {
                return false
            }
        }
    }
    return true
}

fun runOverFabric(claim: Claim): Claim {
    for (x in claim.position.first until claim.position.first + claim.size.first) {
        for (y in claim.position.second until claim.position.second + claim.size.second) {
            fabric.getOrPut(Pair(x, y)) { mutableSetOf(claim.id) }.add(claim.id)
        }
    }
    return claim
}

fun buildPair(line: MatchResult?, delimiter: Char): Pair<Int, Int> {
    return Pair(line!!.value.split(delimiter)[0].toInt(), line.value.split(delimiter)[1].toInt())
}

data class Claim(val id: String, val position: Pair<Int, Int>, val size: Pair<Int, Int>)
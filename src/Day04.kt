import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

private val dateRegex = "\\[(.*)\\]".toRegex()
private var guardMap: MutableMap<Int, GuardSleepSchedule> = mutableMapOf()

fun main(args: Array<String>) {
    val input = File("resources/day04").readLines().map { it.trim() }
    var schedule: GuardSleepSchedule? = null
    parseInput(input)
        .sortedBy { it.timestamp }
        .forEach {
            when {
                it.message.startsWith("G") -> {
                    val key = it.message.split(" ")[1].drop(1).toInt()
                    schedule = guardMap.getOrPut(key) { GuardSleepSchedule(key) }
                }
                it.message.startsWith("f") -> // falls asleep
                    schedule!!.sleep(it.timestamp)
                else -> // wakes up
                    schedule!!.wakeUp(it.timestamp)
            }
        }
    // solution a
    println("----------- solution a ------------")
    val guard = guardMap.values.maxBy { it.sumUp() }
    println("id: ${guard!!.id}") // 409
    val counter = mutableMapOf<Int, Int>()
    guardMap[guard.id]!!.sleeps.map { IntRange(it.from, it.to) }.forEach {
        for (i in it) {
            counter.computeIfAbsent(i) { 1 }
            counter.computeIfPresent(i) { _, value -> value + 1 }
        }
    }
    val minuteOfMaxSleep = counter.maxBy { it.value }
    println("minute slept most: ${minuteOfMaxSleep!!.key} with ${minuteOfMaxSleep.value} minutes of sleep")
    println("solution: ${minuteOfMaxSleep.key * guard.id}")

    //solution b
    println("----------- solution b ------------")
    val guardWithMostMinutes = guardMap.map { it.value.minuteSleptMost() }.maxBy { it.minutes.second }
    println("guard id: $guardWithMostMinutes")
    println("solution: ${guardWithMostMinutes!!.id * guardWithMostMinutes.minutes.first}")
}

//[1518-09-28 00:56] wakes up
fun parseInput(input: List<String>): List<LogEntry> {
    return input.map {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        LogEntry(
            LocalDateTime.parse(dateRegex.find(it)!!.value.replace("[", "").replace("]", ""), formatter),
            it.split("]")[1].trim()
        )
    }
}

data class GuardSleepSchedule(val id: Int, val sleeps: MutableList<Sleep> = mutableListOf()) {
    private lateinit var start: LocalDateTime
    private lateinit var end: LocalDateTime

    fun sleep(time: LocalDateTime) {
        this.start = time
    }

    fun wakeUp(time: LocalDateTime) {
        this.end = time
        sleeps.add(Sleep(start.minute, end.minute - 1, start.until(end, ChronoUnit.MINUTES).toInt()))
    }

    fun minuteSleptMost(): GuardWithMinutes {
        val maxMins = mutableMapOf<Int, Int>()
        sleeps.map { IntRange(it.from, it.to) }.forEach {
            for (i in it) {
                maxMins.computeIfAbsent(i) { 1 }
                maxMins.computeIfPresent(i) { _, value -> value + 1 }
            }
        }
        if (maxMins.isEmpty()) {
            return GuardWithMinutes(id, Pair(0,0))
        }
        return GuardWithMinutes(id, maxMins.maxBy { it.value }!!.toPair())
    }

    fun sumUp(): Int {
        return sleeps.sumBy { it.sum }
    }

}

data class GuardWithMinutes(val id: Int, val minutes: Pair<Int, Int>)
data class Sleep(val from: Int, val to: Int, val sum: Int)
data class LogEntry(val timestamp: LocalDateTime, val message: String)
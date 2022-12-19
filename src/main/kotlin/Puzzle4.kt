import java.io.File

fun puzzle4Part1(args: Array<String>) {
    var sum = 0
    File(args[0])
        .readLines()
        .forEach {
            val (elf1, elf2) = it.split(",").map { assignment -> assignment.getRangeBoundaries() }
            if (elf1.isContainedBy(elf2) || elf2.isContainedBy(elf1)) {
                sum += 1
            }
        }
    println(sum)
}

fun puzzle4Part2(args: Array<String>) {
    var sum = 0
    File(args[0])
        .readLines()
        .forEach {
            val (elf1, elf2) = it.split(",").map { assignment -> assignment.getRangeBoundaries() }
            if (elf1.isOverlapBy(elf2) || elf2.isOverlapBy(elf1)) {
                sum += 1
            }
        }
    println(sum)
}

private fun String.getRangeBoundaries(): Pair<Int, Int> {
    val (first, second) = this.split("-").mapNotNull { it.toIntOrNull() }
    return Pair(first, second)
}

private fun Pair<Int, Int>.isContainedBy(other: Pair<Int, Int>): Boolean {
    return this.first >= other.first && this.second <= other.second
}

private fun Pair<Int, Int>.isOverlapBy(other: Pair<Int, Int>): Boolean {
    val compareRange = other.first..other.second
    return this.first in compareRange || this.second in compareRange
}
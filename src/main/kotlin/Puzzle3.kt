import java.io.File

fun puzzle3Part1(args: Array<String>) {
    var sum = 0
    File(args[0])
        .readLines()
        .forEach {
            val commonItem = it.toCharArray().getCommonItem()
            if (commonItem != null) {
                sum += commonItem.toPriorityPoint()
            }
        }
    println(sum)
}

private fun CharArray.getCommonItem(): Char? {
    // as the requirement, the array item always have size is even number
    // and always have common item
    val compartmentSize = this.size / 2
    val compartmentLeft = this.take(compartmentSize).toHashSet()
    val compartmentRight = this.takeLast(compartmentSize).toHashSet()
    compartmentLeft.forEach {
        if (it in compartmentRight) return it
    }
    return null
}

fun puzzle3Part2(args: Array<String>) {
    var sum = 0
    File(args[0])
        .readLines()
        .chunked(3)
        .forEach {
            val groupBadge = it.getGroupBadge()
            if (groupBadge != null) {
                sum += groupBadge.toPriorityPoint()
            }
        }
    println(sum)
}

private fun List<String>.getGroupBadge(): Char? {
    val (rucksack1, rucksack2, rucksack3) = this.map { it.toCharArray().toHashSet() }
    return when {
        rucksack1.size * 2 < rucksack2.size + rucksack3.size -> {
            getGroupBadge(rucksack1, rucksack2, rucksack3)
        }
        rucksack2.size * 2 < rucksack1.size + rucksack3.size -> {
            getGroupBadge(rucksack2, rucksack1, rucksack3)
        }
        else -> {
            getGroupBadge(rucksack3, rucksack1, rucksack2)
        }
    }
}

private fun getGroupBadge(rucksack1: HashSet<Char>, rucksack2: HashSet<Char>, rucksack3: HashSet<Char>): Char? {
    rucksack1.forEach {
        if (it in rucksack2 && it in rucksack3) return it
    }
    return null
}

private fun Char.toPriorityPoint(): Int {
    return if (this.isUpperCase()) {
        this.code - 38
    } else this.code - 96
}
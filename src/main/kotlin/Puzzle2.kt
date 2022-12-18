import java.io.File

private val gameShape = CircleSet<Shape>().apply {
    addAll(Shape.values())
}

fun puzzle2(args: Array<String>) {
    var sumScore = 0
    File(args[0]).readLines().forEach {
        val (opponent, me) = it.split(" ")
        val opponentShape = Shape.from(opponent)
        val myShape = Shape.from(me)
        sumScore += getCompareScore(myShape, opponentShape) + myShape.score
    }
    println(sumScore)
}


private fun getCompareScore(me: Shape, opponent: Shape): Int {
    return when (gameShape.compare(me, opponent)) {
        -1 -> 0
        0 -> 3
        1 -> 6
        else -> throw Exception("Invalid compare")
    }
}

private enum class Shape(val score: Int) {
    ROCK(1),
    SCISSORS(3),
    PAPER(2);

    companion object {
        fun from(value: String): Shape {
            return when (value) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw Exception("Don't know the shape type")
            }
        }
    }
}

private class CircleSet <E>: MutableSet<E> by mutableSetOf() {

    fun compare(source: E, target: E): Int {
        val sourceIndex = indexOf(source)
        val targetIndex = indexOf(target)
        if (sourceIndex == -1 || targetIndex == -1) {
            throw Exception("Invalid compare elements")
        }
        if (sourceIndex == targetIndex) return 0
        if (sourceIndex == size - 1 && targetIndex == 0) return 1
        if (sourceIndex == 0 && targetIndex == size - 1) return -1
        return if (sourceIndex > targetIndex) {
            -1
        } else 1
    }
}
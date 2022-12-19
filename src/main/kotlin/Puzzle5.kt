import java.io.File

private val numberRegex = "\\d+".toRegex()

/**
 * With this puzzle we do not use stack data structure in part 1, even though it is maybe easier.
 * Because I want to keep most common logic from both part of the puzzle.
 */
fun puzzle5(args: Array<String>, isOnWrongShip: Boolean) {
    val graphStacks = args[0]
    val moveStrategy = args[1]

    val graphMatrix = graphStacks.getGraphMatrix()

    File(moveStrategy)
        .readLines()
        .map {
            it.getCraneMove().mapWithArrayIndex()
        }
        .forEach {
            if (isOnWrongShip) {
                moveCraneOn9000Ship(graphMatrix, it)
            } else {
                moveCraneOn9001Ship(graphMatrix, it)
            }
        }

    println(
        graphMatrix.mapNotNull { it.lastOrNull() }.joinToString("")
    )
}

private fun moveCraneOn9000Ship(
    graphMatrix: List<MutableList<Char>>,
    moveStrategy: Triple<Int, Int, Int>
) {
    val (quantity, from, to) = moveStrategy
    repeat(quantity) { _ ->
        graphMatrix[from].lastOrNull()?.let { item ->
            graphMatrix[to].add(item)
            graphMatrix[from].removeLast()
        }
    }
}

private fun moveCraneOn9001Ship(
    graphMatrix: List<MutableList<Char>>,
    moveStrategy: Triple<Int, Int, Int>
) {
    val (quantity, from, to) = moveStrategy
    graphMatrix[to].addAll(graphMatrix[from].takeLast(quantity))
    repeat(quantity) {
        graphMatrix[from].removeLastOrNull()
    }
}

private fun String.getGraphMatrix(): List<MutableList<Char>> {
    val graphMatrix = mutableListOf<MutableList<Char>>()
    File(this)
        .readLines()
        .map {
            it.toCharArray()
        }
        .forEach { row ->
            for ((columnIndex, rowIndex) in (1 until row.size step 4).withIndex()) {
                if (columnIndex > graphMatrix.lastIndex) {
                    graphMatrix.add(mutableListOf())
                }
                val item = row[rowIndex]
                if (item.isLetter()) {
                    graphMatrix[columnIndex].add(0, item)
                }
            }
        }
    return graphMatrix
}

private fun String.getCraneMove(): Triple<Int, Int, Int> {
    val (first, second, third) = numberRegex
        .findAll(this)
        .mapNotNull { it.value.toIntOrNull() }
        .toList()
    return Triple(first, second, third)
}

private fun Triple<Int, Int, Int>.mapWithArrayIndex(): Triple<Int, Int, Int> {
    return Triple(
        first = this.first,
        second = this.second - 1,
        third = this.third - 1
    )
}


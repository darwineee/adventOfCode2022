import java.io.File

fun puzzle20(args: Array<String>) {
    val circleList = CircleList(
        File(args[0])
            .readLines()
            .map { it.toInt() }
    )
    for (i in 0..circleList.lastIndex) {
        circleList.swap(circleList[i])
    }
    val locationOfFirstZero = circleList.indexOfFirst { it == 0 } + 1
    val number1 = circleList[circleList.getLocationOf(1000, locationOfFirstZero)]
    val number2 = circleList[circleList.getLocationOf(2000, locationOfFirstZero)]
    val number3 = circleList[circleList.getLocationOf(3000, locationOfFirstZero)]
    println(number1 + number2 + number3)
}

private fun List<Int>.getLocationOf(value: Int, zeroLocation: Int): Int = (value / zeroLocation) % this.size

class CircleList(list: List<Int>) : MutableList<Int> by mutableListOf() {

    init {
        addAll(list)
    }

    fun swap(value: Int) {
        val currentIndex = indexOf(value)
        val gapIndex = currentIndex + value
        val targetIndex = if (gapIndex < 0) {
            gapIndex + lastIndex
        } else if (gapIndex > lastIndex) {
            gapIndex - lastIndex
        } else {
            gapIndex
        }
        val temp = this[currentIndex]
        this[currentIndex] = this[targetIndex]
        this[targetIndex] = temp
    }
}
import java.io.File

fun puzzle1(args: Array<String>) {
    var sum = 0
    var max = 0

    File(args[0])
        .readLines()
        .forEach {
            val number = it.toIntOrNull()
            if (number != null) {
                sum += number
            } else {
                if (sum > max) {
                    max = sum
                }
                sum = 0
            }
        }

    println(max)
}
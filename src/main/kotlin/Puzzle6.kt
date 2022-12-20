import java.io.File

const val PACKET_MARKER_SIZE = 4
const val MESSAGE_MARKER_SIZE = 14
fun puzzle6(args: Array<String>, markerSize: Int) {
    var endMarkerIndex = 0
    val signals = File(args[0])
        .readText()
        .windowed(markerSize)

    for ((index, signal) in signals.withIndex()) {
        if (signal.toSet().size == markerSize) {
            endMarkerIndex = index + markerSize
            break
        }
    }
    println(endMarkerIndex)
}
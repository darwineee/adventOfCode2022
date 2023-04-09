package puzzle7

import java.io.File

fun puzzle7(args: Array<String>) {
    val fileExplorer = FileExplorer()
    File(args[0])
        .readLines()
        .forEach { command ->
            fileExplorer.execute(command)
        }

    println("Part 1: the sum of the total sizes of those directories")
    val limitDirectoryCapacity = 100_000L
    println(fileExplorer.getDirectoriesCapacityWithLimit(limitDirectoryCapacity))

    println()

    println("Part 2: the total size of that directory")
    val floorLimitDirectoryCapacity = 8_381_165L
    println(fileExplorer.getDirectoryCapacityWithFloorLimit(floorLimitDirectoryCapacity))
}
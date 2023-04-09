package puzzle7

interface CommandExecutor {
    fun execute(command: String)
}

class FileExplorer(): CommandExecutor {

    private val fileExplorerTree = Tree(
        initialCapacity = 1000,
        loadFactor = 0.75F,
    )

    fun getDirectoriesCapacityWithLimit(limit: Long): Long {
        return fileExplorerTree
            .getAllNodes()
            .mapNotNull {
                val size = fileExplorerTree.getNodeSize(it.hashAddress)
                if (size <= limit) size else null
            }
            .sum()
    }

    fun getDirectoryCapacityWithFloorLimit(limit: Long): Long {
        return fileExplorerTree
            .getAllNodes()
            .mapNotNull {
                val size = fileExplorerTree.getNodeSize(it.hashAddress)
                if (size >= limit) size else null
            }
            .min()
    }

    override fun execute(command: String) {
        val commandElements = command.split(SPACE_CHARACTER)
        if (commandElements[0].isCommand()) {
            when (commandElements[1]) {
                Command.CD.value -> executeChangeDirectoryCommand(commandElements[2])
                Command.LS.value -> executeListFilesCommand()
            }
        } else {
            if (commandElements[0].isDirectory()) {
                addDirectory(commandElements[1])
            } else {
                addFile(
                    fileSize = commandElements[0].toLongOrNull() ?: 0L,
                    fileName = commandElements[1]
                )
            }
        }
    }

    private fun executeChangeDirectoryCommand(
        directoryName: String
    ) {
        when (directoryName) {
            Argument.ROOT.value -> {
                // Do nothing
            }
            Argument.PARENT_DIRECTORY.value -> {
                val currentDirectory = fileExplorerTree.getCurrentNode()
                fileExplorerTree.navigateTo(currentDirectory.parentAddress)
            }
            else -> {
                val childDirectory = fileExplorerTree.getChildNodeInCurrentNode().firstOrNull { it.name == directoryName }
                if (childDirectory != null) {
                    fileExplorerTree.navigateTo(childDirectory.hashAddress)
                }
            }
        }
    }

    private fun executeListFilesCommand() {
        // Do nothing
    }

    private fun addDirectory(directoryName: String) {
        val currentDirectory = fileExplorerTree.getCurrentNode()
        val newDirectory = Node(
            name = directoryName,
            parentAddress = currentDirectory.hashAddress
        )
        fileExplorerTree.addNode(newDirectory)
        currentDirectory.childrenAddress.add(newDirectory.hashAddress)
    }

    private fun addFile(fileSize: Long, fileName: String) {
        val currentDirectory = fileExplorerTree.getCurrentNode()
        val newFile = Leaf(
            name = fileName,
            size = fileSize,
            parentAddress = currentDirectory.hashAddress
        )
        fileExplorerTree.addLeaf(newFile)
        currentDirectory.childrenAddress.add(newFile.hashAddress)
    }

    private fun String.isCommand() = this == Command.COMMAND_SIGN.value

    private fun String.isDirectory() = this == InfoPrefix.DIR.value

    enum class Command(val value: String) {
        CD("cd"),
        LS("ls"),
        COMMAND_SIGN("$");
    }

    enum class Argument(val value: String) {
        ROOT("/"),
        PARENT_DIRECTORY("..")
    }

    enum class InfoPrefix(val value: String) {
        DIR("dir")
    }

    companion object {
        const val SPACE_CHARACTER = " "
    }
}
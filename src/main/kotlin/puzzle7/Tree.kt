package puzzle7

import kotlin.collections.HashMap

abstract class TreeElement {
    abstract val hashAddress: String
    abstract val name: String
}

open class Node(
    override val name: String,
    val parentAddress: String,
) : TreeElement() {
    override val hashAddress: String
        get() = name + parentAddress

    val childrenAddress = mutableSetOf<String>()
}

class Root : Node(
    name = "root",
    parentAddress = ""
)

data class Leaf(
    override val name: String,
    val size: Long,
    val parentAddress: String,
) : TreeElement() {
    override val hashAddress: String
        get() = name + size + parentAddress
}

class Tree(
    initialCapacity: Int,
    loadFactor: Float,
) {

    private val treeStorage = HashMap<String, TreeElement>(initialCapacity, loadFactor)

    private var currentAddress: String = ""

    private val rootElement = Root()

    init {
        addRootNode()
    }

    private fun addRootNode() {
        addNode(rootElement)
        currentAddress = rootElement.hashAddress
    }

    fun addNode(node: Node) {
        treeStorage.getOrPut(node.hashAddress) { node }
    }

    fun addLeaf(leaf: Leaf) {
        treeStorage.getOrPut(leaf.hashAddress) { leaf }
    }

    fun navigateTo(address: String) {
        currentAddress = address
    }

    fun getCurrentNode() = getNode(currentAddress)

    private fun getNode(address: String) = if (address == "") {
        rootElement
    } else {
        (treeStorage[address] as? Node)
            ?: throw IllegalArgumentException("Current address is not a Node or Root.")
    }

    fun getChildNodeInCurrentNode(): List<Node> {
        return getChildrenInNode(currentAddress)
            .filterIsInstance<Node>()
    }

    private fun getChildrenInNode(address: String): List<TreeElement> {
        return getNode(address).childrenAddress.mapNotNull { treeStorage[it] }
    }

    fun getNodeSize(address: String): Long {
        val children = getChildrenInNode(address)
        var sizeOfLeavesInNode = 0L
        for (child in children) {
            sizeOfLeavesInNode += if (child is Leaf) {
                child.size
            } else {
                getNodeSize(child.hashAddress)
            }
        }
        return sizeOfLeavesInNode
    }

    fun getAllNodes() = treeStorage.values.filterIsInstance<Node>()
}
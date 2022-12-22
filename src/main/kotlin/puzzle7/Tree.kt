package puzzle7

import java.util.*
import kotlin.collections.HashMap

abstract class TreeElement {
    abstract val hashAddress: String
    abstract val name: String
}

object Root : TreeElement() {
    override val name: String
        get() = ""
    override val hashAddress: String
        get() = hashCode().toString() + UUID.randomUUID().toString()
}

data class Node(
    override val name: String,
    val parentAddress: String,
    val childrenAddress: String
) : TreeElement() {
    override val hashAddress: String
        get() = hashCode().toString() + UUID.randomUUID().toString()
}

data class Leaf(
    override val name: String,
    val size: Long,
    val parentAddress: String,
) : TreeElement() {
    override val hashAddress: String
        get() = hashCode().toString() + UUID.randomUUID().toString()
}

class Tree (
    initialCapacity: Int,
    loadFactor: Float,
): HashMap<String, TreeElement>(initialCapacity, loadFactor) {

    lateinit var currentAddress: String
    private set

    init {
        addRootNode()
    }

    private fun addRootNode() {
        this[Root.hashAddress] = Root
        currentAddress = Root.hashAddress
    }

    fun addNode(node: puzzle7.Node) {
        this[node.hashAddress] = node
    }

    fun addLeaf(leaf: Leaf) {
        this[leaf.hashAddress] = leaf
    }

    fun navigateTo(address: String) {
        currentAddress = address
    }
}

fun Tree.getTotalCapacityOfLeaves(): Long {
    return this.values.sumOf {
        if (it is Leaf) it.size else 0
    }
}
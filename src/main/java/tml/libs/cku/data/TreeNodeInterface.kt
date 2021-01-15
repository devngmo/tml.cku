package tml.libs.cku.data

interface TreeNodeInterface {
    var parent : TreeNodeInterface?
    val childs : ArrayList<TreeNodeInterface>
    val name:String
    val data: Any?

    fun add(child : TreeNodeInterface)
}

open class TreeNodeBase(override var parent: TreeNodeInterface?, override val name: String) : TreeNodeInterface {
    val _childs = ArrayList<TreeNodeInterface>()

    override val childs: ArrayList<TreeNodeInterface>
        get() = _childs

    var _data: Any?= null
    override val data: Any?
        get() = _data

    override fun add(child: TreeNodeInterface) {
        _childs.add(child)
        child.parent = this
    }

}
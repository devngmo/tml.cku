package tml.libs.cku.data

class TreeNode(var key:String, var value:Any?=null) {
    private var _parent : TreeNode? = null
    val childs = arrayListOf<TreeNode>()

    fun attach(parent: TreeNode) {
        _parent = parent
        parent.childs.add(this)
    }

    fun detach() {
        _parent?.let {
            it.childs.remove(this)
            _parent = null
        }
    }

    fun toHashMap(): HashMap<String, Any?> {
        val map = hashMapOf<String, Any?>()
        for (c in childs) {
            if (c.value == null)
                map[c.key] = null
            else if (c.value is TreeNode) {
                map[c.key] = (c.value!! as TreeNode).toHashMap()
            }
        }
        return map
    }
}

package tml.libs.cku.data

import kotlin.reflect.typeOf

open class JsonNode(var key:String, var content:Any?=null) {
    protected var _parent : JsonNode? = null

    val parent : JsonNode?
    get() = _parent

    val requiredProperties: HashMap<String, Any?>
    get() = content as HashMap<String, Any?>

    fun attach(parent: JsonNode) {
        _parent = parent
        if (parent.content == null)
            parent.content = hashMapOf<String, Any?>()
        parent.requiredProperties[key] = this
    }

    fun detach() {
        _parent?.let {
            it.requiredProperties.remove(key)
            _parent = null
        }
    }

    fun toHashMap(): HashMap<String, Any?> {
        val map = hashMapOf<String, Any?>()
        if (content == null) return map

        for (k in requiredProperties.keys) {
            val c = requiredProperties[k]
            if (c == null)
                map[k] = null
            else if (c is JsonNode) {
                if (c.content == null)
                {
                    map[k] = null
                    continue
                }
                val contentAsMap = c.content as? HashMap<String, Any?>
                if (contentAsMap == null)
                    map[c.key] = "" + c!!.content
                else
                    map[c.key] = c!!.toHashMap()
            }
        }
        return map
    }
}

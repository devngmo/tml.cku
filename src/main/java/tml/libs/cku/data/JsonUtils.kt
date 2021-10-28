package tml.libs.cku.data

import java.util.*
import kotlin.collections.HashMap

class JsonUtils {
    companion object {
        fun ContainProperties(container: Map<String, String>, properties :Map<String, String>) : Boolean {
            for (k in properties) {
                if (container.containsKey(k.key) && container[k.key] == k.value) {
                    // pass
                }
                else {
                    return false
                }
            }
            return true
        }
    }


}
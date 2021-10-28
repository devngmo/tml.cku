package tml.libs.cku.data

class HashMapUtils {
    companion object {
        fun cloneNonNullableMapString(src: HashMap<String, String>?): HashMap<String, String>? {
            if (src == null) return null
            val clone = hashMapOf<String, String>()
            for (key in src.keys)
                clone[key] = src[key]!!
            return clone
        }
        fun cloneMapStringNull(src: HashMap<String, String?>?): HashMap<String, String?>? {
            if (src == null) return null
            val clone = hashMapOf<String, String?>()
            for (key in src.keys)
                clone[key] = src[key]
            return clone
        }
    }
}
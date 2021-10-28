package tml.libs.cku.storages

class SharedPreferencesMemory {
    val properties = hashMapOf<String, Any>()
    companion object {
        val ins = SharedPreferencesMemory()
    }

    fun get(key:String, defaultValue:String) : String {
        if (properties.containsKey(key)) return properties[key] as String
        return defaultValue
    }

    fun set(key:String, value:String) {
        properties[key] = value
    }
}
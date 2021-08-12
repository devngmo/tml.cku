package tml.libs.cku.storages.cdoc

import com.google.gson.Gson
import com.google.gson.JsonObject
import tml.libs.cku.io.StaticLogger

class CollectionDocumentMemoryProvider: CollectionDocumentProviderInterface {
    val jsonConvert = Gson()
    val collectionMap = hashMapOf<String, HashMap<String, String>>()
    fun getCollectionSP(name: String): HashMap<String, String>
    {
        if (collectionMap.containsKey(name))
            return collectionMap[name]!!
        else {
            collectionMap[name] = hashMapOf()
            return collectionMap[name]!!
        }
    }

    fun getCollectionSPArchived(name: String): HashMap<String, String>
    {
        val archiveName = name + "_ARCHIVED"
        if (collectionMap.containsKey(archiveName))
            return collectionMap[archiveName]!!
        else {
            collectionMap[archiveName] = hashMapOf()
            return collectionMap[archiveName]!!
        }
    }

    override fun getDocumentByID(collectionName: String, id: String): JsonObject? {
        val jsonStr = getCollectionSP(collectionName)[id]
        if (jsonStr == null || !jsonStr.startsWith("{")) return null
        val o = jsonConvert.fromJson(jsonStr, JsonObject::class.java)
        o.addProperty("_id", id)
        return o
    }

    override fun getAllDocuments(collectionName: String): List<JsonObject> {
        val ls = arrayListOf<JsonObject>()
        for (e in getCollectionSP(collectionName).entries) {
            val jsonStr = e.value
            var o = JsonObject()
            if (jsonStr != null && jsonStr.startsWith("{")) {
                o = jsonConvert.fromJson(jsonStr, JsonObject::class.java)
            }
            o.addProperty("_id", e.key)
        }
        return ls
    }

    override fun add(collectionName: String, id:String, contentJson: String) {
        getCollectionSP(collectionName)[id] = contentJson
    }

    override fun add(collectionName: String, id: String, jObject: JsonObject) {
        getCollectionSP(collectionName)[id] = jObject.toString()
    }

    override fun count(collectionName: String): Int {
        return getCollectionSP(collectionName).size
    }

    override fun updateByID(collectionName: String, id: String, content: String) {
        getCollectionSP(collectionName)[id] = content
    }

    override fun archive(collectionName: String, id: String) {
        val content = getCollectionSP(collectionName)[id]
        if (content != null) {
            StaticLogger.I(this, "archive document [$id] with content: $content")
            getCollectionSP(collectionName).remove(id)
            getCollectionSPArchived(collectionName)[id] = content
        } else {
            StaticLogger.I(this, "document [$id] content is NULL => not archive")
        }
    }

    override fun unArchive(collectionName: String, id: String) {
        val content = getCollectionSPArchived(collectionName)[id]
        if (content != null) {
            getCollectionSP(collectionName)[id] = content
            getCollectionSPArchived(collectionName).remove(id)
        }
    }

    override fun clear(collectionName: String) {
        getCollectionSP(collectionName).clear()
        getCollectionSPArchived(collectionName).clear()
    }

    override fun getAt(collectionName: String, index: Int): JsonObject? {
        val id = getCollectionSP(collectionName).keys.elementAt(index)
        return getDocumentByID(collectionName, id)
    }
}
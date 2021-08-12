package tml.libs.cku.storages.cdoc

import com.google.gson.JsonObject

interface CollectionDocumentProviderInterface {
    fun getDocumentByID(collectionName: String, id:String):JsonObject?
    fun getAllDocuments(collectionName: String): List<JsonObject>
    fun add(collectionName: String, id:String, contentJson: String)
    fun add(collectionName: String, id:String, jObject: JsonObject)
    fun count(collectionName: String): Int
    fun updateByID(collectionName: String, id: String, content: String)
    fun archive(collectionName: String, id: String)
    fun unArchive(collectionName: String, id: String)
    fun clear(collectionName: String)
    fun getAt(collectionName: String, index: Int): JsonObject?
}
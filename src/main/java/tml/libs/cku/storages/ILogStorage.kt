package tml.libs.cku.storages

@Suppress("unused")
interface ILogStorage {
    fun count():Int
    fun add(msg: String)
    fun getAt(index:Int):String?
}
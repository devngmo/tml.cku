package tml.libs.cku.storages

import tml.libs.cku.TaskResultListener

@Suppress("unused")
interface DocumentStorageInterface  : FileStorageInterface {
    /**
     *
     */
    fun add(fileInfo: String, data: String, callback: TaskResultListener<String, String>)

    /**
     * Update file by id
     */
    fun update(id:String, fileInfo:String, data: String, callback: TaskResultListener<Boolean, String>)
    /**
     *
     */
    fun getFileDataAsText(id:String, callback: TaskResultListener<String, String>)
}
package tml.libs.cku.storages

import tml.libs.cku.event.TaskResultListener
import java.io.InputStream

@Suppress("unused")
interface FileStorageInterface {
    /**
     *
     */
    fun add(fileInfo: String, data: ByteArray, callback: TaskResultListener<String, String>)

    /**
     *
     */
    fun add(fileInfo: String, data: InputStream, callback: TaskResultListener<String, String>)
    /**
     * Update file by id
     */
    fun update(id:String, fileInfo:String, data: ByteArray, callback: TaskResultListener<Boolean, String>)

    fun update(id:String, fileInfo:String, data: InputStream, callback: TaskResultListener<Boolean, String>)

    /**
     *
     */
    fun getFileInfo(id:String, callback: TaskResultListener<String, String>)

    /**
     *
     */
    fun getFileDataAsBytes(id:String, callback: TaskResultListener<ByteArray, String>)

    fun getFileDataAsStream(id:String, callback: TaskResultListener<InputStream, String>)
}
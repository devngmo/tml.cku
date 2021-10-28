@file:Suppress("unused")

package tml.libs.cku.io

import java.io.*
import java.nio.channels.FileChannel
import java.nio.charset.Charset

class FileUtils {
    companion object {
        private const val TAG = "FileUtils"
        fun readAllText(file: File, lineBreak: String? = "\n", charset: Charset? = null): String {
            val text = StringBuilder()
            try {
                val reader = if (charset == null)
                        FileReader(file)
                    else
                        InputStreamReader(FileInputStream(file), charset)

                val br = BufferedReader(reader)
                var line: String? = null
                while (br.readLine().also { line = it } != null) {
                    text.append(line)
                    lineBreak?.let{text.append(it)}
                }
                br.close()
            } catch (e: IOException) {
                StaticLogger.E(TAG, "readAllText " + file.absolutePath, e)
            }
            return text.toString()
        }

        fun writeAllText(text: String, f: File, deleteIfExist: Boolean = true) {
            try {
                if (deleteIfExist)
                    if (f.exists()) f.delete()

                if (!f.createNewFile()) return

                val fos = FileOutputStream(f)
                val outputStreamWriter = OutputStreamWriter(fos)
                //c.openFileOutput(f.getAbsolutePath(), Context.MODE_PRIVATE));
                outputStreamWriter.write(text)
                outputStreamWriter.close()
            } catch (e: IOException) {
                StaticLogger.E(TAG, "writeAllText", e)
            }
        }
        /**
         * append "@appendPrefix + @text" to file
         * @param text
         * @param f
         * @param appendPrefix
         */
        fun appendText(text: String,f: File,appendPrefix: String) {
            try {
                val fos = FileOutputStream(f, true)
                val outputStreamWriter = OutputStreamWriter(fos)
                outputStreamWriter.append(appendPrefix)
                outputStreamWriter.append(text)
                outputStreamWriter.close()
            } catch (e: IOException) {
                StaticLogger.E(TAG, "appendText", e)
            }
        }

        fun writeAllBytes(data: ByteArray, f: File) {
            try {
                if (f.exists()) f.delete()
                if (!f.createNewFile()) return
                val fos = FileOutputStream(f)
                fos.write(data)
                fos.close()
            } catch (e: IOException) {
                StaticLogger.E(TAG, "writeAllBytes", e)
            }
        }

        fun writeAllText(lines: List<String>,lineBreak: String? = "\n",f: File, deleteIfExist: Boolean = true): Boolean {
            try {
                if (deleteIfExist)
                    if (f.exists()) f.delete()

                if (!f.createNewFile()) {
                    return false
                }
                val fos = FileOutputStream(f)
                val outputStreamWriter = OutputStreamWriter(fos)
                for (i in lines.indices) {
                    outputStreamWriter.write(lines[i])
                    if (i < lines.size - 1) {
                        lineBreak?.let {
                            outputStreamWriter.write(it)
                        }
                    }
                }
                outputStreamWriter.close()
                return true
            } catch (e: IOException) {
                StaticLogger.E(TAG, "writeAllText", e)
            }
            return false
        }

        fun appendLines(
            lines: List<String>,
            lineBreak: String? = "\n",
            f: File,
            addLineBreakFirst: Boolean
        ) {
            try {
                val fos = FileOutputStream(f, true)
                val osw = OutputStreamWriter(fos)
                if (addLineBreakFirst) {
                    lineBreak?.let { osw.append(it) }
                }
                for (i in lines.indices) {
                    osw.append(lines[i])
                    if (i < lines.size - 1) {
                        lineBreak?.let { osw.append(it) }
                    }
                }
                osw.close()
            } catch (e: IOException) {
                StaticLogger.E(TAG, "appendLines", e)
            }
        }

        fun readStringLines(
            path: String,
            skipEmptyLine: Boolean
        ): List<String> {
            val results: MutableList<String> = ArrayList()
            val reader: BufferedReader
            try {
                reader = BufferedReader(FileReader(path))
                var line: String? = reader.readLine()
                while (line != null) {
                    line = line.trim { it <= ' ' }
                    if (skipEmptyLine) {
                        if (line.isNotEmpty()) results.add(line)
                    } else results.add(line)
                    line = reader.readLine()
                }
            } catch (ioe: IOException) {
                StaticLogger.E(TAG, "readStringLines", ioe)
            }
            return results
        }

        @Throws(IOException::class)
        fun copyFdToFile(src: FileDescriptor, dst: File) {
            StaticLogger.I(TAG, "copyFdToFile: assetFile " + src + " dst " + dst.absolutePath)
            val inChannel: FileChannel = FileInputStream(src).channel
            val outChannel: FileChannel = FileOutputStream(dst).channel
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel)
            } finally {
                inChannel.close()
                outChannel.close()
            }
        }

        fun copyFile(src: File, dst: File): Boolean {
            try {
                StaticLogger.D(TAG,
                    "copyFile: src " + src.absolutePath
                        .toString() + " dst " + dst.absolutePath
                )
                val inChannel: FileChannel = FileInputStream(src).channel
                val outChannel: FileChannel = FileOutputStream(dst).channel
                try {
                    inChannel.transferTo(0, inChannel.size(), outChannel)
                } finally {
                    inChannel.close()
                    outChannel.close()
                }
                return true
            } catch (ex: Exception) {
                StaticLogger.E(TAG, "copyFile", ex)
            }
            return false
        }

        fun moveFile(src: File, dst: File): Boolean {
            try {
                StaticLogger.D(TAG,
                    "moveFile: src " + src.absolutePath
                        .toString() + " dst " + dst.absolutePath
                )
                val inChannel: FileChannel = FileInputStream(src).channel
                val outChannel: FileChannel = FileOutputStream(dst).channel
                //            try {
                inChannel.transferTo(0, inChannel.size(), outChannel)
                //            } finally {
                inChannel.close()
                outChannel.close()
                //            }
                src.delete()
                return true
            } catch (ex: Exception) {
                StaticLogger.E(TAG, "moveFile", ex)
            }
            return false
        }

        private fun unsafeDeleteSDFolder(folder: File) {
            StaticLogger.I(TAG, "unsafeDeleteSDFolder: " + folder.absolutePath)
            val files: Array<File>? = folder.listFiles()
            files?.let {
                for (f in it) {
                    if (f.isDirectory) unsafeDeleteSDFolder(f) else f.delete()
                }
            }

            folder.delete()
        }

        @Throws(IOException::class)
        fun copyDirectory(sourceLocation: File, targetLocation: File) {
            if (!targetLocation.exists()) targetLocation.mkdirs()
            if (sourceLocation.isDirectory) {
                if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                    throw IOException("Cannot create dir " + targetLocation.absolutePath)
                }
                val children: Array<String>? = sourceLocation.list()
                children?.let {
                    for (i in it.indices) {
                        copyDirectory(
                            File(sourceLocation, it[i]),
                            File(targetLocation, it[i])
                        )
                    }
                }

            } else {

                // make sure the directory we plan to store the recording in exists
                val directory: File? = targetLocation.parentFile
                if (directory != null && !directory.exists() && !directory.mkdirs()) {
                    throw IOException("Cannot create dir " + directory.absolutePath)
                }
                val ins: InputStream = FileInputStream(sourceLocation)
                val out: OutputStream = FileOutputStream(targetLocation)

                val buf = ByteArray(1024)
                var len: Int
                while (ins.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
                ins.close()
                out.close()
            }
        }

        fun readAllBytes(f: File): ByteArray? {
            try {
                if (!f.exists()) return null
                val fos = FileInputStream(f)
                val data = ByteArray(fos.available())
                fos.read(data)
                fos.close()
                return data
            } catch (e: IOException) {
                StaticLogger.E(TAG, "readAllBytes: ${f.absolutePath}", e)
            }
            return null
        }

        fun saveBinaryFile(file: File, data: ByteArray): Boolean {
            try {
                val fos = FileOutputStream(file)
                fos.write(data)
                fos.close()
                return true
            } catch (e: IOException) {
                StaticLogger.E(TAG, "saveBinaryFile: ${file.absolutePath}", e)
            }
            return false
        }
    }
}

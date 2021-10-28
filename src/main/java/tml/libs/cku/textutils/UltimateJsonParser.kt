package tml.libs.cku.textutils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.stream.JsonWriter
import tml.libs.cku.data.JsonNode
import java.lang.Exception

class ParsedLine(val index:Int, val indent:Int)
class ParsedJsonNode(val parsed: ParsedLine, key:String, content:Any?=null) : JsonNode(key, content) {
    val parsedParent : ParsedJsonNode?
    get() {
        if (parent == null) return null
        return parent as ParsedJsonNode
    }

}
class UltimateJsonParser {
    companion object {
        fun parseRawText(rawText: String, prettyPrinting: Boolean = false, lineBreak:String = "\n", multipspaceAsDelimiter: Boolean = false): String? {
            var validText = rawText.trim()
            try {
                if (validText.startsWith("{") || validText.startsWith("[")) {
                    return validText
                }
                val lines = validText.split(lineBreak)
                val root = ParsedJsonNode(ParsedLine(-1, 0), "root")
                parseIndentChilds(0, lines, root, 0, multipspaceAsDelimiter)
                val builder = GsonBuilder()
                if (prettyPrinting)
                    builder.setPrettyPrinting()
                return builder.create().toJson( root.toHashMap() )
            } catch (ex: Exception) {
                return null
            }
        }

        private fun parseIndentChilds(
            index: Int,
            lines: List<String>,
            parent: ParsedJsonNode,
            curBlockIndent: Int,
            multipspaceAsDelimiter: Boolean = false
        ): ParsedLine {
            if (index >= lines.size) return ParsedLine(index, -1)
            var i = index
            var curParent : ParsedJsonNode? = parent
            while (i < lines.size) {
                val trimmedStart = lines[i].trimStart()
                val indent = lines[i].length - trimmedStart.length
                if (indent >= curBlockIndent) {
                    val colonIndex = trimmedStart.indexOf(":")
                    if (colonIndex > 0) {
                        val parts = trimmedStart.split(":")
                        val textNode = ParsedJsonNode(ParsedLine(i, indent), parts[0].trim(), parts[1].trim())
                        textNode.attach(curParent!!)
                        i++
                    } else {
                        if (multipspaceAsDelimiter) {
                            val msIndex = trimmedStart.trim().indexOf("  ")
                            if (msIndex > 0 && msIndex < trimmedStart.length) {
                                val key  = trimmedStart.substring(0, msIndex)
                                val value = trimmedStart.substring(msIndex)
                                val textNode = ParsedJsonNode(ParsedLine(i, indent), key.trim(), value.trim())
                                textNode.attach(curParent!!)
                                i++
                                continue
                            }
                        }
                        val objectNode = ParsedJsonNode(ParsedLine(i, indent), trimmedStart.trim())
                        objectNode.attach(curParent!!)
                        val next = parseIndentChilds(i + 1, lines, objectNode, indent)
                        if (next.index == -1 || next.index >= lines.size) return next
                        while(curParent != null) {
                            if (curParent.parsed.indent > indent) {
                                curParent = curParent.parsedParent
                            }
                        }
                        if (curParent == null)
                            return ParsedLine(i, indent)
                        i = next.indent
                    }
                } else {
                    // indent < curBlockIndent
                    return ParsedLine(i, indent)
                }
            }
            return ParsedLine(-1, -1)
        }

        fun tryPrettier(src: String, defaultReturn: String): String {
            try {
                val builder = GsonBuilder()
                builder.setPrettyPrinting()
                val gson = builder.create()

                val jsonStr = src.trim()
                if (jsonStr.startsWith("[")) {
                    val o = gson.fromJson(src, JsonArray::class.java)
                    return gson.toJson(o)
                }
                else if (jsonStr.startsWith("{")) {
                    val o = gson.fromJson(src, JsonObject::class.java)
                    return gson.toJson(o)
                }
                else
                    return defaultReturn
            }
            catch (ex:Exception) {
                return defaultReturn
            }
        }
    }
}
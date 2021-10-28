package tml.libs.cku.textutils

class UrlUtils {
    companion object {
        fun combine(a: String, b:String):String {
            var s = "${a.trim()}/${b.trim()}"
            if (s.endsWith("/" + b.trim()))
                s = "${a.trim()}${b.trim()}"
            return s
        }

        fun getDomainName(url: String): String {
            var idx = url.indexOf("://")
            if (idx > 0) {
                idx = url.indexOf("/", idx  + 4)
            }
            if (idx < 0) return ""
            return url.substring(0, idx)
        }
    }
}
package tml.libs.cku.io

class ConsoleLogStreamer : LogStreamer {
    override fun D(appTag: String, className: String, msg: String?) {
        println("D/ [$appTag] - $className::$msg")
    }

    override fun E(appTag: String, className: String, msg: String?) {
        println("E/ [$appTag] - $className::$msg")
    }

    override fun E(appTag: String, className: String, msg: String?, ex: Exception) {
        println("E/ [" + appTag + "] - " + className + "::" + msg + " EXCEPTION: " + ex.message)
        ex.printStackTrace()
    }

    override fun W(appTag: String, className: String, msg: String?) {
        println("W/ [$appTag] - $className::$msg")
    }

    override fun I(appTag: String, className: String, msg: String?) {
        println("I/ [$appTag] - $className::$msg")
    }
}
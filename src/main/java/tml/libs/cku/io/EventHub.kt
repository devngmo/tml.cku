@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package tml.libs.cku.io

interface EventHubListener {
    fun onChannelEvent(evt:String, data:Any? = null)
}
class EventHub {
    private val channelMap = HashMap<String, ArrayList<EventHubListener>>()
    fun postD(evt:String, data:Any? = null) {
        post("default", evt, data)
    }

    fun post(channel:String, evt:String, data:Any?) {
        if (channel in channelMap) {
            var triggered = false
            for(lis in channelMap[channel]!!) {
                //StaticLogger.I(this, "$channel: $evt")
                lis.onChannelEvent(evt, data)
                triggered = true
            }
            if (!triggered)
            {
                StaticLogger.W(this, "$channel:$evt not triggered")
            }
        }
        else {
            StaticLogger.W(this, "channel '$channel' not registered!")

        }
    }
    fun register(channel:String, listener: EventHubListener) {
        if (channel in channelMap) {
            channelMap[channel]!!.add(listener)
        }
        else {
            val ls = ArrayList<EventHubListener>()
            if (ls.contains(listener)) return
            ls.add(listener)
            channelMap[channel] = ls
        }
    }

    fun register(channels:Array<String>, listener: EventHubListener) {
        for(channel in channels) {
            if (channel in channelMap) {
                channelMap[channel]!!.add(listener)
            } else {
                val ls = ArrayList<EventHubListener>()
                ls.add(listener)
                channelMap[channel] = ls
            }
        }
    }

    fun unregister(channel:String, listener: EventHubListener) {
        if (channel in channelMap) {
            channelMap[channel]!!.remove(listener)
        }
    }

    fun unregister(channels:Array<String>, listener: EventHubListener) {
        for(channel in channels) {
            if (channel in channelMap) {
                channelMap[channel]!!.remove(listener)
            }
        }
    }
    fun register(listener: EventHubListener) {
        register("default", listener)
    }

    fun unregister(listener: EventHubListener) {
        unregister("default", listener)
    }

    init {
        channelMap["default"] = ArrayList()
    }

    companion object {
        @JvmStatic val ins = EventHub()
    }
}
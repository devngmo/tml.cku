package tml.libs.cku.data

interface DataHubResponseInterface {
    fun onFound(data: Any?)
    fun onNotFound()
}

class DataHub {
    interface PendingDataInterface {

    }

    companion object {
        private val staticVault = HashMap<String, Any>()

        fun clear() {
            staticVault.clear()
        }

        fun getLater(resourceID: String, callback: DataHubResponseInterface) {
            if (staticVault.containsKey(resourceID)) {
                val data =staticVault[resourceID]
                if (data is PendingDataInterface)
                    waitFor(resourceID, callback)
                else
                    callback.onFound(staticVault[resourceID])
            }
            else
                callback.onNotFound()
        }

        fun get(resourceID: String): Any? {
            if (staticVault.containsKey(resourceID)) {
                return staticVault[resourceID]
            }
            return null
        }

        fun pop(resourceID: String, callback: DataHubResponseInterface) {
            if (staticVault.containsKey(resourceID)) {
                val data = staticVault[resourceID]
                staticVault.remove(resourceID)
                waitQueue.remove(resourceID)
                callback.onFound(data)
            }
            else
                callback.onNotFound()
        }
        fun set(resourceID: String, data: Any) {
            staticVault[resourceID] = data
            if (waitQueue.containsKey(resourceID)) {
                (waitQueue[resourceID] as DataHubResponseInterface).onFound(data)
            }
        }

        val waitQueue = HashMap<String, Any>()
        fun waitFor(resourceID: String, callback: DataHubResponseInterface) {
            waitQueue[resourceID]=callback
        }
        fun pendingFor(resourceID: String) {
            staticVault[resourceID] = object:PendingDataInterface{ }
        }
    }
}
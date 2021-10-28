package tml.libs.cku.smartdbg

import tml.libs.cku.io.LogStreamer
import java.util.*

class WorkflowAssert(val id:String, val name:String, val logger:LogStreamer) {
    companion object {
        val passList = arrayListOf<String>()
        fun onProcessStart(name: String, logger: LogStreamer): WorkflowAssert {
            return WorkflowAssert(UUID.randomUUID().toString(), name, logger)
        }

        fun pass(passID: String) {
            passList.add(passID)
        }

        fun expectPasses(expectedPassList: Array<String>) {
            for (expected in expectedPassList)
                if (!passList.contains(expected)) {
                    System.out.println("expected pass but actual not: $expected")
                }
        }
    }
    fun end() {
        logger.D("WorkflowAssert", name, "done as Expected")
    }

    fun expected(expectation: Boolean, failMsg:String):WorkflowAssert {
        if (!expectation) {
            logger.E("WorkflowAssert", name, failMsg)
        }
        return this
    }
}
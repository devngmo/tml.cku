package tml.libs.cku.fsm


open class FSMBaseModule : FiniteStateMachine() {
    companion object {
        val TRIGGER_PHONE_CALL_ENDED = "phone.call.ended"
        val TRIGGER_PHONE_CALL_BEGIN = "phone.call.begin"
        val TRIGGER_PHONE_SHAKE = "phone.shake"

        val TRIGGER_HEADSET_PLUGGED = "headset.plugged"
        val TRIGGER_HEADSET_UNPLUGGED = "headset.unplugged"
        val TRIGGER_VOLUMN_UP = "volumn.up"
        val TRIGGER_VOLUMN_DOWN = "volumn.down"

        val TRIGGER_SCREEN_ON = "screen.on"
        val TRIGGER_SCREEN_OFF = "screen.off"
    }

}
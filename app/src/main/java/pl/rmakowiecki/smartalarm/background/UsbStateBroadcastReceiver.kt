package pl.rmakowiecki.smartalarm.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.rmakowiecki.smartalarm.MainActivity

private const val USB_STATE_CHANGE_ACTION = "android.hardware.usb.action.USB_STATE"
private const val USB_DEVICE_ATTACHED_ACTION = "android.hardware.usb.action.USB_DEVICE_ATTACHED"
private const val USB_DEVICE_DETACHED_ACTION = "android.hardware.usb.action.USB_DEVICE_DETACHED"

class UsbStateBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity(
                Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
        )
    }
}
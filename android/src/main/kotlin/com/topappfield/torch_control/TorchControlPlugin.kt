package com.topappfield.torch_control

import android.content.Context
import android.os.Build
import androidx.annotation.NonNull
import java.util.*
import kotlin.concurrent.timer


import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlin.concurrent.fixedRateTimer

class TorchControlPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var context: Context
    private lateinit var channel: MethodChannel
    private var torchControl: TorchControl? = null
    private var looptimer: Timer? = null
    private var isLightOn: Boolean = false




    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "torch_control")
        channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        if (torchControl != null) torchControl?.release()
        torchControl = null
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "turn" -> {
                val state: Boolean = call.argument<Boolean>("state") ?: false
                isLightOn = state
                result.success( getTorchControl().turn(state) )
            }
            "ready" -> {
                result.success( getTorchControl().ready() )
            }
            "deviceLock" -> {
                result.success(true)
            }
            "loop" -> {
                val time: Int = call.argument<Int>("time") ?: 100
//                val delay = 0 // delay for 30 sec.
//
//                val period = 1000 // repeat every 60 sec.
//
//                doThis = object : TimerTask() {
//                    fun run() {
//                        Log.v("TImer", "repeated")
//                    }
//                }
//
//                looptimer.scheduleAtFixedRate(doThis, delay, period)
//                looptimer = Timer()
//                looptimer!.scheduleAtFixedRate(timerTask {
//                    Log.e("NIlu_TAG","Hello World")
//                },2000,2)
                looptimer?.cancel()
                looptimer =  fixedRateTimer(name = "light",
                    initialDelay = 0, period = time.toLong(),daemon = true) {
                    isLightOn = if (isLightOn) {
                        getTorchControl().turn(false)
                        false
                    }else{
                        getTorchControl().turn(true)
                        true
                    }
                }



                result.success(true)
            }
            "stoploop" -> {
looptimer?.cancel()
                getTorchControl().turn(false)
                isLightOn = false
            }
            else -> result.notImplemented()
        }
    }

    fun getTorchControl(): TorchControl {
        if (torchControl == null) {
            torchControl =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) TorchControlB(context)
                else TorchControlA(context)
        }
        return torchControl as TorchControl
    }

}

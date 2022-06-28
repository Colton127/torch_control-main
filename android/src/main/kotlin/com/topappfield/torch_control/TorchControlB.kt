package com.topappfield.torch_control

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class TorchControlB(context: Context) : TorchControl() {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId: String? = null

    override fun acquire(): Boolean {
        cameraId = cameraManager.cameraIdList.first { id ->
            cameraManager.getCameraCharacteristics(id)[CameraCharacteristics.FLASH_INFO_AVAILABLE] == true
        } ?: null
        if (cameraId == null)
            System.err.println("Camera acquire failed.");
        return cameraId != null
    }

    override fun release() {
        cameraId = null
    }

    override fun ready(): Boolean {
        if (cameraId != null) return true
        return acquire()
    }

    override fun turn(state: Boolean): Boolean {
        if (!this.ready()) return false;
        cameraManager.setTorchMode(cameraId!!, state)
        return state
    }
}


@file:Suppress("DEPRECATION")
package com.topappfield.torch_control

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera

class TorchControlA(private val context: Context) : TorchControl() {
    private var camera: Camera? = null

    override fun acquire(): Boolean {
        if (camera != null) release()
        // system check
        if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
            return false
        // open camera
        camera = try {
            Camera.open()
        } catch (e: Exception) {
            System.err.println("Camera acquire failed: " + e.toString());
            null
        }
        if (camera == null) return false
        // check parameters
        val params = camera!!.parameters
        var result = params?.flashMode != null
        // return
        if (!result) release()
        return result;
    }

    override fun release() {
        camera?.release()
        camera = null
    }

    override fun ready(): Boolean {
        if (camera != null) return true
        return acquire()
    }

    override fun turn(state: Boolean): Boolean {
        if (!ready()) return false;
        // important to do getParameters and then setParameters
        val params = camera?.parameters
        params?.flashMode = if (state) Camera.Parameters.FLASH_MODE_TORCH else Camera.Parameters.FLASH_MODE_OFF
        camera!!.parameters = params
        // start/stop the camera
        if (state) camera!!.startPreview() else camera!!.stopPreview()
        return state
    }
}

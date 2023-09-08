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
    private var maxBrightness: Int = 1
    private var currentBrightness: Int = 1

    override fun acquire(): Boolean {
        cameraId = cameraManager.cameraIdList.first { id ->
            cameraManager.getCameraCharacteristics(id)[CameraCharacteristics.FLASH_INFO_AVAILABLE] == true
        } ?: null
val id = cameraId
        if (id != null){
            checkBrightness(id)
        }

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

    override fun maxBrightness(): Int {
        return maxBrightness
    }

    override fun setBrightness(state: Int): Int {
        currentBrightness = state
        return state
            }
        

    private fun checkBrightness(cameraId: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val maxBright = cameraManager.getCameraCharacteristics(cameraId)[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL];
        if (maxBright != null){
            maxBrightness = maxBright;
        }


            }
            }



    


//     private fun supportsBrightness(): Boolean {
//         if (cameraId == null) return false
//             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                 maxBrightness = cameraManager.getCameraCharacteristics(cameraId)[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL] ?? 1;
        


// return true


//             }
//             return false
//             }

            // private fun startTorch(cameraId: String) {
            //     cameraManager.torchCamera?.let {
            //         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            //             cameraOptions.containsKey(cameraId)
            //         ) {
            //             val characteristics = cameraManager.getCameraCharacteristics(it)
            //             val supportsMaxLevel = characteristics.get(CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL)
            //             if (supportsMaxLevel != null && supportsMaxLevel > 1) {
            //                 cameraManager.turnOnTorchWithStrengthLevel(
            //                     it,
            //                     cameraOptions[it]!!.flashPower
            //                 )
            //             } else {
            //                 cameraManager.setTorchMode(it, true)
            //             }
            //         }
            //     }
            // }
                        
    
//      fun supportsBrightness(): Boolean {
//         cameraId = cameraManager.cameraIdList.first { id ->
//             cameraManager.getCameraCharacteristics(id)[CameraCharacteristics.FLASH_INFO_AVAILABLE] == true
//         } ?: null
//         if (cameraId == null)
//             System.err.println("Camera acquire failed.");
//         return cameraId != null


//         useBrightness = cameraManager.getCameraCharacteristics(id)[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL)];
//         return useBrightness
// }


    override fun turn(state: Boolean): Boolean {
        if (!this.ready()) return false;

            if (state && currentBrightness > 1){
            cameraManager.turnOnTorchWithStrengthLevel(cameraId!!, currentBrightness);
        }else{
            cameraManager.setTorchMode(cameraId!!, state)
  }

        
        return state
    }
}

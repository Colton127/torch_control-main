package com.topappfield.torch_control

abstract class TorchControl {
    abstract fun acquire(): Boolean
    abstract fun release()
    abstract fun ready(): Boolean
    abstract fun turn(state: Boolean): Boolean
}

package com.jaehyeon.intinbletestapp.ui.standby_module

sealed class StandbyState {
    object Start: StandbyState()
    object Pause: StandbyState()
    object Stop: StandbyState()
    object Finish: StandbyState()
}

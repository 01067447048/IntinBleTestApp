package com.jaehyeon.intinbletestapp.ui.choice_device

sealed class ChoiceDeviceState {

    object OnClickAirMonitor: ChoiceDeviceState()
    object OnClickAirCare: ChoiceDeviceState()
}

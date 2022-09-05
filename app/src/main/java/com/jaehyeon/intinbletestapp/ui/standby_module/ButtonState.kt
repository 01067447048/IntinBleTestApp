package com.jaehyeon.intinbletestapp.ui.standby_module

sealed class ButtonState {
    object OnClickStartButton: ButtonState()
    object OnClickResumeButton: ButtonState()
    object OnLongClickButton: ButtonState()
}

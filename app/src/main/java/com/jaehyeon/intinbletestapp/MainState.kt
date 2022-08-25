package com.jaehyeon.intinbletestapp

sealed class MainState {

    object CheckModule: MainState()
    object ChoiceDevice: MainState()
    object ChoiceModule: MainState()
    object Result: MainState()
    object Scan: MainState()
    object StandbyModule: MainState()

}

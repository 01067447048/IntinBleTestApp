package com.jaehyeon.intinbletestapp

import com.jaehyeon.intinbletestapp.util.fragment.MainFragmentType

sealed class MainState {

    object CheckModule: MainState()
    object ChoiceDevice: MainState()
    object ChoiceModule: MainState()
    object Result: MainState()
    object Scan: MainState()
    object StandbyModule: MainState()
    data class BackState(val type: MainFragmentType): MainState()

}

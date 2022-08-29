package com.jaehyeon.intinbletestapp.ui.choice_module

/**
 * Created by Jaehyeon on 2022/08/29.
 */
sealed class ChoiceModuleState {

    object OnClickLED: ChoiceModuleState()
    object OnClickNeb: ChoiceModuleState()
    object OnClickSuction: ChoiceModuleState()
    object OnClickSpray: ChoiceModuleState()
    object OnClickSpirometry: ChoiceModuleState()

}
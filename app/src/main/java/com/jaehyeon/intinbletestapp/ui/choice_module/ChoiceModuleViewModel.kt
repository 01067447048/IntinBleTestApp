package com.jaehyeon.intinbletestapp.ui.choice_module

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaehyeon.intinbletestapp.util.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Jaehyeon on 2022/08/29.
 */
@HiltViewModel
class ChoiceModuleViewModel @Inject constructor(

): ViewModel() {

    private val _action = MutableLiveData<Event<ChoiceModuleState>>()
    val action: LiveData<Event<ChoiceModuleState>> get() = _action

    fun onActionLED() {
        _action.value = Event(ChoiceModuleState.OnClickLED)
    }

    fun onActionNeb() {
        _action.value = Event(ChoiceModuleState.OnClickNeb)
    }

    fun onActionSuction() {
        _action.value = Event(ChoiceModuleState.OnClickSuction)
    }

    fun onActionSpray() {
        _action.value = Event(ChoiceModuleState.OnClickSpray)
    }

    fun onActionSpirometry() {
        _action.value = Event(ChoiceModuleState.OnClickSpirometry)
    }

}
package com.jaehyeon.intinbletestapp.ui.choice_device

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
class ChoiceDeviceViewModel @Inject constructor(

): ViewModel() {

    private val _action = MutableLiveData<Event<ChoiceDeviceState>>()
    val action: LiveData<Event<ChoiceDeviceState>> get() = _action

    fun onActionAirMonitor() {
        _action.value = Event(ChoiceDeviceState.OnClickAirMonitor)
    }

    fun onActionAirCare() {
        _action.value = Event(ChoiceDeviceState.OnClickAirCare)
    }
}
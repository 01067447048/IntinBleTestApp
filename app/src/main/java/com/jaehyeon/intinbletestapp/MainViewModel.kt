package com.jaehyeon.intinbletestapp

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaehyeon.intinbletestapp.util.device.DeviceType
import com.jaehyeon.intinbletestapp.util.device.ModuleType
import com.jaehyeon.intinbletestapp.util.event.Event


/**
 * Created by Jaehyeon on 2022/08/26.
 */
class MainViewModel: ViewModel() {

    private val _ui = MutableLiveData<Event<MainState>>()
    val ui: LiveData<Event<MainState>> get() = _ui

    var device = DeviceType.None
    var module = ModuleType.None

    fun resetDeviceModule() {
        device = DeviceType.None
        module = ModuleType.None
    }

    /**
     * 동기적으로 처리 할 때.
     */
    @MainThread
    fun runState(state: MainState) {
        _ui.value = Event(state)
    }

    /**
     * 비동기적으로 처리 할 때.
     */
    fun postState(state: MainState) {
        _ui.postValue(Event(state))
    }

}
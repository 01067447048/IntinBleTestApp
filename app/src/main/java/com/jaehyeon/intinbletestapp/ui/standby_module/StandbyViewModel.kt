package com.jaehyeon.intinbletestapp.ui.standby_module

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaehyeon.intinbletestapp.util.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by Jaehyeon on 2022/08/30.
 */
@HiltViewModel
class StandbyViewModel @Inject constructor(

): ViewModel() {

    private var currentButtonType  = ButtonType.Stop

    private val _action = MutableLiveData<Event<ButtonState>>()
    val action: LiveData<Event<ButtonState>> get() = _action

    private val _sec = MutableLiveData<Int>()
    val sec: LiveData<Int> get() = _sec

    private val _state = MutableStateFlow<StandbyState>(StandbyState.Stop)
    val state: StateFlow<StandbyState> get() = _state.asStateFlow()

    private val timer = Timer()
    private var timerTask : TimerTask? = null


    fun onClickButton() {
        when (currentButtonType) {
            ButtonType.Start -> {
                currentButtonType = ButtonType.Pause
                _action.value = Event(ButtonState.OnClickResumeButton)
                timerStop()
            }
            ButtonType.Pause -> {
                currentButtonType = ButtonType.Start
                _action.value = Event(ButtonState.OnClickStartButton)
                timerStart()
            }
            ButtonType.Stop -> {
                currentButtonType = ButtonType.Start
                _action.value = Event(ButtonState.OnClickStartButton)
                timerStart()
            }
        }
    }

    fun onLongClickButton(): Boolean {
        _action.value = Event(ButtonState.OnLongClickButton)
        timerStop()
        return false
    }


    private fun createTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                viewModelScope.launch {
                    _sec.postValue(_sec.value?.plus(1))
                }
            }
        }
    }

    private fun timerStart() {
        timerTask = createTimerTask()
        timer.schedule(timerTask, 0)
    }

    private fun timerStop() {
        timerTask?.cancel()
    }

    fun finishTest() {
        flow {
            emit(StandbyState.Finish)
        }.onEach {
            _state.value = it
        }.launchIn(viewModelScope)
    }

    fun changeState() {
        flow<StandbyState> {
            when(state.value) {
                StandbyState.Start -> {
                    emit(StandbyState.Pause)
                }
                StandbyState.Pause -> {
                   emit(StandbyState.Start)
                }
                StandbyState.Stop -> {
                    emit(StandbyState.Start)
                }
                else -> {}
            }
        }.onEach {
            when(it) {
                StandbyState.Start -> {
                    _state.value = it
                }
                StandbyState.Pause -> {
                    _state.value = it
                }
                StandbyState.Stop -> {
                    _state.value = it
                }
                else -> {}
            }
        }.launchIn(viewModelScope)

    }


}
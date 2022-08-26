package com.jaehyeon.intinbletestapp.util.button

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaehyeon.intinbletestapp.util.event.Event

class ButtonViewModel : ViewModel() {

    private val _buttonState = MutableLiveData<Event<ButtonState>>()
    val buttonState: LiveData<Event<ButtonState>>
        get() = _buttonState

    private val _buttonText = MutableLiveData("")
    val buttonText: LiveData<String>
        get() = _buttonText

    private val _buttonEnabled = MutableLiveData(false)
    val buttonEnabled: LiveData<Boolean>
        get() = _buttonEnabled

    fun onButtonClick() {
        _buttonState.value = Event(ButtonState.OnClick)
    }

    fun setLoginButtonText(text: String) {
        _buttonText.value = text
    }

    fun setButtonEnable(state: Boolean) {
        _buttonEnabled.value = state
    }

}
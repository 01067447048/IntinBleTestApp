package com.jaehyeon.intinbletestapp.ui.check_module

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Jaehyeon on 2022/08/29.
 */
@HiltViewModel
class CheckModuleViewModel @Inject constructor(

): ViewModel(){

    private val _status = MutableLiveData(false)
    val status: LiveData<Boolean> get() = _status

    fun changeStatus(status: Boolean) {
        _status.value = status
    }
}
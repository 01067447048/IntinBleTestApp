package com.jaehyeon.intinbletestapp.util.event

import androidx.lifecycle.Observer

/**
 * Created by Jaehyeon on 2022/08/26.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>?> {

    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }

}
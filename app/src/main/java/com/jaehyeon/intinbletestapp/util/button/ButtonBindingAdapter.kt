package com.jaehyeon.intinbletestapp.util.button

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.jaehyeon.intinbletestapp.R

@BindingAdapter("setIsEnabled")
fun bindText(tv: TextView, state: Boolean) {
    tv.background = ContextCompat.getDrawable(tv.context, R.drawable.bg_button_login)
    tv.isEnabled = state
}
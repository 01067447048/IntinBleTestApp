package com.jaehyeon.intinbletestapp.util.binding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jaehyeon.intinbletestapp.util.device.ModuleType

@SuppressLint("SetTextI18n")
@BindingAdapter("setModuleType")
fun bindSetModuleType(tv: TextView, moduleType: ModuleType) {
    when(moduleType) {
        ModuleType.None -> {
            tv.text = "None"
        }
        ModuleType.LED -> {
            tv.text = "LED"
        }
        ModuleType.Nebulizer -> {
            tv.text = "Nebulizer"
        }
        ModuleType.Suction -> {
            tv.text = "Suction"
        }
        ModuleType.Spray -> {
            tv.text = "Spray"
        }
        ModuleType.Spirometery -> {
            tv.text = "Spirometery"
        }
    }
}

@BindingAdapter("setTimer")
fun bindSetTimer(tv: TextView, sec: Int) {
    if (sec == 0)
        tv.text = "00:00:00"
    else
        tv.text = timeString(sec)
}

fun timeString (sec : Int) : String {
    return if(sec >= 60){
        when {
            ((sec / 60) < 10) and ((sec % 60) < 10) -> {
                "0${sec / 60}:0${sec%60}"
            }
            ((sec / 60) >= 10) and ((sec % 60) < 10) -> {
                "${sec / 60}:0${sec%60}"
            }
            ((sec / 60) < 10) and ((sec % 60) >= 10) -> {
                "0${sec / 60}:${sec%60}"
            }
            else -> {
                "${sec / 60}:${sec%60}"
            }
        }
    } else {
        if(sec % 60 < 10) "00:0${sec%60}"
        else "00:${sec%60}"
    }
}


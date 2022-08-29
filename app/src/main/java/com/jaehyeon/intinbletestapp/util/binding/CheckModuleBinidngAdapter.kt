package com.jaehyeon.intinbletestapp.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.util.device.ModuleType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@BindingAdapter("setCheckImage")
fun bindSetCheckImage(iv: ImageView, moduleType: ModuleType) {

    when(moduleType) {
        ModuleType.None -> Unit
        ModuleType.LED -> {
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    iv.setImageResource(R.mipmap.a2493)
                    delay(1500L)
                    iv.setImageResource(R.mipmap.a2496)
                }
            }
        }
        ModuleType.Nebulizer -> {
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    iv.setImageResource(R.mipmap.a2494)
                    delay(1500L)
                    iv.setImageResource(R.mipmap.a2495)
                }
            }
        }
        ModuleType.Suction -> {
            CoroutineScope(Dispatchers.Main).launch {
                while(true) {
                    iv.setImageResource(R.mipmap.a2491)
                    delay(1500L)
                    iv.setImageResource(R.mipmap.a2498)
                }
            }
        }
        ModuleType.Spray -> {
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    iv.setImageResource(R.mipmap.a2492)
                    delay(1500L)
                    iv.setImageResource(R.mipmap.a2497)
                }
            }
        }
        ModuleType.Spirometery -> {
            CoroutineScope(Dispatchers.Main).launch {
                while(true) {
                    iv.setImageResource(R.mipmap.a2492)
                    delay(1500L)
                    iv.setImageResource(R.mipmap.a2497)
                }
            }
        }
    }

}

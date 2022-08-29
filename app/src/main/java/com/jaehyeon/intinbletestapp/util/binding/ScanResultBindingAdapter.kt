package com.jaehyeon.intinbletestapp.util.binding

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("rssi")
fun setRssi (textView: TextView, scanResult: ScanResult?) {
    textView.text = scanResult?.rssi?.toString()
}

@BindingAdapter("device")
fun setDevice (textView: TextView, scanResult: ScanResult?){
    textView.text = scanResult?.device?.toString()
}

@SuppressLint("MissingPermission")
@BindingAdapter("name")
fun setName (textView: TextView, scanResult: ScanResult?) {
    textView.text = scanResult?.device?.name.toString()
}
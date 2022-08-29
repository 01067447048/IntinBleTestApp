package com.jaehyeon.intinbletestapp.util.ble

import android.bluetooth.le.BluetoothLeScanner

/**
 * Created by Jaehyeon on 2022/08/28.
 */
interface BleRepository {

    fun ableUseBle(): Boolean
    fun getScanner(): BluetoothLeScanner
}
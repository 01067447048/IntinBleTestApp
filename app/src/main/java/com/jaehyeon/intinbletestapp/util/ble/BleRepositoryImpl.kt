package com.jaehyeon.intinbletestapp.util.ble

import android.Manifest
import android.app.Application
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Jaehyeon on 2022/08/28.
 */
class BleRepositoryImpl @Inject constructor(
    private val bleAdapter: BluetoothAdapter,
    private val application: Application
): BleRepository {

    override fun ableUseBle(): Boolean {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isBle = bleAdapter.isEnabled

        return !(!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isBle)

    }

    override fun getScanner(): BluetoothLeScanner = bleAdapter.bluetoothLeScanner

}
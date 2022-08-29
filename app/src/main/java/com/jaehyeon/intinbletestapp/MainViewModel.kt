package com.jaehyeon.intinbletestapp

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaehyeon.intinbletestapp.util.ListLiveData
import com.jaehyeon.intinbletestapp.util.ble.BleRepository
import com.jaehyeon.intinbletestapp.util.device.DeviceType
import com.jaehyeon.intinbletestapp.util.device.ModuleType
import com.jaehyeon.intinbletestapp.util.device.SendMessageType
import com.jaehyeon.intinbletestapp.util.event.Event
import com.jaehyeon.intinbletestapp.util.fragment.MainFragmentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.experimental.and


/**
 * Created by Jaehyeon on 2022/08/26.
 */
@SuppressLint("MissingPermission")
@HiltViewModel
class MainViewModel @Inject constructor(
    private val scanner: BleRepository,
    private val application: Application
): ViewModel() {

    private val TAG = javaClass.simpleName

    private val _ui = MutableLiveData<Event<MainState>>()
    val ui: LiveData<Event<MainState>> get() = _ui

    private val _enable = MutableLiveData(false)
    val enable: LiveData<Boolean> get() = _enable

    private val _connectState = MutableLiveData<Int>()
    val connectState: LiveData<Int> get() = _connectState

    private val _scanBle = MutableLiveData<ScanResult>()
    val scanBle: LiveData<ScanResult> get() = _scanBle

    private val _connectedDevice = MutableLiveData<ScanResult>()
    val connectedDevice: LiveData<ScanResult> get() = _connectedDevice

    private val _receiveRead = MutableLiveData<String>()
    val receiveRead: LiveData<String> get() = _receiveRead

    private val _receiveWrite = MutableLiveData<String>()
    val receiveWrite: LiveData<String> get() = _receiveWrite

    private val _receiveChanged = MutableLiveData<String>()
    val receiveChanged: LiveData<String> get() = _receiveChanged

    var device = DeviceType.None
    var module = ModuleType.None

    private val uuid = arrayOf(0xb8, 0x5c)

    private val uuidTx = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
    private val uuidRx = UUID.fromString("02f00000-0000-0000-0000-00000000fe00")

//    private val uuidTx = arrayOf((0xb8).toByte(), (0x5c).toByte(), (0x49).toByte(), (0xd2).toByte(), (0x04).toByte(), (0xa3).toByte(),
//    (0x40).toByte(), (0x71).toByte(), (0xa0).toByte(), (0xb5).toByte(), (0x35).toByte(), (0x85).toByte(), (0x3e).toByte(), (0xb0).toByte(), (0x83).toByte(), (0x07).toByte()).toByteArray()
//
//    private val uuidRx = arrayOf((0xba).toByte(), (0x5c).toByte(), (0x49).toByte(), (0xd2).toByte(), (0x04).toByte(), (0xa3).toByte(),
//        (0x40).toByte(), (0x71).toByte(), (0xa0).toByte(), (0xb5).toByte(), (0x35).toByte(), (0x85).toByte(), (0x3e).toByte(), (0xb0).toByte(), (0x83).toByte(), (0x07).toByte()).toByteArray()

    private var bluetoothGatt: BluetoothGatt? = null
    private var characteristic: BluetoothGattCharacteristic? = null

    fun resetDeviceModule() {
        device = DeviceType.None
        module = ModuleType.None
    }

    fun backState(currentScreen: MainFragmentType) {
        when(currentScreen) {
            MainFragmentType.CheckModule -> runState(MainState.ChoiceModule)
            MainFragmentType.ChoiceDevice -> runState(MainState.Scan)
            MainFragmentType.ChoiceModule -> runState(MainState.ChoiceDevice)
            MainFragmentType.Result -> runState(MainState.ChoiceDevice)
            MainFragmentType.Scan -> Unit
            MainFragmentType.StandbyModule -> runState(MainState.ChoiceModule)
        }
    }

    /**
     * 동기적으로 처리 할 때.
     */
    @MainThread
    fun runState(state: MainState) {
        _ui.value = Event(state)
    }

    /**
     * 비동기적으로 처리 할 때.
     */
    fun postState(state: MainState) {
        _ui.postValue(Event(state))
    }

    init {
        _enable.value = scanner.ableUseBle()
    }

    private val scanCallBack = object : ScanCallback(){
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.let {
                _scanBle.postValue(it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private val gattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (_enable.value == true) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        bluetoothGatt = gatt
                        bluetoothGatt?.discoverServices()
                        _connectState.postValue(newState)
                        Log.d(TAG, "onConnectionStateChange : $newState / $status")
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        _connectState.postValue(newState)
                        bluetoothGatt?.disconnect()
                        bluetoothGatt = null
                        Log.d(TAG, "onConnectionStateChange : $newState / $status")
                    }
                    else -> {
                        Log.d(TAG, "onConnectionStateChange: $newState")
                    }
                }
            }

        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val services = gatt?.services
                services?.let {
                    it.forEach { ser ->
                        Log.e(TAG, "onServicesDiscovered: ${ser.uuid}", )
                    }
                }
                val service = gatt?.getService(uuidRx)
                characteristic = service?.getCharacteristic(uuidTx)
                gatt?.setCharacteristicNotification(characteristic, true)
                Log.d(TAG, "onServicesDiscovered")
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, status)
            Log.d(TAG, "onCharacteristicRead")
            characteristic?.let {
                _receiveRead.value = String(it.value)
//                if (it != null) {
//                    viewModelScope.launch (Dispatchers.IO) {
//                        showAdapter(it.value)
//                    }
//
//                }
//                else {
//                    viewModelScope.launch (Dispatchers.IO) {
//                        showAdapter("errorCode : NULL".toByteArray())
//                    }
//                }
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            Log.d(TAG, "onCharacteristicWrite")
            characteristic?.let {
               _receiveWrite.value = String(it.value)
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            Log.d(TAG, "onCharacteristicChanged")
            characteristic?.let {
                _receiveChanged.value = String(it.value)
            }
        }
    }

    fun hasProperty(characteristic: BluetoothGattCharacteristic, property: Int): Boolean {
        val prop = characteristic.properties and property
        return prop == property
    }


    fun startScan() {
        scanner.getScanner().startScan(scanCallBack)
    }

    fun connect(result: ScanResult) {
        stop()
        _connectedDevice.value = result

        if(bluetoothGatt != null) {
            bluetoothGatt?.disconnect()
            bluetoothGatt = null
        }

        result.device?.connectGatt(application.applicationContext, false, gattCallback)
    }

    private fun stop () {
        scanner.getScanner().stopScan(scanCallBack)
    }

    fun sendMessage(type: SendMessageType, value: String = "") {
        if (characteristic == null) return

        when (type) {
            SendMessageType.Time -> {
                characteristic?.value = String.format(type.message, value).toByteArray()
                characteristic?.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                bluetoothGatt?.writeCharacteristic(characteristic)
            }
            else -> {
                characteristic?.value = type.message.toByteArray()
                characteristic?.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                bluetoothGatt?.writeCharacteristic(characteristic)
            }
        }
    }
}
package com.intin.intin_project;

import android.bluetooth.BluetoothDevice;

/**
 * 검색한 디바이스 정보를 조회 함
 */
public class BLE_BTLE_Device {
    private BluetoothDevice bluetoothDevice;
    private int rssi;

    //디바이스 내용을 얻는다
    public BLE_BTLE_Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    //BLE로 검색된 디바이스의 의 MAC 얻기
    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    //BLE로 검색된 디바이스의 이름
    public String getName() {
        return bluetoothDevice.getName();
    }

    //BLE로 검색된 디바이스의 쓰기 주소 HEX 값으로 넘어옴
    public void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    //BLE로 검색된 디바이스의 읽기 주소 HEX 값으로 넘어옴
    public int getRSSI() {
        return rssi;
    }
}//BTLE_Device..
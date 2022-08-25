package com.intin.intin_project;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Gravity;
import android.widget.Toast;

public class BLE_Utils {

    public static boolean chekBluetooth(BluetoothAdapter bluetoothAdapter){
        // 블루투스가 켜져 있는지 확인함
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()){
            return false;
        }else {
            return true;
        }
    }
    //블루투스가 꺼져 잇으면 켜줌
    public static void requestUserBluetooth(Activity activity){
        Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intentBluetoothEnable, Result_Count.START_BLE);
    }

    public static IntentFilter makeGattUpdateIntentFilter(){

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLE_Service_BTLE_GATT.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BLE_Service_BTLE_GATT.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BLE_Service_BTLE_GATT.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BLE_Service_BTLE_GATT.ACTION_DATA_AVAILABLE);

        return intentFilter;
    }
    // 전달된 데이터 HEX를 스트링 형태로 쪼개서 리턴해줌

    public static String hexToString(byte[] data){
        final StringBuilder sb = new StringBuilder(data.length);
        for (byte byteChar : data){
            sb.append(String.format("%02X ", byteChar));
        }
        return sb.toString();
    }// 코드 변경이 필요한 부분

    public static int hasWriteProperty(int property){
        return property & BluetoothGattCharacteristic.PROPERTY_WRITE;
    }

    public static int hasReadProperty(int property){
        return property & BluetoothGattCharacteristic.PROPERTY_READ;
    }

    public static int hasNotifyProperty(int property){
        return property & BluetoothGattCharacteristic.PROPERTY_NOTIFY;
    }
    // 노출되는 토스트를 위치와 크기 조정할수 있음.
    public static void toast(Context context, String string){
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0 , 400 );
        toast.show();
    }

}
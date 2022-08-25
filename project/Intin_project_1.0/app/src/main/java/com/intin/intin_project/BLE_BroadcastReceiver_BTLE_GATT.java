package com.intin.intin_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BLE_BroadcastReceiver_BTLE_GATT extends BroadcastReceiver {

    private boolean mConnected = false;

    private Result_Count activity;

    public BLE_BroadcastReceiver_BTLE_GATT(Result_Count activity){
        this.activity = activity;
    }

    //서비스에서 발생한 다양한 이벤트를 처리합니다.
    // ACTION_GATT_CONNECTED : GATT 서버에 연결되었습니다.
    // ACTION_GATT_DISCONNECTED : GATT 서버와의 연결이 끊어졌습니다.
    // ACTION_GATT_SERVICES_DISCOVERED : GATT 서비스가 발견되었습니다.
    // ACTION_DATA_AVAILABLE : 장치에서 데이터를 수신했습니다. 이것은 될 수 있습니다
    // 읽기 또는 알림 작업의 결과.

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (BLE_Service_BTLE_GATT.ACTION_GATT_CONNECTED.equals(action)){
            mConnected = true;
            activity.BLE_connected();
        }
        else if (BLE_Service_BTLE_GATT.ACTION_GATT_DISCONNECTED.equals(action)){
            mConnected = false;
            activity.BLE_not_connected();
            BLE_Utils.toast(activity.getApplicationContext(), "디바이스와의 연결이 끊어 졌습니다.");
            activity.finish();
        }
        else if (BLE_Service_BTLE_GATT.ACTION_GATT_SERVICES_DISCOVERED.equals(action)){
            activity.updateServices();
        }
        else if (BLE_Service_BTLE_GATT.ACTION_DATA_AVAILABLE.equals(action)){
            activity.updateCharacteristic();
        }
        return;
    }
}

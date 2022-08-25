package com.intin.intin_project;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BLE_BroadcastReceiver_BTState extends BroadcastReceiver {

    Context activityContext;

    public BLE_BroadcastReceiver_BTState(Context activityContext) {
        this.activityContext = activityContext;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

            switch (state){
                case BluetoothAdapter.STATE_OFF:
                    BLE_Utils.toast(activityContext, "블루투스가 꺼져 있습니다");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    BLE_Utils.toast(activityContext, "블루투스가 꺼졌다.");
                    break;
                case BluetoothAdapter.STATE_ON:
                    BLE_Utils.toast(activityContext, "블루 투스가 켰습니다.");
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    BLE_Utils.toast(activityContext, "블루투스를 켜졌습니다.");
                    break;
            }
        }
    }


}
package com.intin.intin_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BLE_Dialog_BTLE_Characteristic extends DialogFragment implements DialogInterface.OnClickListener {

    private String title;
    private BLE_Service_BTLE_GATT service;
    private BluetoothGattCharacteristic characteristic;
    Intent intentResult;

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.dialog_btle_characteristic, null)).setNegativeButton("START", this)
                .setPositiveButton("END", this).setNeutralButton("OFF",this);
        builder.setTitle("OVIEW 테스트  " +title);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

       // which = intentResult.getIntExtra("end" , b);
        Button startCmd = (Button)((AlertDialog)dialog).findViewById(R.id.startBtn) ;
        Button endCM = (Button)((AlertDialog)dialog).findViewById(R.id.endBtn) ;
        Button offCM = (Button)((AlertDialog)dialog).findViewById(R.id.offBtn) ;
        Button infCM = (Button)((AlertDialog)dialog).findViewById(R.id.inf_btn) ;

        System.out.println(which);

        // 어떤 버튼이 눌렸는지 찾기
//        EditText edit = (EditText)((AlertDialog) dialog).findViewById(R.id.et_submit);
        switch (which) {
            case -2:
                if (service != null) {
                    characteristic.setValue(startCmd.getText().toString());
                    service.writeCharacteristic(characteristic);

                }
                // 취소버튼이 눌려졌다
                break;
            case -1:
                // ok 버튼이 눌려졌다
                if (service != null) {
                    characteristic.setValue(endCM.getText().toString());
                    service.writeCharacteristic(characteristic);
                    System.out.println("Before: " + startCmd.getText());
                    System.out.println("After: " + characteristic.getValue());
                }
                break;
            default:
                if (service != null) {
                    characteristic.setValue(offCM.getText().toString());
                    service.writeCharacteristic(characteristic);
                }
                break;
        }
    }

    public void setTitle(String title) {  this.title = title;  }

    public void setService(BLE_Service_BTLE_GATT service) {  this.service = service;   }

    public void setCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }
}

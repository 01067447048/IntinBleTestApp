package com.intin.intin_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BLE_MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;
    public static final int BLE_ADAPTER = 3;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private ArrayList<BLE_BTLE_Device> mBTDevicesArrayList;
    private HashMap<String, BLE_BTLE_Device> mBTDevicesHashMap;
    private BLE_ListAdapter_BTLE_Devices adapter;
    private ListView listView;
    private Button btn_Scan;
    private Activity activity;

    private BLE_BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private BLE_Scanner_BTLE mBTLeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble__main_acitivity);

        //?????? ????????? BLE??? ????????? ?????? ???????????? ??????
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getApplicationContext(), "BLE ????????? ???????????? ?????? ?????? ?????????.", Toast.LENGTH_SHORT).show();
            finish();
        }

        ImageView img = (ImageView) findViewById(R.id.my_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(img);

        mBTStateUpdateReceiver = new BLE_BroadcastReceiver_BTState(getApplicationContext());
//        mBTLeScanner = new BLE_Scanner_BTLE(this, 5000, -75);

        mBTDevicesHashMap = new HashMap<>();
        mBTDevicesArrayList = new ArrayList<>();

        adapter = new BLE_ListAdapter_BTLE_Devices(this, R.layout.btle_device_list_item, mBTDevicesArrayList);

        listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        btn_Scan = findViewById(R.id.scan_btn);

        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);
        btn_Scan.setOnClickListener(this);
        startScan();
    }// onCreate..

    // ???????????? ??????
    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    // ????????? ?????? ??????
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

//        unregisterReceiver(mBTStateUpdateReceiver);
        // stopScan();
    }

    // ????????? ????????????
    @Override
    protected void onStop() {
        super.onStop();

//        unregisterReceiver(mBTStateUpdateReceiver);
        stopScan();
    }

    // ?????????
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (requestCode == RESULT_OK) {

            } else if (requestCode == RESULT_CANCELED) {
                BLE_Utils.toast(getApplicationContext(), "?????? ????????? ????????????");
            }
        } else if (requestCode == BTLE_SERVICES) {
            // ?
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();

//        Utils.toast(context, "List Item clicked");

        // do something with the text views and start the next activity.

        stopScan();

        String name = mBTDevicesArrayList.get(position).getName();
        String address = mBTDevicesArrayList.get(position).getAddress();

        Intent intent = new Intent(getApplicationContext(), BLE_Activity_BTLE_Services.class);
        intent.putExtra(BLE_Activity_BTLE_Services.EXTRA_NAME, name);
        intent.putExtra(BLE_Activity_BTLE_Services.EXTRA_ADDRESS, address);
        startActivityForResult(intent, BTLE_SERVICES);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_btn:
                BLE_Utils.toast(getApplicationContext(), "?????? ????????? ???????????????.");
                if (!mBTLeScanner.ismScanning()) {
                    startScan();
                } else {
                    stopScan();
                }
                break;

            default:
                break;
        }
    }

    //?????? ??? ??????????????? ???????????? ?????? ???
    public void addDevice(BluetoothDevice device, int rssi) {
        String address = device.getAddress();
        if (!mBTDevicesHashMap.containsKey(address)) {
            BLE_BTLE_Device btle_device = new BLE_BTLE_Device(device);
            btle_device.setRSSI(rssi);

            mBTDevicesHashMap.put(address, btle_device);
            mBTDevicesArrayList.add(btle_device);
        } else {
            mBTDevicesHashMap.get(address).setRSSI(rssi);
        }
        adapter.notifyDataSetChanged();
    }

    public void startScan() {
        btn_Scan.setTag("Scanning.. ?????????");
        mBTDevicesArrayList.clear();
        mBTDevicesHashMap.clear();
        mBTLeScanner.start();
    }

    public void stopScan() {
        btn_Scan.setText("RE BLE SCANN..");
        mBTLeScanner.stop();
    }

}

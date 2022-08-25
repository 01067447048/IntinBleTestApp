package com.intin.intin_project;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BLE_Activity_BTLE_Services extends AppCompatActivity implements ExpandableListView.OnChildClickListener {
    private final static String TAG = BLE_Activity_BTLE_Services.class.getSimpleName();

    public static final String EXTRA_NAME = "android.com.example.myapplication.Activity_BTLE_Services.NAME";
    public static final String EXTRA_ADDRESS = "android.com.example.myapplication.Activity_BTLE_Services.ADDRESS";
    public BLE_Scanner_BTLE mm;

    private BLE_ListAdapter_BTLE_Services expandableListAdapter;
    private ExpandableListView expandableListView;

    private ArrayList<BluetoothGattService> services_ArrayList;
    private HashMap<String, BluetoothGattCharacteristic> characteristics_HashMap;
    private HashMap<String, ArrayList<BluetoothGattCharacteristic>> characteristics_HashMapList;

    private Intent mBTLE_Service_Intent;
    private BLE_Service_BTLE_GATT mBTLE_Services;
    private boolean mBTLE_Service_Bond;
    private BLE_BroadcastReceiver_BTLE_GATT mGattUpdateReceiver;

    private String name;
    private String address;

    private ServiceConnection mBTLE_ServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //LocalService에 바인딩하고 IBinder를 캐스팅하고 LocalService 인스턴스를 가져옵니다

            BLE_Service_BTLE_GATT.BTLeServiceBinder binder = (BLE_Service_BTLE_GATT.BTLeServiceBinder) service;
            mBTLE_Services = binder.getService();
            mBTLE_Service_Bond = true;

            System.out.println("접속 성공이냐? 접속 성공?? 접속 성공이?? 접속이 성공?? ");
            if (!mBTLE_Services.initialize()) {
                Log.e(TAG, "블루투스가 초기화 되지 않았습니다.");
                finish();
            }
            mBTLE_Services.connect(address);
            System.out.println(address);
            // 시작초기화에 성공하면 장치에 자동으로 연결 합니다.
//            mBTLeService.connect(mBTLeDeviceAddress);

//            mBluetoothGatt = mBTLeService.getmBluetoothGatt();
//            mGattUpdateReceiver.setBluetoothGatt(mBluetoothGatt);
//            mGattUpdateReceiver.setBTLeService(mBTLeService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBTLE_Services = null;
            mBTLE_Service_Bond = false;
//            mBluetoothGatt = null;
//            mGattUpdateReceiver.setBluetoothGatt(null);
//            mGattUpdateReceiver.setBTLeService(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__btle__services);

        ImageView img = (ImageView) findViewById(R.id.my_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(img);

        System.out.println("2222222222222222222222222222222222여기가 먼저가?? 먼저 시작하고 접속 되는기가?? 어디가 먼저고? ");

        Intent intent = getIntent();
        name = intent.getStringExtra(BLE_Activity_BTLE_Services.EXTRA_NAME);
        address = intent.getStringExtra(BLE_Activity_BTLE_Services.EXTRA_ADDRESS);

        services_ArrayList = new ArrayList<>();
        characteristics_HashMap = new HashMap<>();
        characteristics_HashMapList = new HashMap<>();

        expandableListAdapter = new BLE_ListAdapter_BTLE_Services(
                this, services_ArrayList, characteristics_HashMapList);


        expandableListView = (ExpandableListView) findViewById(R.id.lv_expandable);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(this);

        ((TextView) findViewById(R.id.tv_name)).setText(name + "서비스");
        ((TextView) findViewById(R.id.tv_address)).setText(address);

    }// onCreate..

    @Override
    protected void onStart() {
        super.onStart();

//        mGattUpdateReceiver = new BLE_BroadcastReceiver_BTLE_GATT(this);
        registerReceiver(mGattUpdateReceiver, BLE_Utils.makeGattUpdateIntentFilter());

        mBTLE_Service_Intent = new Intent(this, BLE_Service_BTLE_GATT.class);
        bindService(mBTLE_Service_Intent, mBTLE_ServiceConnection, Context.BIND_AUTO_CREATE);

        startService(mBTLE_Service_Intent);
        System.out.println("3333333333333333333333333 여기가 먼저가?? 먼저 시작하고 접속 되는기가?? 어디가 먼저고? ");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(mGattUpdateReceiver);
        unbindService(mBTLE_ServiceConnection);
        mBTLE_Service_Intent = null;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        BluetoothGattCharacteristic characteristic = characteristics_HashMapList.get(
                services_ArrayList.get(groupPosition).getUuid().toString()).get(childPosition);

        if (BLE_Utils.hasWriteProperty(characteristic.getProperties()) != 0) {
            String uuid = characteristic.getUuid().toString();

            BLE_Dialog_BTLE_Characteristic dialog_btle_characteristic = new BLE_Dialog_BTLE_Characteristic();

            dialog_btle_characteristic.setTitle(uuid);
            dialog_btle_characteristic.setService(mBTLE_Services);
            dialog_btle_characteristic.setCharacteristic(characteristic);
            System.out.println(characteristic);

            dialog_btle_characteristic.show(getSupportFragmentManager(), "Dialog_BTLE_Characteristic");
        } else if (BLE_Utils.hasReadProperty(characteristic.getProperties()) != 0) {
            if (mBTLE_Services != null) {
                mBTLE_Services.readCharacteristic(characteristic);
            }
        } else if (BLE_Utils.hasNotifyProperty(characteristic.getProperties()) != 0) {
            if (mBTLE_Services != null) {
                mBTLE_Services.setCharacteristicNotification(characteristic, true);
            }
        }
        return false;
    }

    public void updateServices() {
        if (mBTLE_Services != null) {

            services_ArrayList.clear();
            characteristics_HashMap.clear();
            characteristics_HashMapList.clear();

            List<BluetoothGattService> serviceList = mBTLE_Services.getSupportedGattServices();

            for (BluetoothGattService service : serviceList) {
                services_ArrayList.add(service);

                List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                ArrayList<BluetoothGattCharacteristic> nweCharacteristicsList = new ArrayList<>();

                for (BluetoothGattCharacteristic characteristic : characteristicList) {
                    characteristics_HashMap.put(characteristic.getUuid().toString(), characteristic);
                    nweCharacteristicsList.add(characteristic);
                }
                characteristics_HashMapList.put(service.getUuid().toString(), nweCharacteristicsList);
            }

            if (serviceList != null && serviceList.size() > 0) {
                expandableListAdapter.notifyDataSetChanged();
            }
        }
    }

    public void updateCharacteristic() {
        expandableListAdapter.notifyDataSetChanged();
    }

}

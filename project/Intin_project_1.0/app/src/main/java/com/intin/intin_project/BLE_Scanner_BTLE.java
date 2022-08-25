package com.intin.intin_project;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

/*
BLE 디바이스를 찾기 위한 클래스
 */
public class BLE_Scanner_BTLE {
    private BLE_Service_BTLE_GATT connct_For_GATT;
    private Result_Count mm;
    public static final int BTLE_SERVICES = 2;
    public static final String OVIEW_MAC_address = "4C:E1:73:C2:25:16";
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private long scanPeriod;
    private int signalStrength;

    public BLE_Scanner_BTLE(Result_Count result_count, long scanPeriod, int signalStrength){
        mm = result_count;
        mHandler = new Handler();

        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;

        final BluetoothManager bluetoothManager = (BluetoothManager) mm.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    public boolean ismScanning(){ return  mScanning; }

    public void start(){
        scanLeDevice(true);

    }
    public void stop(){
        scanLeDevice(false);
        mm.no_scann_BLE();
    }

    /*
    특정 유형의 주변 장치 만 검색하려면 대신 startLeScan (UUID [], BluetoothAdapter.LeScanCallback)을 호출하십시오.
    앱이 지원하는 GATT 서비스를 지정하는 UUID 객체 배열을 제공합니다
     */
    private void scanLeDevice(final boolean enable){
        if (enable && !mScanning){
           BLE_Utils.toast(mm.getApplicationContext(), "BLE 장치를 찾습니다...");
            // 미리 예정한 시간동안 스캔한후 정지함
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BLE_Utils.toast(mm.getApplicationContext(),"BLE 장치 검색을 중지 합니다...");
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mm.stopScan();
                }
            }, scanPeriod);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);                     //장치검색
//            mBluetoothAdapter.startLeScan(uuids, mLeScanCallback);  // 이건 특정 UUID형식의 기계만 찾을경우
        }
        else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }
    // 장치에 대한 콜백
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    final int new_rssi = rssi;
                    if (rssi > signalStrength) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
//                                mm.addDevice(device, new_rssi);

                            }
                        });
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (device.getAddress().equals(OVIEW_MAC_address)) {

                                    String addMAC = device.getAddress();
                                    String name = device.getName();
                                    System.out.println(device);
                                    System.out.println("넘어 간다 이제 ..");
                                    mm.addString(addMAC, name);
//                                    Intent intent = new Intent(mm.getApplicationContext(), BLE_Activity_BTLE_Services.class);
//                                    intent.putExtra(BLE_Activity_BTLE_Services.EXTRA_NAME, name);
//                                    intent.putExtra(BLE_Activity_BTLE_Services.EXTRA_ADDRESS, addMAC);
//                                    mm.startActivityForResult(intent, BTLE_SERVICES);
                                    stop();
                                }
                            }
                        });
                    }

                }
            };
}
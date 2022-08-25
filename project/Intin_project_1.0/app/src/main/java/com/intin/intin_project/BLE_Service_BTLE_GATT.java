package com.intin.intin_project;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 Gatt 서버를 통한 BLE 와의 통신을 위한 클래스 Kelvin 이 만든 클래스 참조
 */
public class BLE_Service_BTLE_GATT extends Service {

    private final static String TAG = BLE_Service_BTLE_GATT.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBLE_Address;
    private BluetoothGatt mBluetoothGatt;
    private Result_Count result_count;
    private boolean isConnect = false;

    private int mConnectionState = STATE_DISCONNECTED;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED = "com.intin.intin_project.Service_BTLE_GATT.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.intin.intin_project.Service_BTLE_GATT.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.intin.intin_project.Service_BTLE_GATT.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.intin.intin_project.Service_BTLE_GATT.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_UUID = "com.intin.intin_project.Service_BTLE_GATT.EXTRA_UUID";
    public final static String EXTRA_DATA = "com.intin.intin_project.Service_BTLE_GATT.EXTRA_DATA";

    // BLE가 연결 되었을때 사용 될 콜백

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if( newState == BluetoothProfile.STATE_CONNECTED ){
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;

                broadcastUpdate(intentAction);

                Log.i(TAG,"GATT 서버에 접속했습니다.");

                // 서버연결후 서비스를 찾는다
                Log.i(TAG, "서비스 검색을 시작하려 합니다 : " + mBluetoothGatt.discoverServices());
            }else if (newState == BluetoothProfile.STATE_DISCONNECTED){
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "GATT 서버와 끊겼습니다. 끊겼다고 합니다.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS){
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            }
            else {
                Log.w(TAG, "onServicesDiscovered 수신자 :"+status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS){
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    private void broadcastUpdate(final String action){
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic){
        final Intent intent = new Intent(action);
        intent.putExtra(EXTRA_UUID, characteristic.getUuid().toString());

        // HEX 형식의 프로필
        final byte[] data = characteristic.getValue();

        if (data != null && data.length > 0){
            intent.putExtra(EXTRA_DATA, new String(data) + "\n" + BLE_Utils.hexToString(data));
        }
        else {
            intent.putExtra(EXTRA_DATA, 0);
        }
        sendBroadcast(intent);
    }

    public class BTLeServiceBinder extends Binder {

        BLE_Service_BTLE_GATT getService() { return BLE_Service_BTLE_GATT.this; }
    }

    @Override
    public IBinder onBind(Intent intent) { return mBinder; }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        // 주어진 장치를 사용한 후에는 자원을 올바르게 정리할 수 있도록
        // BluetoothGatt.close ()를 호출해야합니다.
        // 이 특정 예제에서 UI가 서비스에서 연결 해제되면 close ()가 호출됩니다.
        close();
        return super.onUnbind(intent);
    }
    private final IBinder mBinder = new BTLeServiceBinder();

    public boolean isConnect() {
        return isConnect;
    }

    /**
     * 로컬 블루투스 어댑터의 참조를 초기화 합니다
     * 초기화에 성공 하면 true를 반환
     * @return Return true if the initialization is successful.
     */
    public boolean initialize(){
        // API 레벨 18 이상인 경우 다음을 통해 BluetoothAdapter에 대한 참조를 얻으십시오.
        // BluetoothMAnager.
        if (mBluetoothManager == null){
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null){
                Log.e(TAG, "블루투스매니저를 초기화 못하겠다");
                return false;
            }
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null){
            Log.e(TAG, "블루투스를 못 얻어 오겠다.. ");
            return false;
        }
        return true;
    }

    /**
     * 드디어 블루투스 LE 장치에서 호스팅되는 GATT에 연결
     * @param address 대상장치의 장치 주소
     *
     * 연결이 성공적으로 시작되면 true를 리턴하십시오. 연결 결과는 다음을 통해 비동기 적으로보고됩니다
     * @return  {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */

    public boolean connect(final String address){

        System.out.println("카트에 연결 됬다 안됬다 됬다.....!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(address);
        if (mBluetoothAdapter == null  || address == null){
            Log.w(TAG, "블루투스 어댑터가 초기화 되지 않았거나 지정되지 않은 주소 ");
            return false;
        }

        // 이전 연결 장치를 다시 연결해 보자
        if (mBLE_Address != null && address.equals(mBLE_Address) && mBluetoothGatt != null){
            Log.d(TAG, "기존에 연결한 GATT를 사용하여 연결하려고 한다");
            if (mBluetoothGatt.connect()){

                mConnectionState = STATE_CONNECTING;
                return true;
            }
            else {
                System.out.println("저또 접속이 안되노... 왜이러지? 크흠~~ 조깟네..");
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null){
            Log.w(TAG, "장치를 찾을수 없습니다. 연결할수 없습니다.");
            return false;
        }

        // 우리는 장치에 직접 연결하기를 원하므로 자동 연결을 설정하고 있습니다.
        // 매개 변수를 false로 설정하십시오.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection...");

        mBLE_Address = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * 기존 연결을 끊거나 보류중인 연결을 취소합니다. 연결 해제 결과는 를 통해 비동기적으로 콜백 됩니다.
     *{@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *
     */

    public void disconnect(){
        if(mBluetoothAdapter == null || mBluetoothGatt == null){
            Log.w(TAG, "블루투스 어댑터가 초기화 되지 않았습니다.");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     *주어진 BLE 디바이스를 사용한 후 앱은이 메소드를 호출하여 리소스가 올바르게
     * 해제되도록해야합니다.
     */
    public void close(){

        if (mBluetoothGatt == null){
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**주어진
     *  {@code BluetoothGattCharacteristic}에 대한 읽기를 요청하십시오. 읽은 결과가보고됩니다 비동기를 통해 콜백함
     * {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic){
        if (mBluetoothAdapter == null || mBluetoothGatt == null){
            Log.w(TAG, "블루투스 어댑터가 초기화 되지 않음.");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }
    /**
     * 지정된 방법으로 쓰기요청
     * {@code BluetoothGattCharacteristic}
     * 콜백을 통해 비동기적으로
     * {@code BluetoothGattCallback#onCharacteristicWrite(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * @param characteristic The characteristic to read from.
     */
    public void writeCharacteristic(BluetoothGattCharacteristic characteristic){
        if (mBluetoothAdapter == null || mBluetoothGatt == null){
            Log.w(TAG, "블루투스 어댑터가 초기화 되지 않음.");
            return;
        }
        mBluetoothGatt.writeCharacteristic(characteristic);
    }
    /**
     * 주기적인 특성 통보에 대해 활성 비활성한다.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled if true, enable notification, False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled){
        if (mBluetoothAdapter == null || mBluetoothGatt == null){
            Log.w(TAG, "블루투스 어댑터가 초기화가 되지 않음");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                UUID.fromString(getString(R.string.CLIENT_CHARACTERISTIC_CONFIG)));
        if (enabled){
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        }
        else {
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        mBluetoothGatt.writeDescriptor(descriptor);
    }
    /**
     * 연결된 장치에서 지원되는 GATT 서비스 목록을 검색합니다. 성공적으로 완료된 후에 만 ​​호출해야합니다
     * {@code BluetoothGatt#discoverServices()}
     * @return A {@code List} of supported services
     */
    public List<BluetoothGattService> getSupportedGattServices(){
        if (mBluetoothGatt == null){
            return null;
        }
        return mBluetoothGatt.getServices();
    }

}

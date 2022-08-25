package com.intin.intin_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Result_Count extends AppCompatActivity {
    private final static String TAG = Result_Count.class.getSimpleName();
    // BLE 스캐너 연결부
    private BLE_Scanner_BTLE scan_BLE_intin;
    private BluetoothAdapter mBluetoothAdapter;
    private String address, name;
    private boolean mScanning = false;
    //    private Handler playaniHandler;
    private BluetoothGattCharacteristic characteristic;
    private BLE_BroadcastReceiver_BTLE_GATT mGattUpdateReceiver;
    private HashMap<String, BluetoothGattCharacteristic> characteristics_HashMap;
    private HashMap<String, ArrayList<BluetoothGattCharacteristic>> characteristics_HashMapList;
    private ArrayList<BluetoothGattService> services_ArrayList;
    private Intent mBTLE_Service_Intent;
    private BLE_Service_BTLE_GATT mBTLE_Services;
    private boolean mBTLE_Service_Bond;
    public static final String OVIEW_MAC_address = "4C:E1:73:C2:25:16";
    private String Wuuid = "00004a5b-0000-1000-8000-00805f9b34fb";
    public static final String EXTRA_NAME = "android.com.example.myapplication.Activity_BTLE_Services.NAME";
    public static final String EXTRA_ADDRESS = "android.com.example.myapplication.Activity_BTLE_Services.ADDRESS";
    private BLE_ListAdapter_BTLE_Services expandableListAdapter;

    //원래 카운트 다운을 위한 함수들
    private TextView time_Count, my_toolbar_text, set_title;
    private Button count_Start, wanRyo_btn, restart_Btn, start_btn_for_gatt, end_btn_for_gatt, off_btn_for_gatt, scan_BLE;

    private long basetime, pauseime;
    public static String statTime, result_lastTime, result_lastTime_1, getTime_1;
    public static final int INIT = 0;
    public static final int RUN = 1;
    public static final int PAUSE = 2;
    public static int status = INIT;
    public static long nowTime_1;
    int requsttime;
    public static final int START_BLE = 2;
    public static final int END_BLE = -2;
    public static final int BLE_ADAPTER = 3;
    final static int BLUETOOTH_REQUEST_CODE = 100;
    final static int BT_REQUEST_ENABLE = 1;

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

    public Result_Count() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__count);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        View mtoolbar = (View) findViewById(R.id.my_toolbar_2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(mtoolbar);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getApplicationContext(), "BLE 모드를 지원하지 않는 기기 입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
        my_toolbar_text = findViewById(R.id.my_toolbar_text);
        time_Count = findViewById(R.id.time_count);
        count_Start = findViewById(R.id.count_start_btn);               // 카운터 스타트 버튼
        wanRyo_btn = findViewById(R.id.wanryo_btn);
        restart_Btn = findViewById(R.id.resart_btn);
        set_title = findViewById(R.id.set_title);
        scan_BLE = findViewById(R.id.add_scan);                         // BLE 검색 버튼

        // 기기 명령어
        start_btn_for_gatt = (Button) findViewById(R.id.start_btn_for_gatt);
        off_btn_for_gatt = (Button) findViewById(R.id.off_btn_for_gatt);
        end_btn_for_gatt = (Button) findViewById(R.id.end_btn_for_gatt);

        services_ArrayList = new ArrayList<>();
        characteristics_HashMap = new HashMap<>();
        characteristics_HashMapList = new HashMap<>();
        expandableListAdapter = new BLE_ListAdapter_BTLE_Services(
                this, services_ArrayList, characteristics_HashMapList);

        set_title.setText(title);
        Drawable alpha_start_Button = count_Start.getBackground();
        alpha_start_Button.setAlpha(30);

//        long now = System.currentTimeMillis();
//        Date mdata = new Date(now);
//        SimpleDateFormat simpleDate = new SimpleDateFormat("MM");
//        String getTime = simpleDate.format(mdata);
        String getTime = new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()));
        my_toolbar_text.setText(getTime + "월");

        scan_BLE_intin = new BLE_Scanner_BTLE(this, 5000, -75);

        final BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    public void count_Start() {

        switch (status) {
            case INIT:
                basetime = System.currentTimeMillis();
                handler.sendEmptyMessage(0);
                time_Count.setText("00:00.00");
                count_Start.setBackgroundResource(R.mipmap.a2403);
                wanRyo_btn.setVisibility(View.GONE);
                statTime = starTime();
                result_lastTime = lastTime();
                handler.postDelayed(Result_count, 35000);
                set_Characteristic(0, 0, 0);
                status = RUN;
                break;
            case RUN:
                handler.removeMessages(0);
                handler.removeCallbacks(Result_count);
                handler.postDelayed(Result_count, 35000);
                pauseime = System.currentTimeMillis();
                restart_Btn.setVisibility(View.VISIBLE);
                count_Start.setBackgroundResource(R.mipmap.a2404);
                set_Characteristic(0, 0, 1);
                status = PAUSE;
                break;
            case PAUSE:
                long reStart = System.currentTimeMillis();
                basetime += (reStart - pauseime);
                handler.sendEmptyMessage(0);
                count_Start.setBackgroundResource(R.mipmap.a2403);
                restart_Btn.setVisibility(View.GONE);
                set_Characteristic(0, 0, 0);
                status = RUN;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }

    }// count_Start...

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void wanRyo(View view) {
        Finish_wanRyo();
    }

    public void Finish_wanRyo() {
        String starttime = statTime;
        String lasttime = result_lastTime_1;
        String gettime = getTime_1;
        Intent intent = new Intent();
        intent.putExtra("startime", starttime);
        intent.putExtra("lasttime", lasttime);
        intent.putExtra("gettime", gettime);
        if (mScanning == true) {
            set_Characteristic(0, 0, 2);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Finish_wanRyo();
        super.onBackPressed();
    }

    private String starTime() {
        String starTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            starTime = new SimpleDateFormat("HH:mm:ss.SS").format(new Date(System.currentTimeMillis()));
        }
        return starTime;
    }

    private String lastTime() {
        String lastTime_1 = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            lastTime_1 = new SimpleDateFormat("HH:mm:ss:SS").format(new Date(System.currentTimeMillis()));
        }
        return lastTime_1;
    }

    public String LastResult_time() {
        String[] timearray = result_lastTime.split(":");
        long hh1 = Long.parseLong(timearray[0]);
        long hh2 = Long.parseLong(timearray[1]);
        long hh3 = Long.parseLong(timearray[2]);
        long hh4 = Long.parseLong(timearray[3]);
        long nowTime = System.currentTimeMillis();
        long overTime = nowTime - basetime;
        long m = overTime / 1000 / 60;
        long s = (overTime / 1000) % 60;
        long ms = overTime % 100;
        long h = hh1;
        long mm = hh2 + m;
        long ss = hh3 + s;

        if (ss >= 60) {
            ss = ss % 60;
            mm = mm + 1;
        }
        long hm = hh4 + ms;
        String result = String.format("%02d:%02d:%02d.%02d", h, mm, ss, hm);
        return result;
    }

    public long getTime_1() {
        long nowTime = System.currentTimeMillis();
        return nowTime;
    }

    public void reSart(View view) {
        handler.removeCallbacks(Result_count);
        restart_Btn.setVisibility(View.GONE);
        basetime = 0;
        pauseime = 0;

        status = INIT;
        count_Start();
    }

    private String getTime() {
        long nowTime = System.currentTimeMillis();
        long overTime = nowTime - basetime;
        long m = overTime / 1000 / 60;
        long s = (overTime / 1000) % 60;
        long ms = overTime % 100;
        String recTime = String.format("%02d:%02d.%02d", m, s, ms);
        return recTime;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            time_Count.setText(getTime());
            handler.sendEmptyMessage(0);
        }
    };
    Runnable Result_count = new Runnable() {
        @Override
        public void run() {
            time_Count.setText(getTime());
            handler.removeMessages(0);
            wanRyo_btn.setVisibility(View.VISIBLE);
            count_Start.setBackgroundResource(R.mipmap.a2401);
            set_Characteristic(0, 0, 1);
            getTime_1 = getTime();
            result_lastTime_1 = LastResult_time();
            status = INIT;
            //Dialog.onClick(null, -1);
        }
    };

    public void Start(View view) {
        if (mScanning != true) {
            BLE_Utils.toast(getApplicationContext(), "기기와의 연결상태를 확인해 주세요.");
        } else {
           count_Start();
        }
    }

    public void BLE_connected(){
        mScanning = true;
        int backgroundColor = Color.parseColor("#FFFFFF");
        int textColor = Color.parseColor("#000000");
        scan_BLE.setBackgroundColor(backgroundColor);
        scan_BLE.setText("BLE");
        scan_BLE.setTextColor(textColor);
        Drawable alpha_start_Button = count_Start.getBackground();
        scan_BLE.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ble_stop_ani));
        alpha_start_Button.setAlpha(200);
       BLE_Utils.toast(getApplicationContext(), "BLE에 연결 되었습니다.");
    }

    public void BLE_not_connected(){
        int backgroundColor = Color.parseColor("#E91E65");
        scan_BLE.setBackgroundColor(backgroundColor);
        mScanning = false;
    }

    public void no_scann_BLE(){
        int backgroundColor = Color.parseColor("#FFFFFF");
        int textColor = Color.parseColor("#000000");
        scan_BLE.setBackgroundColor(backgroundColor);
        scan_BLE.setText("NO BLE");
        scan_BLE.setTextColor(textColor);
    }

    public void scann_BLE(View view) {

        if (!mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BLUETOOTH_REQUEST_CODE);
        } else if (mScanning != true) {
            int backgroundColor = Color.parseColor("#E91E65");
            int textColor = Color.parseColor("#FFFFFF");
            scan_BLE.setBackgroundColor(backgroundColor);
            scan_BLE.setText("SCAN..");
            scan_BLE.setTextColor(textColor);
            scan_BLE.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ble_scaning_ani));
            scan_BLE_intin.start();
        }
    }

    public void Scan_btn_ani() {
        int beforColor;
        int afterColor;
        if (((ColorDrawable) scan_BLE.getBackground()).getColor() == Color.parseColor("#E91E65")) {
            beforColor = Color.parseColor("#E91E65");
            afterColor = Color.parseColor("#0099FF");
        } else {
            beforColor = Color.parseColor("#0099FF");
            afterColor = Color.parseColor("#E91E65");
        }
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), beforColor, afterColor);
        colorAnimation.setDuration(200);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scan_BLE.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

//    Handler playaniHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Scan_btn_ani();
//            playaniHandler.sendEmptyMessage(0);
//        }
//    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode);
        System.out.println(resultCode);
        System.out.println(data);

        switch (requestCode) {
            case BLUETOOTH_REQUEST_CODE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스 활성화.", Toast.LENGTH_LONG).show();

                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스를 켜 주세요.", Toast.LENGTH_LONG).show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void stopScan() {
        scan_BLE_intin.stop();
    }

    public void addString(String add1, String add2) {
        address = add1;
        name = add2;
        System.out.println("야 티가 안난다... 넘어 온건지 아닌지 .. ㅅㅂ" + address);
        GATT_init_start();

    }

    public void GATT_init_start() {
        mGattUpdateReceiver = new BLE_BroadcastReceiver_BTLE_GATT(this);
        registerReceiver(mGattUpdateReceiver, BLE_Utils.makeGattUpdateIntentFilter());

        mBTLE_Service_Intent = new Intent(this, BLE_Service_BTLE_GATT.class);
        bindService(mBTLE_Service_Intent, mBTLE_ServiceConnection, Context.BIND_AUTO_CREATE);

        startService(mBTLE_Service_Intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mScanning == true) {
            unregisterReceiver(mGattUpdateReceiver);
            unbindService(mBTLE_ServiceConnection);
            mBTLE_Services.close();

        }
    }


    public void set_Characteristic(int groupPosition, int childPosition, int where) {
        BluetoothGattCharacteristic characteristic = characteristics_HashMapList.get(services_ArrayList.get(groupPosition).getUuid().toString()).get(childPosition);
        if (characteristic.getUuid().toString().equals(Wuuid)) {
            switch (where) {
                case 0:
                    if (mBTLE_Services != null) {
                        characteristic.setValue(start_btn_for_gatt.getText().toString());
                        mBTLE_Services.writeCharacteristic(characteristic);
                    }

                    break;
                case 1:
                    if (mBTLE_Services != null) {
                        characteristic.setValue(end_btn_for_gatt.getText().toString());
                        mBTLE_Services.writeCharacteristic(characteristic);
                    }

                    break;
                case 2:
                    if (mBTLE_Services != null) {
                        characteristic.setValue(off_btn_for_gatt.getText().toString());
                        mBTLE_Services.writeCharacteristic(characteristic);
                    }

                    break;
            }


        }
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


}//onCreate..

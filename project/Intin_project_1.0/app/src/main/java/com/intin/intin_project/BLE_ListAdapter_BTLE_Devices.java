package com.intin.intin_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;

public class BLE_ListAdapter_BTLE_Devices extends ArrayAdapter<BLE_BTLE_Device> {

    Activity activity;
    int layoutResourceID;
    ArrayList<BLE_BTLE_Device> devices;

    // 클래스를 초기화
    public BLE_ListAdapter_BTLE_Devices(Activity activity, int resource, ArrayList<BLE_BTLE_Device> objects) {
        super(activity.getApplicationContext(), resource, objects);
        System.out.println("디바이스 정보를 받아와서 뷰에 넣어준다 ");
        this.activity = activity;
        layoutResourceID = resource;
        devices = objects;
    }


    // 뷰에 어떻게 뿌릴건가 선언

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID, parent, false);
        }
        BLE_BTLE_Device device = devices.get(position);
        String name = device.getName();
        String address = device.getAddress();
        int rssi = device.getRSSI();

        TextView tv = null;
        // 디바이스의 이름을 찾아 리스트 뷰에 보여준다
        tv = (TextView) convertView.findViewById(R.id.tv_name);
        if (name != null && name.length() > 0) {
            tv.setText(device.getName());
        }
        else {
            tv.setText("이름없는 Device");
        }

        tv = (TextView) convertView.findViewById(R.id.tv_rssi);
        tv.setText("RSSI :" + Integer.toString(rssi));

        tv = convertView.findViewById(R.id.tv_macaddr);
        if (address != null && address.length() > 0) {
            tv.setText("MAC : " + device.getAddress());
        } else {
            tv.setText("MAC 주소가 없습니다.");
        }
        return convertView;
    }


}
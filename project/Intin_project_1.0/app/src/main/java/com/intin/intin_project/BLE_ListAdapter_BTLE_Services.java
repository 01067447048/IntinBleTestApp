package com.intin.intin_project;

import android.app.Activity;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class BLE_ListAdapter_BTLE_Services extends BaseExpandableListAdapter {

    private Activity activity, mm;
    private ArrayList<BluetoothGattService> services_ArrayList;
    private HashMap<String, ArrayList<BluetoothGattCharacteristic>> characteristics_HashMap;
    public static final int BLE_ADAPTER = 3;
    public int INN = 0;

    public BLE_ListAdapter_BTLE_Services(Activity activity, ArrayList<BluetoothGattService> listDataHeader,
                                     HashMap<String, ArrayList<BluetoothGattCharacteristic>> listChildData) {
        mm = new MainActivity();
        this.activity = activity;
        this.services_ArrayList = listDataHeader;
        this.characteristics_HashMap = listChildData;
    }

    @Override
    public int getGroupCount() {
        return services_ArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return characteristics_HashMap.get(
                services_ArrayList.get(groupPosition).getUuid().toString()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return services_ArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return characteristics_HashMap.get(
                services_ArrayList.get(groupPosition).getUuid().toString()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        BluetoothGattService bluetoothGattService = (BluetoothGattService) getGroup(groupPosition);

        String serviceUUID = bluetoothGattService.getUuid().toString();
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.btle_service_list_item, null);
        }
        if ( INN == 0) {
            TextView tv_service = (TextView) convertView.findViewById(R.id.tv_service_uuid);
            String a = Integer.toString(INN);
            tv_service.setText("테스트 S : OVIEW  " );
        }else {
            INN--;
        }
        INN++;

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        BluetoothGattCharacteristic bluetoothGattCharacteristic = (BluetoothGattCharacteristic) getChild(groupPosition, childPosition);


        String characteristicUUID = bluetoothGattCharacteristic.getUuid().toString();

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.btle_characteristice_list_item, null);
        }

        TextView tv_service = (TextView) convertView.findViewById(R.id.tv_characteristic_uuid);
        tv_service.setText("테스트 ON :  " + characteristicUUID);

//        tv_service.setText("C: " + characteristicUUID);

        int properties = bluetoothGattCharacteristic.getProperties();

        TextView tv_property = (TextView) convertView.findViewById(R.id.tv_properties);
        StringBuilder sb = new StringBuilder();

        if (BLE_Utils.hasReadProperty(properties) != 0) {
            sb.append("Read");
        }

        if (BLE_Utils.hasWriteProperty(properties) != 0) {
            sb.append("Write");
        }

        if (BLE_Utils.hasNotifyProperty(properties) != 0) {
            sb.append("Nnotify");
        }

        tv_property.setText(sb.toString());

        TextView tv_value = (TextView) convertView.findViewById(R.id.tv_value);

        byte[] data = bluetoothGattCharacteristic.getValue();
        if (data != null) {
            tv_value.setText("정보: " + BLE_Utils.hexToString(data));
        }
        else {
            tv_value.setText("정보: ---");
        }

        System.out.println(convertView);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
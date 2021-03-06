package com.example.hp.bluetoothfinder;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    Button button;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<String> deviceNameAddress=new ArrayList<>();
    ArrayList<String> addresses=new ArrayList<>();
    ArrayAdapter arrayAdapter;

    private final BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            Log.i("ACTION",action);
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                textView.setText("Finished");
                button.setEnabled(true);
            }else if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name=device.getName();
                String address= device.getAddress();
                String rssi=Integer.toString(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE));
                Log.i("Device Found","Name : "+name+" Address : "+address+" RSSI : "+rssi);

                if(!addresses.contains(address)) {
                    addresses.add(address);
                    if (name == null || name.equals("")) {

                        deviceNameAddress.add(address + " - RSSI " + rssi + "dBm");
                    } else {
                        deviceNameAddress.add(name + " - RSSI " + rssi + "dBm");
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    public void searchFunc(View view){
        textView.setText("Searching...");
        button.setEnabled(false);

        deviceNameAddress.clear();
        addresses.clear();
        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        bluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        listView=findViewById(R.id.listView);
        button=findViewById(R.id.button);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,deviceNameAddress);
        listView.setAdapter(arrayAdapter);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver,intentFilter);




    }
}

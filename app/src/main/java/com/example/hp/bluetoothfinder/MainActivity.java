package com.example.hp.bluetoothfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    Button button;

    public void searchFunc(View view){
        textView.setText("Searching...");
        button.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        listView=findViewById(R.id.listView);
        button=findViewById(R.id.button);
    }
}

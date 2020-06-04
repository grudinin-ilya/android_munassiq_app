package com.example.munassiq_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    float x1, x2, y1, y2;
    private List<String> data = new ArrayList<String>();
    private List<String> data1 = new ArrayList<String>();
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();
        String names = intent.getStringExtra("name").replace("]", "").replace("[", " ");
        String str[] = names.split(",");
        List<String> al = new ArrayList<String>();
        al = Arrays.asList(str);
        for (String s : al) {
            data.add(s);
        }

        String address = intent.getStringExtra("address").replace("]", "").replace("[", " ");
        String str1[] = address.split(",");
        List<String> al1 = new ArrayList<String>();
        al1 = Arrays.asList(str1);
        for (String s : al1) {
            data1.add(s);
        }

        table = (TableLayout) findViewById(R.id.myTableLayout);

        for (int i = 0; i < data.size(); i++) {
            TableRow row = new TableRow(this);

            String name_tab = data.get(i);
            String address_tab = data1.get(i);
            TextView tv1 = new TextView(this);
            tv1.setText(name_tab);
            tv1.setTextSize(15);
            tv1.setTextColor(Color.parseColor("#000000"));
            tv1.setBackgroundResource(R.drawable.border);
            TextView tv2 = new TextView(this);
            tv2.setText(address_tab);
            tv2.setTextSize(15);
            tv2.setTextColor(Color.parseColor("#000000"));
            tv2.setBackgroundResource(R.drawable.border);
            row.addView(tv1);
            row.addView(tv2);
            table.addView(row);
        }
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2) {
                    finish();
                }
        }
        return false;
    }

}

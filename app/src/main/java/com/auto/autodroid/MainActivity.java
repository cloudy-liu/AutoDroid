package com.auto.autodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "cloudy/MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button wechatReadBtn = (Button) findViewById(R.id.wechatbtn);
        Button permissionBtn = (Button) findViewById(R.id.permissionbtn);
        wechatReadBtn.setOnClickListener(this);
        permissionBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int resId = v.getId();
        if (resId == R.id.permissionbtn) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } else if (resId == R.id.wechatbtn) {
            Toast.makeText(this, "wechat read click", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "resId not support !!");
        }
    }
}
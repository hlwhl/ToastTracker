package com.linwei.electronicdream.toasttracker;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.TextView;

import com.linwei.electronicdream.toasttracker.Services.ToastTrackerService;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String SERVICE_ID="com.linwei.electronicdream.toasttracker/.Services.ToastTrackerService";
    Button settings;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(MainActivity.this, ToastTrackerService.class);
        startService(intent);
        settings=findViewById(R.id.btn_setting);
        status=findViewById(R.id.tv_status);
        getAndSetServiceStatus();
    }

    private void getAndSetServiceStatus(){
        if(!isAccessibilityEnabled(getApplicationContext(), SERVICE_ID)){
            status.setText("监测服务状态:关");
            settings.setVisibility(View.VISIBLE);
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
            });
        } else {
            settings.setVisibility(View.GONE);
            status.setText("监测服务状态:开");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAndSetServiceStatus();
    }

    public static boolean isAccessibilityEnabled(Context context, String id) {
        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo service : runningServices) {
            if (id.equals(service.getId())) {
                return true;
            }
        }
        return false;
    }

}

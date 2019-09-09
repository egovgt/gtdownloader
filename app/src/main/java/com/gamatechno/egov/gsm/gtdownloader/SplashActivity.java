package com.gamatechno.egov.gsm.gtdownloader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {
    private View mContentView;
    String[] appPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1240;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mContentView = findViewById(R.id.iv_icon);

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if(checkPermission()){
            initApp();
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                initApp();
//            }
//        },2000);
    }

    public void initApp(){
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }

    public boolean checkPermission(){
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String perm : appPermissions){
            if(ContextCompat.checkSelfPermission(this,perm) != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(perm);
            }
        }

        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,
                    listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
                    PERMISSION_REQUEST_CODE
            );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCode = 0;

            for (int i=0; i<grantResults.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCode++;
                }
            }
            if(deniedCode == 0){
                initApp();
            }
        }
    }
}

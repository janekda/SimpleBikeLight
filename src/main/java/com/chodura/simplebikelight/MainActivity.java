package com.chodura.simplebikelight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private OrientationListener orientationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFullDisplayForLight();
        setContentView(R.layout.activity_main);
        awakeDevice();

        orientationListener = new OrientationListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        orientationListener.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        orientationListener.stopListening();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_MENU){
            showPreferences();
        }

        //Toast.makeText(this, "text to show ", Toast.LENGTH_LONG).show();
        return super.onKeyDown(keyCode, event);
    }


    private void showFullDisplayForLight(){

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(View.SYSTEM_UI_FLAG_FULLSCREEN, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void awakeDevice() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void showPreferences(){

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}

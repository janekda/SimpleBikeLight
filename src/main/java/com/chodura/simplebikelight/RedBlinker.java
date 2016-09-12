package com.chodura.simplebikelight;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

public class RedBlinker {

    private RelativeLayout layout;
    private Handler handler;
    private AnimationDrawable drawable;

    public RedBlinker(RelativeLayout layout){
        this.layout = layout;

        handler = new Handler();
        drawable = new AnimationDrawable();

        drawable.addFrame(new ColorDrawable(Color.BLACK), 200);
        drawable.addFrame(new ColorDrawable(Color.RED), 400);

        drawable.setOneShot(false);


    }

    public void start(){

        this.layout.setBackgroundDrawable(drawable);

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("RedBlinker", "blinker runnning");
                drawable.start();
            }
        });
    }

    public void stop(){
        Log.i("RedBlinker", "blinker stopped");
        drawable.stop();
        this.layout.setBackgroundDrawable(null);
    }

}

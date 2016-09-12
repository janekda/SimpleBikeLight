package com.chodura.simplebikelight;

import android.app.Activity;
import android.graphics.Color;
import android.widget.RelativeLayout;

public class BikeLight {

    private final RelativeLayout background;
    private RedBlinker redBlinker;
    private BackLightBeeper beeper;
    private boolean isFrontLight = false;

    public BikeLight(final Activity activity){

        background = (RelativeLayout) activity.findViewById(R.id.backgroundView);
        initBackLight();
    }

    private void initBackLight() {

        redBlinker = new RedBlinker(background);
        beeper = new BackLightBeeper(background);
    }

    public void start(){

        beeper.beep();
    }

    public void stop(){

        beeper.stop();
    }

    public void changeBackground(){

        isFrontLight = !isFrontLight;

        if(isFrontLight){

            startFrontLight();
        }
        else{

            startBackLight();
        }

    }

    private void startFrontLight() {

        redBlinker.stop();
        beeper.stop();
        background.setBackgroundColor(Color.WHITE);
    }

    private void startBackLight() {

        redBlinker.start();
        beeper.beep();
    }

}
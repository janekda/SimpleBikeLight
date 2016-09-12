package com.chodura.simplebikelight;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class OrientationListener implements SensorEventListener{

    private final WindowManager windowManger;
    private final SensorManager sensorManager;
    private final ShakeDetector shakeDetector;
    private final BikeLight bikeLight;

    public OrientationListener(final Activity activity){

        windowManger = activity.getWindow().getWindowManager();
        sensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
        shakeDetector = new ShakeDetector (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        bikeLight = new BikeLight(activity);

    }

    public void startListening() {

        bikeLight.start();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopListening() {

        sensorManager.unregisterListener(this);
        bikeLight.stop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(shakeDetector.isShaken(event)){
            bikeLight.changeBackground();
        }
    }

}

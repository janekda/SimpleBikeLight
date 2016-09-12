package com.chodura.simplebikelight;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class ShakeDetector {

    private static final int MICROMETERS = 10000;
    private final static int DELAY_BETWEEN_SHAKES_IN_MS = 2500;
    private static final int SHAKE_THRESHOLD_IN_MICROMETER = 150;

    private final Sensor accelerometer;
    private long lastUpdate;
    private long lastShakenTime;
    private float lastSpeed;
    private float[] lastCoordinates = new float[3];

    private int i=0;

    public ShakeDetector(Sensor sensor) {

        if (sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            Log.i("ShakeDetector", "it is not accelerometer");
        }

        this.accelerometer = sensor;
        initLastVariables();
    }

    private void initLastVariables() {

        lastUpdate = System.currentTimeMillis();
        lastShakenTime = lastUpdate - 3000;
        lastSpeed = 0;

        lastCoordinates[0] = 0;
        lastCoordinates[1] = 0;
        lastCoordinates[2] = 0;
    }

    public boolean isShaken(SensorEvent event) {

        float[] actualCoordinates = event.values;
        boolean isShaken = false;

        long currentTime = System.currentTimeMillis();
        long differentTimeBetweenShakes = (currentTime - lastShakenTime);

        float absoluteSpeed = getAbsoluteSpeed(actualCoordinates, currentTime);
        System.arraycopy(actualCoordinates, 0, lastCoordinates, 0, 3);

        boolean isShakenByUser = isRelativelyShaken(absoluteSpeed);

        if (isTooEarlyShake(differentTimeBetweenShakes)) {
            return false;
        }

        if (isShakenByUser) {

            isShaken = getShake(currentTime, absoluteSpeed);
        }

        return isShaken;
    }

    private boolean isRelativelyShaken(float absoluteSpeed) {

        boolean isSpeedChange = Math.abs((absoluteSpeed - lastSpeed)/absoluteSpeed) > 0.3;
        boolean isRelativelyShaken = (absoluteSpeed > SHAKE_THRESHOLD_IN_MICROMETER) && isSpeedChange;

        return isRelativelyShaken;
    }

    private float getAbsoluteSpeed(float[] actualCoordinates, long currentTime) {
        long differentTimeBetweenSensorUpdate = currentTime - lastUpdate;
        lastUpdate = currentTime;

        float absoluteDistance = getAbsoluteDistance(actualCoordinates);
        return absoluteDistance/differentTimeBetweenSensorUpdate * MICROMETERS;
    }

    private boolean getShake(long currentTime, float speed) {

        Log.i("ShakeDetector", "speed= " + speed + ", is bigger shake");
        lastShakenTime = currentTime;

        return true;
    }

    private float getAbsoluteDistance(float[] actualCoordinated) {

        float dx = actualCoordinated[0]- lastCoordinates[0];
        float dy = actualCoordinated[1]- lastCoordinates[1];
        float dz = actualCoordinated[2]- lastCoordinates[2];

        return Math.abs(dx + dy + dz);
    }

    private boolean isTooEarlyShake(long differentTime) {

       return (differentTime < DELAY_BETWEEN_SHAKES_IN_MS);
    }
}

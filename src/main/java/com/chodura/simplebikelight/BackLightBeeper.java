package com.chodura.simplebikelight;


import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.view.View;

public class BackLightBeeper {

    private static final int INTERVAL = 4000;
    private Handler handler;
    private ToneGenerator toneGenerator;
    private Runnable runnable;

    public BackLightBeeper(View view) {

        handler = new Handler();
        toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        runnable = createRunnable();
    }

    public void beep() {

        handler.postDelayed(runnable, 0);

    }

    private Runnable createRunnable(){

        return new Runnable() {
            @Override
            public void run() {
                try {
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 200);
                } catch (Exception e){

                }
                finally {
                    handler.postDelayed(this, INTERVAL);
                }
            }
        };
    }

    public void stop() {

        handler.removeCallbacks(runnable);
    }

}

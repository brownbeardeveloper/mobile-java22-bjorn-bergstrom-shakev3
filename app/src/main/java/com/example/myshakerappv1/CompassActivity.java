package com.example.myshakerappv1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private static final long MIN_TIME_BETWEEN_UPDATES = 100;
    private ImageView compassImage;
    private float currentDegree = 0f;
    private SensorManager sensorManager;
    private long currentTime;
    private long lastUpdateTime;

    float[] mGravity;
    float[] mGeomagnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassImage = findViewById(R.id.compass);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        currentTime = System.currentTimeMillis();

        if ((currentTime - lastUpdateTime) >= MIN_TIME_BETWEEN_UPDATES) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity =  event.values;
                Log.i("AccelerometerValues", Arrays.toString(mGravity));
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mGeomagnetic =  event.values;
                Log.i("MagnetometerValues", Arrays.toString(mGeomagnetic));
            }

            if (mGravity != null && mGeomagnetic != null) {

                float rotationMatrix[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, mGravity, mGeomagnetic);

                Log.i("RotationMatrix", Arrays.toString(rotationMatrix));

                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(rotationMatrix, orientation);
                    float azimuthInRadians = orientation[0];
                    float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

                    RotateAnimation ra = new RotateAnimation(
                            currentDegree,
                            -azimuthInDegrees,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f
                    );
                    ra.setDuration(210);
                    ra.setFillAfter(true);

                    compassImage.startAnimation(ra);
                    currentDegree = -azimuthInDegrees;

                    Log.i("azimuthInDegrees", String.valueOf(azimuthInDegrees));
                }
            }

            lastUpdateTime = currentTime;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

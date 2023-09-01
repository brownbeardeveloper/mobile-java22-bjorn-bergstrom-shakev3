package com.example.myshakerappv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private ProgressBar progressbarX, progressbarY, progressbarZ;
    float accelerometerX, accelerometerY, accelerometerZ;
    private long lastUpdate = 0;
    private static final int SHAKE_THRESHOLD = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer_sensor);

        progressbarX = findViewById(R.id.accelerometer_progressbarX);
        progressbarY = findViewById(R.id.accelerometer_progressbarY);
        progressbarZ = findViewById(R.id.accelerometer_progressbarZ);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent e) {

        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {

                accelerometerX = e.values[0];
                accelerometerY = e.values[1];
                accelerometerZ = e.values[2];

                float magnitude = (float) Math.sqrt(accelerometerX * accelerometerX + accelerometerY * accelerometerY + accelerometerZ * accelerometerZ);
                Log.i("magnitude",String.valueOf(magnitude));

                if (magnitude > SHAKE_THRESHOLD) {
                    Toast.makeText(this, "SHAKE THRESHOLD", Toast.LENGTH_SHORT).show();
                }

                String formattedX = String.format("%.2f", accelerometerX);
                String formattedY = String.format("%.2f", accelerometerY);
                String formattedZ = String.format("%.2f", accelerometerZ);

                TextView accelerationTextView = findViewById(R.id.accelerometer_text);
                accelerationTextView.setText("Accelerometer X: " + formattedX + "\nAccelerometer Y: " + formattedY + "\nAccelerometer Z: " + formattedZ);

                progressbarX.setProgress((int)accelerometerX*10);
                progressbarY.setProgress((int)accelerometerY*10);
                progressbarZ.setProgress((int)accelerometerZ*10);

                lastUpdate = curTime;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public AccelerometerActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
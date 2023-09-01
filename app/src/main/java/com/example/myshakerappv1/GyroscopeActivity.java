package com.example.myshakerappv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GyroscopeActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor gyroscopeSensor;
    WebView web;
    WebSettings webSettings;

    public GyroscopeActivity() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        web = (WebView) findViewById(R.id.webview_gyroscope);
        webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        web.loadUrl("https://www.berkshirehathaway.com/");
        web.setWebViewClient(new WebViewClient());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent e) {

        if (e.sensor.getType() == Sensor.TYPE_GYROSCOPE){

            String gyroscopeX = String.format("%.2f", e.values[0]);
            String gyroscopeY = String.format("%.2f", e.values[1]);
            String gyroscopeZ = String.format("%.2f", e.values[2]);

            TextView gyroscopeTextView = findViewById(R.id.gyroscope_text);
            gyroscopeTextView.setText("Gyroscope X: " + gyroscopeX + "\nGyroscope Y: " + gyroscopeY + "\nGyroscope Z: " + gyroscopeZ);

            // Calculate the magnitude of the gyroscope vector
            double gyroMagnitude = Math.sqrt(e.values[0] * e.values[0] * e.values[1] * e.values[1] * e.values[2] * e.values[2]);
            Log.i("gyroMagnitude",String.valueOf(gyroMagnitude));

            if( gyroMagnitude > 5  ){
                Toast.makeText(this,"Keep up the good work!", Toast.LENGTH_SHORT).show();
           }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
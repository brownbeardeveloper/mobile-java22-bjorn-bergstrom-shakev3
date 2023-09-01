package com.example.myshakerappv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch switchView;
    Switch fragmentSwitch;
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fragmentSwitch = findViewById(R.id.fragment_switch);

        fm.beginTransaction()
                .add(R.id.mainLayout, MainFragmentGreen.class, null)
                .commit();
    }

    public void launchMainFragment(View v){

        if(fragmentSwitch.isChecked()) {
            fm.beginTransaction()
                    .add(R.id.mainLayout, MainFragmentYellowGreen.class, null)
                    .commit();
        } else {
            fm.beginTransaction()
                    .add(R.id.mainLayout, MainFragmentGreen.class, null)
                    .commit();
        }
    }

    public void launchSensorActivity(View v, int switchId, Class<?> activityClass) {
        switchView = findViewById(switchId);
        switchView.setChecked(false);

        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
    public void launchAccelerometerActivity(View v) {
        launchSensorActivity(v, R.id.accelerometer_switch,AccelerometerActivity.class);
    }
    public void launchGyroscopeActivity(View v) {
        launchSensorActivity(v, R.id.gyroscope_switch,GyroscopeActivity.class);
    }
    public void launchMagneticFieldActivity(View v) {
        launchSensorActivity(v, R.id.magnetic_switch, MagneticFieldActivity.class);
    }
    public void launchCompassActivity(View v) {
        launchSensorActivity(v, R.id.compass_switch, CompassActivity.class);
    }
}

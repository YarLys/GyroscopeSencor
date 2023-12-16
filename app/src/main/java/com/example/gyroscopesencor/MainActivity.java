package com.example.gyroscopesencor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Sensor mAccelerometer;
    Sensor mMagnetic;
    SensorManager mSensorManager;

    TextView xyAngle;
    TextView xzAngle;
    TextView zyAngle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xyAngle = findViewById(R.id.tv1);
        xzAngle = findViewById(R.id.tv2);
        zyAngle = findViewById(R.id.tv3);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    float[] accel = new float[3];
    float[] magnet = new float[3];

    float[] rotationmatrix = new float[16];
    float[] orientation = new float[3];

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accel = sensorEvent.values.clone();
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnet = sensorEvent.values.clone();
        }
        SensorManager.getRotationMatrix(rotationmatrix, null, accel, magnet);
        SensorManager.getOrientation(rotationmatrix, orientation);

        xyAngle.setText(String.valueOf(Math.round(Math.toDegrees(orientation[0]))));
        xzAngle.setText(String.valueOf(Math.round(Math.toDegrees(orientation[1]))));
        zyAngle.setText(String.valueOf(Math.round(Math.toDegrees(orientation[2]))));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        
    }
}
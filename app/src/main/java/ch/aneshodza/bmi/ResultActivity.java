package ch.aneshodza.bmi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    BmiService bmiService;
    boolean mBound = false;
    double weight;
    double height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Intent service = new Intent(ResultActivity.this, BmiService.class);
        bindService(service, connection, Context.BIND_AUTO_CREATE);

        weight = intent.getDoubleExtra("weight", 0);
        height = intent.getDoubleExtra("height", 0);
        setContentView(R.layout.activity_result);
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BmiService.LocalBinder binder = (BmiService.LocalBinder) service;
            bmiService = binder.getService();
            mBound = true;
            double result = bmiService.calcBmi(height, weight);
            ((TextView) findViewById(R.id.bmi)).setText(String.valueOf(result).substring(0, 4));
            ((TextView) findViewById(R.id.classification)).setText(result > 25 ? "You are Overweight" : result < 18.5 ? "You are Undeweight" : "You are normal weight");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}

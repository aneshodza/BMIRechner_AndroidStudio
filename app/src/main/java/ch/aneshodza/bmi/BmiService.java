package ch.aneshodza.bmi;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BmiService extends Service {
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        BmiService getService() {
            return BmiService.this;
        }
    }

    public double calcBmi(double height, double weight) {
        return (weight/(height*height));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }
}
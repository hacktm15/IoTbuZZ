package ro.hacktm.smarthome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by user on 07.11.2015.
 */
public class BeaconService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package ro.hacktm.smarthome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.onyxbeacon.OnyxBeaconApplication;
import com.onyxbeacon.listeners.OnyxBeaconsListener;

import java.util.ArrayList;

/**
 * Created by user on 07.11.2015.
 */
public class ContentReceiver extends BroadcastReceiver {
    private static ContentReceiver sInstance;
    private OnyxBeaconsListener mOnyxBeaconListener;

    public ContentReceiver() {}

    public static ContentReceiver getInstance() {
        if (sInstance == null) {
            sInstance = new ContentReceiver();
            return sInstance;
        } else {
            return sInstance;
        }
    }

/*    public ContentReceiver(OnyxBeaconsListener onyxBeaconSDKListener){
        mOnyxBeaconListener = new OnyxBeaconsListener() {
            @Override
            public void didRangeBeaconsInRegion(List<IBeacon> list) {
                Log.i("ro.hacktm.smarthome", "TAG aaaaaaaaaa " + list.toString());
            }

            @Override
            public void onError(int i, Exception e) {
                Log.i("ro.hacktm.smarthome", "TAG aaaaaaaaaa ");
            }
        };
    }*/

    public void setOnyxBeaconsListener(OnyxBeaconsListener onyxBeaconListener) {
        mOnyxBeaconListener = onyxBeaconListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String payloadType = intent.getStringExtra(OnyxBeaconApplication.PAYLOAD_TYPE);
        Log.i("ro.hacktm.smarthome", "TAG aaaaaaaaaa ");
        if(payloadType.equals(OnyxBeaconApplication.BEACON_TYPE)){
            ArrayList beacons = intent.getParcelableArrayListExtra(OnyxBeaconApplication.EXTRA_BEACONS);
            Log.i("ro.hacktm.smarthome", "TAG " + beacons);
        }
    }
}
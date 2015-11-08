package ro.hacktm.smarthome;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by user on 30.08.2015.
 */
@ReportsCrashes( // will not be used
        mailTo = "victor.holotescu@yahoo.ro",
        mode = ReportingInteractionMode.DIALOG,
        resDialogText = 1,
        resDialogIcon = android.R.drawable.ic_dialog_info
)
public class SmartHomeMain extends Application
{


    public void onCreate(){
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                e.printStackTrace();
                //Send Report to Crashlytics. Crashlytics will send it as soon as it starts to work
                //Crashlytics.logException(e);

                //Your custom codes to Restart the app or handle this crash
                // handleCrashes();
            }
        });
    }
}

package gwt.com.basicapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ReportLocationService extends Service {

    private String TAG = this.getClass().getName();
    public ReportLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.i(TAG, "onBind success");
        
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //throw new RuntimeException("Stub!");
        Log.i(TAG, "onUnbind success");
        return true;
    }

    @Override
    public void onCreate(){
        Log.i(TAG, "onCreate success");
    }
}

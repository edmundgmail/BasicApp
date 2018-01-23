package gwt.com.basicapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class TrackLocationService extends FirebaseMessagingService {
    private static final String TAG = "TrackLocationService";
    private static String mBusId = null;


     @Override
     public void onCreate()
     {

         SharedPreferences sharedPreferences = getSharedPreferences("mysettings", 0);
         mBusId = sharedPreferences.getString("busId", "");
         Log.i(TAG, "busId = " + mBusId);

         FirebaseMessaging.getInstance().subscribeToTopic(mBusId);
     }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if(mBusId!=null && !mBusId.equals("")) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(mBusId);
        }

    }

    private void sendMessage(double lat, double lon, float bear){
         Intent intent = new Intent();
         intent.setAction("unique_name");
         intent.putExtra("latitude", lat);
         intent.putExtra("longitude", lon);
         intent.putExtra("bearing", bear);
         this.sendBroadcast(intent);
     }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try{
            Log.d(TAG, "message = " + remoteMessage.getNotification().getBody());
            JSONObject jObject = new JSONObject(remoteMessage.getNotification().getBody());
            double lat = jObject.getDouble("latitude");
            double lon = jObject.getDouble("longitude");
            float bear = (float)jObject.getDouble("bearing");

            sendMessage(lat, lon,bear);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

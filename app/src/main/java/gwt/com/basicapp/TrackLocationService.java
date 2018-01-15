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

     @Override
     public void onCreate()
     {

         SharedPreferences sharedPreferences = getSharedPreferences("mysettings", 0);
         String busId = sharedPreferences.getString("busId", "busId not found");
         Log.i(TAG, "busId = " + busId);

         FirebaseMessaging.getInstance().subscribeToTopic("bus1234");
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

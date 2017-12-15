package gwt.com.basicapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class TrackLocationService extends FirebaseMessagingService {
    private static final String TAG = "TrackLocationService";

     @Override
     public void onCreate(){
         FirebaseMessaging.getInstance().subscribeToTopic("bus");
     }

     private void sendMessage(double lat, double lon, float bear){
         Intent intent = new Intent();
         intent.setAction("unique_name");
         intent.putExtra("lat", lat);
         intent.putExtra("lon", lon);
         intent.putExtra("bear", bear);
         this.sendBroadcast(intent);
     }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try{
            Log.d(TAG, "message = " + remoteMessage.getNotification().getBody());
            JSONObject jObject = new JSONObject(remoteMessage.getNotification().getBody());
            double lat = jObject.getDouble("lat");
            double lon = jObject.getDouble("lon");
            float bear = (float)jObject.getDouble("bear");

            sendMessage(lat, lon,bear);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

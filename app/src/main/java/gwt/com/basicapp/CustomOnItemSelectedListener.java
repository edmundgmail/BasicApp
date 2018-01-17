package gwt.com.basicapp;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    private static final String TAG = "CustomOnItemSelected";

    public void onItemSelected(final AdapterView<?> parent, View view, int pos, long id) {

        final SharedPreferences sharedPreferences = parent.getContext().getSharedPreferences("mysettings", 0);
        final String selected =  parent.getItemAtPosition(pos).toString();
        Log.d(TAG, "selected = " + selected);


        DatabaseReference busesProfile = FirebaseDatabase.getInstance().getReference("buses").child(selected);
        BusProfile busProfile = new BusProfile();
        busProfile.id = selected;
        busProfile.stops = new ArrayList<>();
        busProfile.stops.add(new SimpleLocation(40.0,30.0));
        busProfile.stops.add(new SimpleLocation(41, 31));
        busesProfile.setValue(selected, busProfile);
        busesProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BusProfile profile = dataSnapshot.getValue(BusProfile.class);
                if(profile!=null){
                    String stops = TextUtils.join("|", profile.stops);
                    Log.d(TAG, "stops are " + stops);
                    sharedPreferences.edit().putString(selected, stops);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sharedPreferences.edit().putString("busId", selected).commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

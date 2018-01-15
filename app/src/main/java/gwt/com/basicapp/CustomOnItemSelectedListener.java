package gwt.com.basicapp;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    private static final String TAG = "CustomOnItemSelected";

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

        SharedPreferences sharedPreferences = parent.getContext().getSharedPreferences("mysettings", 0);
        String selected =  parent.getItemAtPosition(pos).toString();
        Log.d(TAG, "selected = " + selected);
        sharedPreferences.edit().putString("busId", selected).commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

package com.example.clevertapjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;

import java.util.ArrayList;

public class NativeDisplayActivity extends AppCompatActivity implements DisplayUnitListener {

    String displayUnitString = "";
    TextView textNativeDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_display);
        CleverTapAPI.getDefaultInstance(getApplicationContext()).pushEvent("NativeScreen");
        textNativeDisplay = findViewById(R.id.text_native_display_units);
        CleverTapAPI.getDefaultInstance(getApplicationContext()).setDisplayUnitListener(this);
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        // you will get display units here
        // implement your logic to create your display views using these Display Units here
        Toast.makeText(this, "Number of display units: "+units.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i <units.size() ; i++) {
            CleverTapDisplayUnit unit = units.get(i);
            prepareDisplayView(unit);
            CleverTapAPI.getDefaultInstance(this).pushDisplayUnitViewedEventForID(unit.getUnitID());

        }
        textNativeDisplay.setText(displayUnitString);
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {
        Log.d("NATIVE", unit.toString());
        displayUnitString = "" +displayUnitString + (unit.toString());
    }
}
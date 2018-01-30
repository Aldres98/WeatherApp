package com.example.aldres.workingwithapis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

public class CitySelectionActivity extends AppCompatActivity {

    Button showForecast;
    LatLng cityLatLong;
    String cityLat;
    String cityLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        showForecast = findViewById(R.id.show_forecast);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("place", "Place: " + place.getLatLng());
                cityLatLong = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Place", "An error occurred: " + status);
            }
        });




        showForecast.setOnClickListener(v -> {
            cityLat = Double.toString(cityLatLong.latitude).substring(0,8);
            cityLong = Double.toString(cityLatLong.longitude).substring(0,8);
            Intent intent = new Intent(CitySelectionActivity.this, MainActivity.class);
            intent.putExtra("cityLat", cityLat );
            intent.putExtra("cityLong", cityLong);
            startActivity(intent);

        });
    }
}

package com.fmm.uberprototype;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class SearchSheetActivity extends AppCompatActivity {

    EditText txtBegin, txtEnd;
    Button btnConfirm;
    ImageButton btnBack, btnMarkerBegin, btnMarkerEnd;
    LatLng latLngBegin, latLngEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_sheet);
        txtBegin = findViewById(R.id.search_sheet_et_begin);
        txtEnd = findViewById(R.id.search_sheet_et_end);
        btnConfirm = findViewById(R.id.search_sheet_bt_confirm);
        btnBack = findViewById(R.id.search_sheet_bt_back);
        btnMarkerBegin = findViewById(R.id.search_sheet_bt_mark_begin);
        btnMarkerEnd = findViewById(R.id.search_sheet_bt_mark_end);

        Places.initialize(getApplicationContext(), "AIzaSyD4cEW3sxpuhDvV0THxrjF7Oup18KKhOE8");

        txtBegin.setFocusable(false);
        txtBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(SearchSheetActivity.this);

                startActivityForResult(intent, 1);
            }
        });

        txtEnd.setFocusable(false);
        txtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(SearchSheetActivity.this);

                startActivityForResult(intent, 2);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latLngBegin != null && latLngEnd != null){
                    Intent intent = new Intent(SearchSheetActivity.this, ChooseDriverActivity.class);
                    intent.putExtra("PosBegin", latLngBegin);
                    intent.putExtra("PosEnd", latLngEnd);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnMarkerBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSheetActivity.this, DragLocationActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        btnMarkerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSheetActivity.this, DragLocationActivity.class);
                startActivityForResult(intent, 4);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSheetActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            txtBegin.setText(place.getName());
            latLngBegin = place.getLatLng();
        }
        else if(requestCode == 2 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            txtEnd.setText(place.getName());
            latLngEnd = place.getLatLng();
        }
        else if(requestCode == 3 && resultCode == RESULT_OK){
            txtBegin.setText("Partida personalizada");
            latLngBegin = data.getExtras().getParcelable("local");
        }else if(requestCode == 4 && resultCode == RESULT_OK){
            txtEnd.setText("Destino personalizado");
            latLngEnd = data.getExtras().getParcelable("local");
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){

            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

package com.fmm.uberprototype;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DragLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView pin;
    Button btnConfirm, btnCancel;
    Marker currentMarker;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pin = findViewById(R.id.activity_drag_img_pin);
        btnConfirm = findViewById(R.id.activity_drag_location_bt_confirm);
        btnCancel = findViewById(R.id.activity_drag_location_bt_cancel);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent local = new Intent();
                local.putExtra("local", latLng);
                setResult(RESULT_OK, local);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent local = new Intent();
                local.putExtra("local", latLng);
                setResult(RESULT_CANCELED, local);
                finish();
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        final LatLng currentPosition = new LatLng(-3.1345819, -59.981451);
        currentMarker = mMap.addMarker(new MarkerOptions().position(currentPosition).title("Ponto"));
        // Add a marker in Sydney and move the camera
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition
                (new CameraPosition.Builder().target(currentPosition)
                        .zoom(15).build()));

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = mMap.getCameraPosition().target;
                if(currentMarker != null)
                {
                    currentMarker.remove();
                    currentMarker = mMap.addMarker(new MarkerOptions().position(center).title("Ponto"));
                    latLng = currentMarker.getPosition();
                }
            }
        });
    }
}

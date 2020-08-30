package com.fmm.uberprototype;

import androidx.annotation.RequiresApi;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class ChooseDriverActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LatLng Begin, End;
    private List<CarUberItem> carUberItems;
    private ListView listUber;

    private LinearLayout linearLayout;
    private MarkerOptions destination_marker, begin_marker;
    private Bitmap carBitmapIcon;
    private Button btnStart;
    private Handler handler;
    private Marker prvMarker;

    private int driver, cont = 1;
    final static int ratio = 20;
    final static int frames = 700;
    double Lat, Lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_driver);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_maps_map);
        mapFragment.getMapAsync(this);
        setThings();
        setList();
        handler = new Handler();
    }

    private void setList() {
        carUberItems = new ArrayList<>();

        carUberItems.add(new CarUberItem((R.drawable.kzeca), "Zeca", "R$10,00"));
        carUberItems.add(new CarUberItem((R.drawable.arley), "Arlenha", "R$100,00"));
        carUberItems.add(new CarUberItem((R.drawable.klinsman), "Klinsman", "R$1000,00"));

        listUber.setAdapter(new ListUberAdapter(getApplicationContext(), carUberItems));

        listUber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                driver = position;
                if(driver == 0) {
                    Toast.makeText(getApplicationContext(), " Você escolheu o Zequinha Gameplays!", Toast.LENGTH_SHORT).show();
                    Drawable kzecaDrawable = getDrawable(R.drawable.kzeca);
                    Bitmap kzecaIconBitmap = ((BitmapDrawable)kzecaDrawable).getBitmap();
                    Bitmap kzecaBitmapIcon = Bitmap.createScaledBitmap(kzecaIconBitmap, 100, 100, false);
                    carBitmapIcon = kzecaBitmapIcon;
                }
                if(driver == 1) {
                    Toast.makeText(getApplicationContext(), "Você escolheu o Arlenha!", Toast.LENGTH_SHORT).show();
                    Drawable arleyDrawable = getDrawable(R.drawable.arley);
                    Bitmap arleyIconBitmap = ((BitmapDrawable)arleyDrawable).getBitmap();
                    Bitmap arleyBitmapIcon = Bitmap.createScaledBitmap(arleyIconBitmap, 100, 100, false);
                    carBitmapIcon =arleyBitmapIcon;
                    
                }
                if(driver == 2){ 
                    Toast.makeText(getApplicationContext(), "Você escolheu o Mestre Klinsman!", Toast.LENGTH_SHORT).show();
                    Drawable klinsmanDrawable = getDrawable(R.drawable.klinsman);
                    Bitmap klinsmanIconBitmap = ((BitmapDrawable)klinsmanDrawable).getBitmap();
                    Bitmap klinsmanBitmapIcon = Bitmap.createScaledBitmap(klinsmanIconBitmap, 100, 100, false);
                    carBitmapIcon = klinsmanBitmapIcon;
                }
            }
        });

    }

    private void setThings() {
        btnStart = findViewById(R.id.choose_driver_bt_confirm);
        Begin = getIntent().getExtras().getParcelable("PosBegin");
        End = getIntent().getExtras().getParcelable("PosEnd");
        linearLayout = findViewById(R.id.choose_driver_layout_drivers);
        listUber = findViewById(R.id.choose_driver_list);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Begin));
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_json));
        Drawable PinDrawable = getDrawable(R.drawable.pin);
        Bitmap PinIconBitmap = ((BitmapDrawable) PinDrawable).getBitmap();
        Bitmap PinBitmapIcon = Bitmap.createScaledBitmap(PinIconBitmap, 100, 100, false);

        destination_marker = new MarkerOptions();
        destination_marker.position(End);
        destination_marker.title("Destino");
        destination_marker.icon(BitmapDescriptorFactory.fromBitmap(PinBitmapIcon));

        begin_marker = new MarkerOptions();
        begin_marker.position(Begin);
        begin_marker.title("Partida");
        begin_marker.icon(BitmapDescriptorFactory.fromBitmap(PinBitmapIcon));
        
        mMap.addMarker(destination_marker);
        mMap.addMarker(begin_marker);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(driver == 0 || driver == 1 || driver == 2){
                    linearLayout.setVisibility(View.GONE);
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition
                            (new CameraPosition.Builder().target(Begin)
                                    .zoom(25).build()));
                    Route();
                }else{
                    Toast.makeText(getApplicationContext(), "Escolha um motorista", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void Route() {
        Lat = (End.latitude - Begin.latitude) / ratio;
        Lng = (End.longitude - Begin.longitude) / ratio;
        LatLng Movement = new LatLng(Begin.latitude+(Lat*(cont)), Begin.longitude+(Lng*(cont)));
        prvMarker = mMap.addMarker(new MarkerOptions().position(Movement).title("Motorista").icon(BitmapDescriptorFactory.fromBitmap(carBitmapIcon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Begin));
        runnable.run();
    }

    void stop() {
        handler.removeCallbacks(runnable);
    }

    void move() {
        if (cont <= ratio) {
            if (prvMarker != null) {
                prvMarker.remove();
            }
            LatLng Movement = new LatLng(Begin.latitude+(Lat*(cont)), Begin.longitude+(Lng*(cont)));
            prvMarker = mMap.addMarker(new MarkerOptions().position(Movement).title("Motorista").icon(BitmapDescriptorFactory.fromBitmap(carBitmapIcon)));
            if (cont >= ratio) {
                prvMarker = null;
            }
            cont++;
        }else{
            stop();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            move();
            handler.postDelayed(runnable, frames);
        }
    };

}

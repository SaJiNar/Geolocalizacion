package com.example.geolocalizacin;

import android.content.pm.ActivityInfo;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_maps);
        extra = getIntent().getExtras();
        longitud = extra.getString("Longitud");
        latitud = extra.getString("Latitud");

        mapaDisponible();
    }

    private void mapaDisponible() {
        if(mapFragment == null) {
            mapFragment = (SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(Mapa.this);
        }
        if (mapFragment != null){
            Toast.makeText(this, "Mapa de Google disponible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double dLongitud = Double.parseDouble(longitud);
        double dLatitud = Double.parseDouble(latitud);

        try {
            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapa.setMyLocationEnabled(true);
            if (addresses.size() > 0) {
                addresses = addresses.get(0);
                direccionCoordenadas = addresses.getAddressLine(0)
                        + " " + addresses.getPostalCode()
                        + " " + addresses.getLocality()
                        + " " + addresses.getCountryName();
            }
            mapa.addMarker(new MarkerOptions()
                    .title(direccionCoordenadas)
                    .position(nuevaPosicion));
            CameraPosition cameraPosition = new CameraPosition.builder()
                    .target(nuevaPosicion)
                    .zoom(16.0f)
                    .tilt(45.0f)
                    .bearing(45.0f)
                    .build();
            mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
        }
    }
}

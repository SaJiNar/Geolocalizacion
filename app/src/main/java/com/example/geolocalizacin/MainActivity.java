package com.example.geolocalizacin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        tvLatitud = (TextView) findViewById(R.id.tvLatitud);
        tvLongitud = (TextView) findViewById(R.id.tvLongitud);
        tvPrecision = (TextView) findViewById(R.id.tvPrecision);
        tvAltura = (TextView) findViewById(R.id.tvAltura);
        tvPorDefecto = (TextView) findViewById(R.id.tvPorDefecto);

        btnLocalizar = (Button)findViewById(R.id.btnLocalizar);
    }

    private void rastreoGPS() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mostrarPosicion(loc);

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            @Override
            public void onStatusChanged(StringProvider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(StringProvider) {

            }

            @Override
            public void onProviderDisabled(StringProvider) {

            }

            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 0, locListener);
        };
    }

    private String[] mostrarPosicion(Location loc) {
        String[] datos;
        if (loc != null) {
            tvPorDefecto.setText("(valores GPS)");
            tvLatitud.setText(String.valueOf(loc.getLatitude()));
            tvLongitud.setText(String.valueOf(loc.getLongitude()));
            tvAltura.setText(String.valueOf(loc.getAltitude()));
            tvPrecision.setText(String.valueOf(loc.getAccuracy()));
            datos = new String[]{String.valueOf(loc.getLongitude()), String.valueOf(loc.getLatitude())};
        }
        else {
            tvPorDefecto.setText("(valores por defecto)");
            datos = new String[]{String.valueOf(40.4167754), String.valueOf(-3.7037901999999576),"Posición por defecto"};
            tvLatitud.setText(String.valueOf(40.4167754));
            tvLongitud.setText(String.valueOf(-3.7037901999999576));
            tvAltura.setText(String.valueOf(15.00));
            tvPrecision.setText(String.valueOf(1.0));
        }
        return datos;
    }

}

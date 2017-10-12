package fr.wcs.foodtruck;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private static final int PERMISSION_REQUEST_LOCALISATION = 10;
    private GoogleMap mMap;


    int num = 1;
    String rue = " place de la bourse ";
    int cp = 31000;
    String ville = " Toulouse ";
    String pays = " France";
    String la = num + rue + cp + ville + pays;
    Double mNblt;
    Double mNblg;


    private class GeocoderAsyncTask extends AsyncTask<String, Void, List<Address>> {


        protected List<Address> doInBackground(String... adresses) {
            String adresse = adresses[0];

            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.FRANCE);
            try {
               return geocoder.getFromLocationName(adresse ,1,41.954747,4.576272,51.193997,7.913997);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }




        protected void onPostExecute(List<Address> addresses) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            mNblt = addresses.get(0).getLatitude();
            mNblg = addresses.get(0).getLongitude();
            LatLng  bourse = new LatLng(mNblt,mNblg);
            mMap.addMarker(new MarkerOptions().position(bourse).title("Truck 2 FOOD | 12h00 - 14h00 | (Lundi)"));
            float zoomLevel = 16.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bourse, zoomLevel));

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GeocoderAsyncTask geocoderAsyncTask = new GeocoderAsyncTask();
        geocoderAsyncTask.execute(la);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCALISATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chekPermission();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        chekPermission();
    }

    void chekPermission() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }
}


package fr.wcs.foodtruck;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double latitude = 43.6013671;
    private Double longitude = 1.4420031;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng bourse = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(bourse).title("Truck 2 FOOD | 12h00 - 14h00 | (Lundi)").icon(BitmapDescriptorFactory.fromResource(R.drawable.markeur)));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bourse, zoomLevel));


    }




/*
    private static final int PERMISSION_REQUEST_LOCALISATION = 10;
    private GoogleMap mMap;


    static int num = 1;
    static String rue = " place de la bourse ";
    static int cp = 31000;
    static  String ville = " Toulouse ";
    static String pays = " France";
    static String la = num + rue + cp + ville + pays;
    Double mNblt;
    Double mNblg;



    private class GeocoderAsyncTask extends AsyncTask<String, Void, List<Address>> {


        protected List<Address> doInBackground(String... adresses) {
            String adresse = adresses[0];

            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
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
            mNblt = addresses.get(1).getLatitude();
            mNblg = addresses.get(1).getLongitude();
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
        //GeocoderAsyncTask geocoderAsyncTask = new GeocoderAsyncTask();
        //geocoderAsyncTask.execute(la);
        getAddressFromLocation(la,this,new GeocoderHandler());
    }

    public void methodebourain(){

        getAddressFromLocation(la,this,new GeocoderHandler());
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

    public static void getAddressFromLocation(
            final String address, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                Double lat = null;
                Double lon = null;
                try {
                    List<Address> list = geocoder.getFromLocationName(address ,1,41.954747,4.576272,51.193997,7.913997);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        // sending back first address line and locality
                        lat = address.getLatitude();
                        lon = address.getLongitude();

                    }
                } catch (IOException e) {
                    Log.e("tacos", "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (lat != null && lon !=null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putDouble("latitude", lat);
                        bundle.putDouble("longitude", lon);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Double lat = 0d;
            Double lon = 0d;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    lat = bundle.getDouble("latitude");
                    lon = bundle.getDouble("longitude");
                    LatLng  bourse = new LatLng(lat,lon);
                    mMap.addMarker(new MarkerOptions().position(bourse).title("Truck 2 FOOD | 12h00 - 14h00 | (Lundi)"));
                    float zoomLevel = 16.0f;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bourse, zoomLevel));
                    break;
                default:
                    methodebourain();

            }
            // replace by what you need to do

        }
    }  */
}


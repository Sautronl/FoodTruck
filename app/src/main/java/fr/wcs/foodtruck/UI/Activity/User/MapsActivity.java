package fr.wcs.foodtruck.UI.Activity.User;

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
import android.support.design.widget.CoordinatorLayout;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.wcs.foodtruck.*;
import fr.wcs.foodtruck.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    int mJourMarkeur;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference coordonnerRef = database.getReference("Coordonner");

    private GoogleMap mMap;
     Double mLat;
     Double mLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fr.wcs.foodtruck.R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mJourMarkeur = getIntent().getIntExtra("jourMarkeur", 0);

        if (mJourMarkeur == 3) {
            getCoord("4 Jeudi/lat","4 Jeudi/lon");
        } else if (mJourMarkeur == 0) {
            getCoord("1 Lundi/lat","1 Lundi/lon");
        } else if (mJourMarkeur == 1) {
            getCoord("2 Mardi/lat","2 Mardi/lon");
        } else if (mJourMarkeur == 2) {
            getCoord("3 Mercredi/lat","3 Mercredi/lon");
        } else if (mJourMarkeur == 4) {
            getCoord("5 Vendredi/lat","5 Vendredi/lon");
        }else if (mJourMarkeur == 5) {
            getCoord("6 Samedi/lat","6 Samedi/lon");
        }else if (mJourMarkeur == 6) {
            getCoord("7 Dimanche/lat","7 Dimanche/lon");
        }
    }

    private void getCoord(String childLat, String childLon) {
        coordonnerRef.child(childLat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double latMap = (Double) dataSnapshot.getValue(Double.class);
                mLat = latMap;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        coordonnerRef.child(childLon).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Double lonMap = (Double) dataSnapshot.getValue(Double.class);
                mLon = lonMap;
                addMark();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addMark(){
        LatLng bourse = new LatLng(mLat, mLon);
        mMap.addMarker(new MarkerOptions().position(bourse).title("SAM'donne faim ! | 12h00 - 14h00").icon(BitmapDescriptorFactory.fromResource(R.drawable.sammarkv2)));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bourse, zoomLevel));

    }
}


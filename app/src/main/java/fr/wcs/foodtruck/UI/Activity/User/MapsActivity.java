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
    //private Double latitude = 43.6013671;
    //private Double longitude = 1.4420031;
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
            coordonnerRef.child("4 Jeudi/lat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Double latJeudi = (Double) dataSnapshot.getValue(Double.class);
                    mLat = latJeudi;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            coordonnerRef.child("4 Jeudi/lon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Double lonJeudi = (Double) dataSnapshot.getValue(Double.class);
                    mLon = lonJeudi;
                    addMark();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } else if (mJourMarkeur == 0) {
            coordonnerRef.child("1 Lundi/lat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Double latLundi = (Double) dataSnapshot.getValue(Double.class);
                    mLat = latLundi;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            coordonnerRef.child("1 Lundi/lon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Double lonLundi = (Double) dataSnapshot.getValue(Double.class);
                    mLon = lonLundi;
                    addMark();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else if (mJourMarkeur == 1) {
            coordonnerRef.child("2 Mardi/lat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Double latMardi = (Double) dataSnapshot.getValue(Double.class);
                    mLat = latMardi;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            coordonnerRef.child("2 Mardi/lon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Double lonMardi = (Double) dataSnapshot.getValue(Double.class);
                    mLon = lonMardi;
                    addMark();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else if (mJourMarkeur == 2) {
            coordonnerRef.child("3 Mercredi/lat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Double latMercredi = (Double) dataSnapshot.getValue(Double.class);
                    mLat = latMercredi;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            coordonnerRef.child("3 Mercredi/lon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Double lonMercredi = (Double) dataSnapshot.getValue(Double.class);
                    mLon = lonMercredi;
                    addMark();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else if (mJourMarkeur == 4) {
            coordonnerRef.child("5 Vendredi/lat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Double latVendredi = (Double) dataSnapshot.getValue(Double.class);
                    mLat = latVendredi;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            coordonnerRef.child("5 Vendredi/lon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Double lonVendredi = (Double) dataSnapshot.getValue(Double.class);
                    mLon = lonVendredi;
                    addMark();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else if (mJourMarkeur == 5) {
            coordonnerRef.child("6 Samedi/lat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Double latSamedi = (Double) dataSnapshot.getValue(Double.class);
                    mLat = latSamedi;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            coordonnerRef.child("6 Samedi/lon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Double lonSamedi = (Double) dataSnapshot.getValue(Double.class);
                    mLon = lonSamedi;
                    addMark();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else if (mJourMarkeur == 6) {
            coordonnerRef.child("7 Dimanche/lat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Double latDimanche = (Double) dataSnapshot.getValue(Double.class);
                    mLat = latDimanche;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            coordonnerRef.child("7 Dimanche/lon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Double lonDimanche = (Double) dataSnapshot.getValue(Double.class);
                    mLon = lonDimanche;
                    addMark();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void addMark(){
        LatLng bourse = new LatLng(mLat, mLon);
        mMap.addMarker(new MarkerOptions().position(bourse).title("SAM'donne faim ! | 12h00 - 14h00").icon(BitmapDescriptorFactory.fromResource(R.drawable.sammarkv2)));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bourse, zoomLevel));

    }
}


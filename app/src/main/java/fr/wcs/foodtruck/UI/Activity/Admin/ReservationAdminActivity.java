package fr.wcs.foodtruck.UI.Activity.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Model.ReservationModels;
import fr.wcs.foodtruck.UI.Adapter.AdapterReservAdmin;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

public class ReservationAdminActivity extends AppCompatActivity {

    private RecyclerView mListReserve;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private AdapterReservAdmin mAdapRes;
     ReservationModels mReservMdodel;
    private List<ReservationModels> mReserve = new ArrayList<>();
    //private int selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_admin);

        RelativeLayout reservAdminLayout = (RelativeLayout) findViewById(R.id.reservAdminLayout);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(reservAdminLayout,mainfont);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ReservationAdminActivity.this, ChoixReserveContactAdmin.class);
                startActivity(back);
            }
        });

        initFirebase();
        childFirebaseListener();

        final Button remove = (Button)findViewById(R.id.removeReservationAll);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapRes == null) {
                    Toast.makeText(ReservationAdminActivity.this, getResources().getString(R.string.suppListeAdmin), Toast.LENGTH_SHORT).show();
                } else {
                    mDatabaseReference.child("Réservation").removeValue();
                    mListReserve.setAdapter(null);
                    mAdapRes.notifyDataSetChanged();
                }
            }
        });

        mListReserve = (RecyclerView) findViewById(R.id.listeres);
        mListReserve.setLayoutManager(new LinearLayoutManager(ReservationAdminActivity.this));

        mAdapRes = new AdapterReservAdmin(ReservationAdminActivity.this,mReserve);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void childFirebaseListener() {
        mDatabaseReference.child("Réservation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mReserve.size()>0){
                    mReserve.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                     mReservMdodel = snap.getValue(ReservationModels.class);
                    mReserve.add(mReservMdodel);
                }
                mAdapRes = new AdapterReservAdmin(ReservationAdminActivity.this, mReserve);
                mListReserve.setAdapter(mAdapRes);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
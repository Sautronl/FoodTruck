package fr.wcs.foodtruck;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private ListView list_data;
    private ProgressBar circular_progress;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ListEventAdapter mAdapter;

    private int mCurrentPosition;

    private List<EventModel> list_events = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(EventActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        //Control
        circular_progress = (ProgressBar)findViewById(R.id.circular_progress);

        //Liste d'events
        list_data = (ListView)findViewById(R.id.list_data);
        /*list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EventModel event = (EventModel)adapterView.getItemAtPosition(position);
                mCurrentPosition = position;
                for (int i = 0; i < list_data.getChildCount(); i++) {
                    if(position == i ){
                        list_data.getChildAt(i).setBackgroundColor(Color.BLACK);
                    }else{
                        list_data.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });*/
        //Firebase
        initFirebase();
        addEventFirebaseListener();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("events");
    }

    private void addEventFirebaseListener() {

        //Progressing
        circular_progress.setVisibility(View.VISIBLE);
        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_events.size() > 0)
                    list_events.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventModel event = postSnapshot.getValue(EventModel.class);
                    list_events.add(event);
                }
                Collections.reverse(list_events);
                mAdapter = new ListEventAdapter(EventActivity.this, list_events);
                list_data.setAdapter(mAdapter);
                circular_progress.setVisibility(View.INVISIBLE);
                list_data.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

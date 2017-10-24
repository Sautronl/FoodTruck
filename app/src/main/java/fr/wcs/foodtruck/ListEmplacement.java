package fr.wcs.foodtruck;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apprenti on 15/09/17.
 */

public class ListEmplacement extends AppCompatActivity {



    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference coordonnerRef = database.getReference("Coordonner");

    private ListView mListViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emplacement);

        mListViewResults = (ListView) findViewById(R.id.listEmplacement);

        mListViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListEmplacement.this, MapsActivity.class);
                intent.putExtra("jourMarkeur", i);
                startActivity(intent);
            }
        });
        addAdrs();
    }

   private void addAdrs(){
        final List<ListJourEmplacementModel> results = new ArrayList<>();

        coordonnerRef.orderByKey().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot daySnapshot : dataSnapshot.getChildren()) {
                    String adresse = (String) daySnapshot.child("adrs").getValue();
                    results.add(new ListJourEmplacementModel(daySnapshot.getKey(), adresse));
                }
                AdapterListEmplacement adapter = new AdapterListEmplacement(ListEmplacement.this, results);
                mListViewResults.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

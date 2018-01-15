package fr.wcs.foodtruck.UI.Activity.User;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.foodtruck.Model.ListJourEmplacementModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Adapter.AdapterListEmplacement;

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

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ListEmplacement.this, MainActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

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

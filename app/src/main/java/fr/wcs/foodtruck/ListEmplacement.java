package fr.wcs.foodtruck;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

        /*mListViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListEmplacement.this, MapsActivity.class);
                intent.putExtra("jour", i);
                startActivity(intent);


            }
        });*/

    }

   /* private List<ListJourEmplacementModel> genererList(){
        List<ListJourEmplacementModel> results = new ArrayList<>();
        results.add(new ListJourEmplacementModel("Lundi", "1 place de la bourse 31000 Toulouse"));
        results.add(new ListJourEmplacementModel("Mardi", "1 place de la bourse 31000 Toulouse"));
        results.add(new ListJourEmplacementModel("Mercredi", "1 place de la bourse 31000 Toulouse"));
        results.add(new ListJourEmplacementModel("Jeudi", "1 place de la bourse 31000 Toulouse"));
        results.add(new ListJourEmplacementModel("Vendredi", "1 place de la bourse 31000 Toulouse"));
        return results;
    }*/

    /*private void afficherLists(){
        List<ListJourEmplacementModel> jours = genererList();

        AdapterListEmplacement adapter = new AdapterListEmplacement(ListEmplacement.this, jours);
        mListViewResults.setAdapter(adapter);
    }*/

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



 /*   private void addEmplacement() {
        coordonnerRef.child("Lundi").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListJourEmplacementModel adresse = dataSnapshot.getValue(ListJourEmplacementModel.class);
                mResults.add(adresse);
                AdapterListEmplacement adapEmplacement = new AdapterListEmplacement(ListEmplacement.this, mResults);
                mListViewResults.setAdapter(adapEmplacement);
                //genererList();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}

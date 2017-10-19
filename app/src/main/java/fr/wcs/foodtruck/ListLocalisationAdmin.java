package fr.wcs.foodtruck;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apprenti on 15/09/17.
 */

public class ListLocalisationAdmin extends AppCompatActivity {


    private ListView mListViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_localisation);

        mListViewResults = (ListView) findViewById(R.id.listLocalisation);


        mListViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListLocalisationAdmin.this, GeocoderActivity.class);
                intent.putExtra("jour", i);
                startActivity(intent);
            }
        });
        afficherLists();
    }

    private List<ListLocalisationModel> genererList(){
        List<ListLocalisationModel> results = new ArrayList<>();
        results.add(new ListLocalisationModel("Lundi"));
        results.add(new ListLocalisationModel("Mardi"));
        results.add(new ListLocalisationModel("Mercredi"));
        results.add(new ListLocalisationModel("Jeudi"));
        results.add(new ListLocalisationModel("Vendredi"));
        return results;
    }

    private void afficherLists(){
        List<ListLocalisationModel> jours = genererList();

        AdapterListLocalisation adapter = new AdapterListLocalisation(ListLocalisationAdmin.this, jours);
        mListViewResults.setAdapter(adapter);
    }



    }



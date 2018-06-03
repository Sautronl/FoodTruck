package fr.wcs.foodtruck;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
        setContentView(R.layout.activity_admin_list_emplacement);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ListLocalisationAdmin.this, AdminAccueil.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

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



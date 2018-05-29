package fr.wcs.foodtruck.UI.Activity.Admin;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import fr.wcs.foodtruck.Model.ListeJourModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Adapter.AdapterListeJour;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

public class AdminListeMenuDuJour extends AppCompatActivity {

    private ListView mListeJour;
    private List<ListeJourModel> mLtJour;
    private Button boissonAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_menu_du_jour);

        ConstraintLayout layoutMenuJourAdmin = (ConstraintLayout) findViewById(R.id.layoutMenuJourAdmin);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(layoutMenuJourAdmin,mainfont);

        boissonAdmin = (Button)findViewById(R.id.boissonAdmin);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        boissonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(AdminListeMenuDuJour.this, StuffActivity.class));
            }
        });

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(AdminListeMenuDuJour.this, AdminAccueil.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        mListeJour = (ListView) findViewById(R.id.liste);
        mListeJour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View convertView, int i, long l) {
                Intent intent = new Intent(AdminListeMenuDuJour.this, AdminMenuDuJour.class);
                intent.putExtra("day", i);
                startActivity(intent);
            }
        });
        afficherListeTweets();
    }

    private List<ListeJourModel> afficherListeTweets(){
        mLtJour = new ArrayList<>();
        mLtJour.add(new ListeJourModel("Lundi"));
        mLtJour.add(new ListeJourModel("Mardi"));
        mLtJour.add(new ListeJourModel("Mercredi"));
        mLtJour.add(new ListeJourModel("Jeudi"));
        mLtJour.add(new ListeJourModel("Vendredi"));

        AdapterListeJour adapter = new AdapterListeJour(AdminListeMenuDuJour.this, mLtJour);
        mListeJour.setAdapter(adapter);

        return mLtJour;
    }
}

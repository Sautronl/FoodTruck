package fr.wcs.foodtruck.UI.Activity.User;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Model.RemerciementModel;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

public class RemerciementCommande extends AppCompatActivity {

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remerciement_commande);

        RelativeLayout merciLayout =(RelativeLayout) findViewById(R.id.merciLayout);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(merciLayout,mainfont);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(RemerciementCommande.this, MainActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        final TextView tvRemerciementN = (TextView) findViewById(R.id.tvRemerciementNom);
        final TextView tvHeure = (TextView) findViewById(R.id.remerciementHeure);

        String nom = getIntent().getStringExtra("nom");
        String heure = getIntent().getStringExtra("heure");

        RemerciementModel rem = new RemerciementModel(nom,heure);
        tvRemerciementN.setText(rem.getNom());
        tvHeure.setText(rem.getHeure());
    }
}

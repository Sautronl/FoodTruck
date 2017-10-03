package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;

public class RemerciementCommande extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remerciement_commande);
        final Button btHome = (Button) findViewById(R.id.btHomeRemerciement);
        final TextView tvRemerciementN = (TextView) findViewById(R.id.tvRemerciementNom);




        String nom = getIntent().getStringExtra("nom");



        RemerciementModel rem = new RemerciementModel(nom);
        tvRemerciementN.setText(rem.getNom());



        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemerciementCommande.this, MenuDuJourActivity.class);
                startActivity(intent);
            }
        });

    }


}

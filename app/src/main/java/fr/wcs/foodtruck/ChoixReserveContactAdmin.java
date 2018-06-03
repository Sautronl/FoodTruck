package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChoixReserveContactAdmin extends AppCompatActivity {

    private TextView choixR;
    private TextView choixC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_reserve_contact_admin);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ChoixReserveContactAdmin.this, AdminAccueil.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        choixC = (TextView) findViewById(R.id.choixcontact);
        choixR = (TextView) findViewById(R.id.choixreservation);

        choixC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contact = new Intent(ChoixReserveContactAdmin.this, ContactAdmin.class);
                startActivity(contact);
            }
        });

        choixR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reserv = new Intent(ChoixReserveContactAdmin.this, ReservationAdminActivity.class);
                startActivity(reserv);
            }
        });
    }
}

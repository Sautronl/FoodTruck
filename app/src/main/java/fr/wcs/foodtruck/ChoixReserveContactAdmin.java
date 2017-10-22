package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChoixReserveContactAdmin extends AppCompatActivity {

    private TextView choixR;
    private TextView choixC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_reserve_contact_admin);

        choixC = (TextView) findViewById(R.id.choixcontact);
        choixR = (TextView) findViewById(R.id.choixreservation);

        choixC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contact = new Intent(ChoixReserveContactAdmin.this, ContactAdmin.class);
                startActivity(contact);
            }
        });

        /*choixR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reserv = new Intent(ChoixReserveContactAdmin.this, ReservationAdmin.class);
            }
        });*/
    }
}

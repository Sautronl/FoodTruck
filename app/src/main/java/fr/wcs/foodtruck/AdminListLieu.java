package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminListLieu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_lieu);

        TextView tvLundi = (TextView) findViewById(R.id.lundi_admin_list_lieu);
        TextView tvMardi = (TextView) findViewById(R.id.mardi_admin_list_lieu);
        TextView tvMercredi = (TextView) findViewById(R.id.mercredi_admin_list_lieu);
        TextView tvJeudi = (TextView) findViewById(R.id.jeudi_admin_list_lieu);
        TextView tvVendredi = (TextView) findViewById(R.id.vendredi_admin_list_lieu);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent back = new Intent(AdminListLieu.this, MainActivity.class);
                startActivity(back);

            }
        });


        tvLundi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminListLieu.this, GeocoderActivity.class);
                startActivity(intent);




            }
        });
        tvMardi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminListLieu.this, AdminAccueil.class);
                startActivity(intent);
            }
        });
        tvMercredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminListLieu.this, AdminAccueil.class);
                startActivity(intent);
            }
        });
        tvJeudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminListLieu.this, AdminAccueil.class);
                startActivity(intent);
            }
        });
        tvVendredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminListLieu.this, AdminAccueil.class);
                startActivity(intent);
            }
        });




    }
}
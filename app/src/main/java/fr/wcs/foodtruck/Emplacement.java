package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class Emplacement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplacement);


        TextView textViewLundi = (TextView)findViewById(R.id.adresseLundi);
        SpannableString content1 = new SpannableString("1 Place de la Bourse 31000 Toulouse ");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        textViewLundi.setText(content1);

        TextView textViewMardi = (TextView)findViewById(R.id.adresseMardi);
        SpannableString content2 = new SpannableString("1 Place de la Bourse 31000 Toulouse ");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        textViewMardi.setText(content2);

        TextView textViewMercredi = (TextView)findViewById(R.id.adresseMercredi);
        SpannableString content3 = new SpannableString("1 Place de la Bourse 31000 Toulouse ");
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        textViewMercredi.setText(content3);

        TextView textViewJeudi = (TextView)findViewById(R.id.adresseJeudi);
        SpannableString content4 = new SpannableString("1 Place de la Bourse 31000 Toulouse ");
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        textViewJeudi.setText(content4);

        TextView textViewVendredi = (TextView)findViewById(R.id.adresseVendredi);
        SpannableString content5 = new SpannableString("1 Place de la Bourse 31000 Toulouse ");
        content5.setSpan(new UnderlineSpan(), 0, content5.length(), 0);
        textViewVendredi.setText(content5);

        textViewLundi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLundi = new Intent(Emplacement.this,MapsActivity.class);
                startActivity(intentLundi);
            }
        });

        textViewMardi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMardi = new Intent(Emplacement.this,MapsActivity.class);
                startActivity(intentMardi);
            }
        });

        textViewMercredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMercredi = new Intent(Emplacement.this,MapsActivity.class);
                startActivity(intentMercredi);
            }
        });

        textViewJeudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentJeudi = new Intent(Emplacement.this,MapsActivity.class);
                startActivity(intentJeudi);
            }
        });

        textViewVendredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVendredi = new Intent(Emplacement.this,MapsActivity.class);
                startActivity(intentVendredi);
            }
        });
    }
}

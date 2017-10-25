package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import static android.view.View.VISIBLE;

public class Presentation extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);


        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Presentation.this, MainActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        final GridView gridView = (GridView) findViewById(R.id.gallery);
        gridView.setAdapter(new GalleryAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), fullImageActivity.class);
                i.putExtra("id", position);
                startActivity(i);

            }
        });

        Button button1 = (Button) findViewById(R.id.buttonPresentation1);
        Button button2 = (Button) findViewById(R.id.buttonPresentation2);
        Button button3 = (Button) findViewById(R.id.buttonPresentation3);
        final TextView textView1 = (TextView) findViewById(R.id.textViewPresentation1);
        final TextView textView2 = (TextView) findViewById(R.id.textViewPresentation2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textView1.getVisibility() == View.VISIBLE) {
                    textView1.setVisibility(View.GONE);
                } else {
                    textView1.setVisibility(VISIBLE);
                    textView2.setVisibility(View.GONE);
                    gridView.setVisibility(View.GONE);

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView2.getVisibility() == View.VISIBLE) {
                    textView2.setVisibility(View.GONE);
                } else {
                    textView2.setVisibility(VISIBLE);
                    textView1.setVisibility(View.GONE);
                    gridView.setVisibility(View.GONE);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                } else {
                    gridView.setVisibility(VISIBLE);
                    textView2.setVisibility(View.GONE);
                    textView1.setVisibility(View.GONE);
                }
            }
        });
    }
}

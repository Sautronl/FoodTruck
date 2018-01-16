package fr.wcs.foodtruck.UI.Activity.User;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FormuleActivity extends AppCompatActivity {

    PhotoViewAttacher pAttach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formule);

        ScrollView formuleScroll = (ScrollView) findViewById(R.id.formuleScroll);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(formuleScroll,mainfont);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(FormuleActivity.this, DrawActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        Button reserver = (Button) findViewById(R.id.reserver);
        ImageView samCarte =(ImageView) findViewById(R.id.samCarte);

        pAttach = new PhotoViewAttacher(samCarte);
        pAttach.update();

        final Intent intent = new Intent(FormuleActivity.this, Commande.class);
        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}

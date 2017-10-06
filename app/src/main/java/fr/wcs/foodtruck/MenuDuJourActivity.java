package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuDuJourActivity extends AppCompatActivity {

    /*private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mChildReference = mRootReference.child("option1");
    private  TextView mNomDuPlat;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_du_jour);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(MenuDuJourActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        TextView adress = (TextView)findViewById(R.id.adress);
        SpannableString adressSS = new SpannableString("1 Place de la Bourse 31000 Toulouse");
        adressSS.setSpan(new UnderlineSpan(), 0, adressSS.length(), 0);
        adress.setText(adressSS);

        TextView voirFormules = (TextView)findViewById(R.id.totheformules);
        SpannableString formuleSS = new SpannableString("Découvrez nos formules >");
        formuleSS.setSpan(new UnderlineSpan(), 0, formuleSS.length(), 0);
        voirFormules.setText(formuleSS);

        Button reserver = (Button) findViewById(R.id.reserver);
        TextView decouvrez = (TextView) findViewById(R.id.totheformules);
       /* mNomDuPlat = (TextView) findViewById(R.id.nomDuPlat);*/



        final Intent intent = new Intent(MenuDuJourActivity.this, Commande.class);
        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        final Intent intent2 = new Intent(MenuDuJourActivity.this, FormuleActivity.class);
        decouvrez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

    }

    /*
    @Override
    protected void onStart(){
        super.onStart();
        mChildReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String optionMenu = dataSnapshot.getValue(String.class);
                mNomDuPlat.setText(optionMenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}

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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MenuDuJourActivity extends AppCompatActivity {

    private FirebaseDatabase mFire;
    private DatabaseReference mDbRef;
    private DatabaseReference mDbRefCoor;
    private TextView mNomBurger;
    private TextView mDescriptionMenu;
    private TextView mAdress;
    private Calendar myCalendar;
    private ImageView mImgplatMenu;


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

        mFire = FirebaseDatabase.getInstance();
        mDbRef = mFire.getReference("menu");
        mDbRefCoor = mFire.getReference();

        mNomBurger = (TextView) findViewById(R.id.burger);
        mDescriptionMenu = (TextView) findViewById(R.id.descriPlat);
        mAdress = (TextView)findViewById(R.id.adress);
        mImgplatMenu = (ImageView) findViewById(R.id.imgDuPlatMenu);

        TextView voirFormules = (TextView)findViewById(R.id.totheformules);
        SpannableString formuleSS = new SpannableString("Découvrez nos formules >");
        formuleSS.setSpan(new UnderlineSpan(), 0, formuleSS.length(), 0);
        voirFormules.setText(formuleSS);

        Button reserver = (Button) findViewById(R.id.reserver);
        TextView decouvrez = (TextView) findViewById(R.id.totheformules);

        checkDay();

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

    protected void checkDay() {

        myCalendar = Calendar.getInstance();
        int dayD = myCalendar.get(Calendar.DAY_OF_WEEK);

        if (dayD == 2) {
            majMenu("menuLundi");
            if (mDbRefCoor != mDbRef) {
                mDbRefCoor = mDbRefCoor.child("Coordonner/Lundi");
                majEmplacement();
            }
        }else if (dayD == 3) {
            majMenu("menuMardi");
            if (mDbRefCoor != mDbRef) {
                mDbRefCoor = mDbRefCoor.child("Coordonner/Mardi");
                majEmplacement();
            }
        }else if (dayD == 4) {
            majMenu("menuMercredi");
            if (mDbRefCoor != mDbRef) {
                mDbRefCoor = mDbRefCoor.child("Coordonner/Mercredi");
                majEmplacement();
            }
        }else if (dayD == 5) {
            majMenu("menuJeudi");
            if (mDbRefCoor != mDbRef) {
                mDbRefCoor = mDbRefCoor.child("Coordonner/Jeudi");
                majEmplacement();
            }
        }else if (dayD == 6) {
            majMenu("menuVendredi");
            if (mDbRefCoor != mDbRef) {
                mDbRefCoor = mDbRefCoor.child("Coordonner/Vendredi");
                majEmplacement();
            }
        }
        else{
            Intent intentClose = new Intent(MenuDuJourActivity.this, CloseDay.class);
            startActivity(intentClose);
        }
    }

    protected void majEmplacement(){
        mDbRefCoor.child("adrs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ad = dataSnapshot.getValue(String.class);
                mAdress.setText(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void majMenu(String jour){
        mDbRef.child(jour).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    MajPlatDuJour maj = dataSnapshot.getValue(MajPlatDuJour.class);
                    mNomBurger.setText(maj.getNomPlat());
                    mDescriptionMenu.setText(maj.getDescPlat());
                    Glide.with(MenuDuJourActivity.this).load(maj.getUrlImg()).into(mImgplatMenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

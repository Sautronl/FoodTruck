package fr.wcs.foodtruck.AdminMenuDuJour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.foodtruck.AdminMenuDuJour.Data.MajPlatDuJour;
import fr.wcs.foodtruck.R;

public class AdminMenuDuJourLundi extends AppCompatActivity {

    public static String DB_URL="https://foodtruck-ebd2c.firebaseio.com/";


    EditText nomPlatDuJour;
    EditText descriptionDuPlat;
    EditText prix;
    Button maj;
    private FirebaseDatabase mFire;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_du_jour_lundi);

 // initialisation firebase

       // initializeFirebase();

        nomPlatDuJour = (EditText)findViewById(R.id.nomDuPlat);
        descriptionDuPlat =(EditText)findViewById(R.id.descriPlat);
        prix = (EditText)findViewById(R.id.prixHint);
        maj =(Button)findViewById(R.id.maj);

        mFire = FirebaseDatabase.getInstance();
        mDbRef = mFire.getReference("MajPlatDuJourLundi");
        // mFire = new Firebase (DB_URL);

        maj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MajPlatDuJour majLundi = new MajPlatDuJour();
                majLundi.setNomPlatDuJour(nomPlatDuJour.getText().toString());
                majLundi.setDescriptionDuPlat(descriptionDuPlat.getText().toString());
                //majLundi.setPrix(maj.getText().toString());

                mDbRef.setValue(majLundi);

                nomPlatDuJour.setText("");
                descriptionDuPlat.setText("");

            }
        });

        /*mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MajPlatDuJour nomDuPlat = dataSnapshot.getValue(MajPlatDuJour.class);
                String plat = dataSnapshot.getValue(String.class);
                String desc = dataSnapshot.getValue(String.class);
                descriptionDuPlat.setText(desc);
                nomPlatDuJour.setText(plat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

    }

   // private void initializeFirebase(){
    //    .setAndroidContext(this);
    //}
}

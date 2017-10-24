package fr.wcs.foodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by apprenti on 27/09/17.
 */

public class Commande  extends AppCompatActivity {

    Button btReserverCommande;
    EditText txtNomCommande;
    EditText txtTelCommande;

    private DatabaseReference mReservRef;
    private FirebaseDatabase mFirebase;

    private String TITLE = "nom";
    private String CONTENT = "tel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        mFirebase = FirebaseDatabase.getInstance();
        mReservRef = mFirebase.getReference("Réservation");

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Commande.this, MenuDuJourActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        btReserverCommande = (Button) findViewById(R.id.buttonReserver);
        txtNomCommande = (EditText) findViewById(R.id.editTextNom);
        txtTelCommande = (EditText) findViewById(R.id.editTextTel);
        final ImageView warningNom = (ImageView) findViewById(R.id.warningNom);
        final ImageView warningTel = (ImageView) findViewById(R.id.warningTel);
        final TextView votreNom = (TextView) findViewById(R.id.votreNom);
        final TextView votreTel = (TextView) findViewById(R.id.votreTel);
        final Spinner spinnerCommande = (Spinner) findViewById(R.id.spinnerCommande);

        // On crée l'adapter pour le spinner.
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.model_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCommande.setAdapter(adapter);

        spinnerCommande.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Si aucune horaires n'est selectionner on affiche un toast

                if (i == 0) {
                    btReserverCommande.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), getResources().getString
                                    (R.string.toast_spinner_vide), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //Si l'horaire est selectionner alors on effectuer les commandes suivante

                else {
                    btReserverCommande.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String telCommande = txtTelCommande.getText().toString();
                            String nomCommande = txtNomCommande.getText().toString();

                            //Si les champs nom et telephone sont vide alors on affiche un toast et
                            // le texte s'affiche en rouge avec un logo.

                            if (telCommande.equals("") && (nomCommande.equals(""))) {

                                Toast.makeText(getApplicationContext(), getResources().getString
                                        (R.string.toast_champ_vide), Toast.LENGTH_SHORT).show();
                                warningNom.setVisibility(View.VISIBLE);
                                warningTel.setVisibility(View.VISIBLE);
                                votreNom.setTextColor(getResources().getColor(R.color.rougeDark));
                                votreTel.setTextColor(getResources().getColor(R.color.rougeDark));


                            }

                            //Si le champ telephone est vide alors on affiche un toast.

                            else {
                                if (telCommande.equals("") && !nomCommande.equals("")) {

                                    Toast.makeText(getApplicationContext(), getResources().getString
                                            (R.string.toast_champ_vide_tel), Toast.LENGTH_SHORT).show();
                                    warningTel.setVisibility(View.VISIBLE);
                                    warningNom.setVisibility(View.GONE);
                                    votreTel.setTextColor(getResources().getColor(R.color.rougeDark));
                                    votreNom.setTextColor(getResources().getColor(R.color.blanc));
                                }

                                //Si le champ nom est vide alors on affiche un toastet
                                // le texte s'affiche en rouge avec un logo.

                                else if (nomCommande.equals("") && !telCommande.equals("")) {
                                    Toast.makeText(getApplicationContext(), getResources().getString
                                            (R.string.toast_champ_vide_nom), Toast.LENGTH_SHORT).show();
                                    warningNom.setVisibility(View.VISIBLE);
                                    warningTel.setVisibility(View.GONE);
                                    votreNom.setTextColor(getResources().getColor(R.color.rougeDark));
                                    votreTel.setTextColor(getResources().getColor(R.color.blanc));
                                }

                                //Si tout est remplie on va sur la page Remerciement Commande en
                                // expliquant que la commande a était prise en compte.

                                else {
                                    warningTel.setVisibility(View.GONE);
                                    warningNom.setVisibility(View.GONE);
                                    votreNom.setTextColor(getResources().getColor(R.color.blanc));
                                    votreTel.setTextColor(getResources().getColor(R.color.blanc));

                                    //on recuperer le nom et l'heure pour l'envoyer sur la page
                                    // de remerciement.

                                    btReserverCommande.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            createNotifications();

                                            /*ReservationModels reservation = new ReservationModels();
                                            reservation.setNomReserv(txtNomCommande.getText().toString());
                                            reservation.setNumTelReserv(txtTelCommande.getText().toString());

                                            mReservRef.push().setValue(reservation);*/

                                            createRes();
                                            Intent intent = new Intent(Commande.this, RemerciementCommande.class);
                                            intent.putExtra("heure","Elle sera prête pour " + spinnerCommande.getItemAtPosition
                                                    (spinnerCommande.getSelectedItemPosition()).toString());
                                            intent.putExtra("nom", "Merci "+ txtNomCommande.getText().toString());

                                            Commande.this.startActivity(intent);
                                            //Le finish permet de ne par revenir sur la page
                                            // Commande dès que l'on a deja commmander.
                                            finish();
                                        }
                                    });

                                }

                            }

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //addValue();
    }

    protected void createRes(){
        String nomRes = txtNomCommande.getText().toString();
        String telRes = txtTelCommande.getText().toString();
        ReservationModels res = new ReservationModels(nomRes, telRes);
        //res.setNomReserv(nomRes);
       // res.setNumTelReserv(telRes);
        String idRes = mReservRef.push().getKey();
        mReservRef.child(idRes).setValue(res);
    }
/*
    protected void addValue(){
        mReservRef.child("Nom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String snapNom = dataSnapshot.getValue(String.class);
                txtNomCommande.setText(snapNom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mReservRef.child("Num téléphone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String snapTel = dataSnapshot.getValue(String.class);
                txtTelCommande.setText(snapTel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    private void createNotifications(){

        // Get the Database
        FirebaseDatabase database = FirebaseHelper.getDatabase();
        // Get the Notification Reference
        final DatabaseReference notificationContactRef = database.getReference("notificationCommande");
        // Keep the Database sync in case of loosing connexion
        notificationContactRef.keepSynced(true);

        // Send Notification on Send Click
//        buttonSend.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        // Get Edit Text
        EditText editTextNomPrenom = (EditText) findViewById(R.id.editTextNom);
        EditText editTextTel = (EditText) findViewById(R.id.editTextTel);
        // Spinner editTextSbText = (Spinner) findViewById(R.id.spinnerCommande);

        // Get Text
        String nom = editTextNomPrenom.getText().toString();
        String tel = editTextTel.getText().toString();
        //String horaire = editTextSubText.getText().toString();
        // Store in a map
        HashMap<String, String> notification = new HashMap<String, String>();
        notification.put(TITLE, nom);
        notification.put(CONTENT, tel);
       // notification.put(SUBTEXT, sujet);
        // Send the map
        notificationContactRef.setValue(notification);
    }

}

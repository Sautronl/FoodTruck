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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

/**
 * Created by apprenti on 27/09/17.
 */

public class Commande  extends AppCompatActivity {

    Button btReserverCommande;
    EditText txtNomCommande;
    EditText txtTelCommande;
    Spinner spinnerCommande;

    private DatabaseReference mReservRef;
    private FirebaseDatabase mFirebase;


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
        spinnerCommande = (Spinner) findViewById(R.id.spinnerCommande);

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
                    txtNomCommande.setEnabled(false);
                    txtTelCommande.setEnabled(false);
                }else
                    {
                        txtNomCommande.setEnabled(true);
                        txtTelCommande.setEnabled(true);
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btReserverCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtNomCommande.getText().toString().isEmpty() || txtTelCommande.getText().toString().isEmpty()) {
                    Toast.makeText(Commande.this, getResources().getString(R.string.messToast), Toast.LENGTH_SHORT).show();
                } else {
                    createRes();
                    Intent intent = new Intent(Commande.this, RemerciementCommande.class);
                    intent.putExtra("heure", "Elle sera prête pour " + spinnerCommande.getItemAtPosition
                            (spinnerCommande.getSelectedItemPosition()).toString());
                    intent.putExtra("nom", "Merci " + txtNomCommande.getText().toString());

                    Commande.this.startActivity(intent);
                    //Le finish permet de ne par revenir sur la page
                    // Commande dès que l'on a deja commmander.
                    finish();
                }
            }
        });
    }

    protected void createRes() {
        String nomRes = txtNomCommande.getText().toString();
        String telRes = txtTelCommande.getText().toString();
        String horaireRes = spinnerCommande.getItemAtPosition(spinnerCommande.getSelectedItemPosition()).toString();
        ReservationModels res = new ReservationModels(UUID.randomUUID().toString(), nomRes, telRes, horaireRes);
        mReservRef.child(res.getId()).setValue(res);
    }
}

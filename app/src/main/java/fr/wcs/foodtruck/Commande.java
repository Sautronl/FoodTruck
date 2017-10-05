package fr.wcs.foodtruck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by apprenti on 27/09/17.
 */

public class Commande  extends AppCompatActivity {

    Button btReserverCommande;
    EditText txtNomCommande;
    EditText txtTelCommande;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);


        btReserverCommande = (Button) findViewById(R.id.buttonReserver);
        txtNomCommande = (EditText) findViewById(R.id.editTextNom);
        txtTelCommande = (EditText) findViewById(R.id.editTextTel);
        Button backCommande = (Button) findViewById(R.id.buttonBack);
        final ImageView warningNom = (ImageView) findViewById(R.id.warningNom);
        final ImageView warningTel = (ImageView) findViewById(R.id.warningTel);
        final TextView votreNom = (TextView) findViewById(R.id.votreNom);
        final TextView votreTel = (TextView) findViewById(R.id.votreTel);
        final Spinner spinnerCommande = (Spinner) findViewById(R.id.spinnerCommande);


                String telCommande = txtTelCommande.getText().toString();
                String nomCommande = txtNomCommande.getText().toString();

                //Si les champs nom et telephone sont vide alors on affiche un toast.


                if (telCommande.equals("") && (nomCommande.equals(""))) {

                    Toast.makeText(getApplicationContext(), getResources().getString
                            (R.string.toast_champ_vide), Toast.LENGTH_SHORT).show();
                    warningNom.setVisibility(View.VISIBLE);
                    warningTel.setVisibility(View.VISIBLE);
                    votreNom.setTextColor(getResources().getColor(R.color.rougeDark));
                    votreTel.setTextColor(getResources().getColor(R.color.rougeDark));

                }
                //Si le champ telephone est vide alors on affiche un toast.
                else if (telCommande.equals("") && !nomCommande.equals("")) {

                    Toast.makeText(getApplicationContext(), getResources().getString
                            (R.string.toast_champ_vide_tel), Toast.LENGTH_SHORT).show();
                    warningTel.setVisibility(View.VISIBLE);
                    warningNom.setVisibility(View.GONE);
                    votreTel.setTextColor(getResources().getColor(R.color.rougeDark));
                    votreNom.setTextColor(getResources().getColor(R.color.blanc));


                }
                //Si le champ nom est vide alors on affiche un toast.
                else if (nomCommande.equals("") && !telCommande.equals("")) {

                    Toast.makeText(getApplicationContext(), getResources().getString
                            (R.string.toast_champ_vide_nom), Toast.LENGTH_SHORT).show();
                    warningNom.setVisibility(View.VISIBLE);
                    warningTel.setVisibility(View.GONE);
                    votreNom.setTextColor(getResources().getColor(R.color.rougeDark));
                    votreTel.setTextColor(getResources().getColor(R.color.blanc));

                }
                //Sinon on affiche un toast expliquant que la commande a était prise en compt.
                else {
                    btReserverCommande.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Commande.this, RemerciementCommande.class);
                            startActivity(intent);
                        }
                    });
                    warningTel.setVisibility(View.GONE);
                    warningNom.setVisibility(View.GONE);
                    votreNom.setTextColor(getResources().getColor(R.color.blanc));
                    votreTel.setTextColor(getResources().getColor(R.color.blanc));

                }


        backCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Commande.this, MenuDuJourActivity.class);
                startActivity(intent);
            }
        });


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.model_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCommande.setAdapter(adapter);

        final Intent intent = new Intent(Commande.this,
                RemerciementCommande.class);
        btReserverCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("heure", spinnerCommande.getSelectedItemPosition());
                intent.putExtra("nom", txtNomCommande.getText().toString());

                Commande.this.startActivity(intent);

            }
        });
    }
}

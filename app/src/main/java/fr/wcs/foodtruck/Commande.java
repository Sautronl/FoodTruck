package fr.wcs.foodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by apprenti on 27/09/17.
 */

public class Commande  extends AppCompatActivity {
    CheckBox checkCommande;
    Button btReserverCommande;
    EditText txtNomCommande;
    EditText txtTelCommande;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        checkCommande = (CheckBox) findViewById(R.id.checkBox);
        btReserverCommande = (Button) findViewById(R.id.buttonReserver);
        txtNomCommande = (EditText) findViewById(R.id.editTextNom);
        txtTelCommande = (EditText) findViewById(R.id.editTextTel);
        Button backCommande = (Button) findViewById(R.id.buttonBack);
        final ImageView warningNom = (ImageView) findViewById(R.id.warningNom);
        final ImageView warningTel = (ImageView) findViewById(R.id.warningTel);
        final TextView votreNom = (TextView) findViewById(R.id.votreNom);
        final TextView votreTel = (TextView) findViewById(R.id.votreTel);

        checkCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            // Si la checkBox est cocher alors les champs Nom et téléphone son disponible.
                if (checkCommande.isChecked()) {
                    btReserverCommande.setEnabled(true);
                    txtNomCommande.setEnabled(true);
                    txtTelCommande.setEnabled(true);

                }else {
                    btReserverCommande.setEnabled(false);
                    txtNomCommande.setEnabled(false);
                    txtTelCommande.setEnabled(false);
                }

             //On effectue une action au click.
                btReserverCommande.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                        else if (telCommande.equals("") && !nomCommande.equals("")){

                            Toast.makeText(getApplicationContext(), getResources().getString
                                    (R.string.toast_champ_vide_tel), Toast.LENGTH_SHORT).show();
                            warningTel.setVisibility(View.VISIBLE);
                            warningNom.setVisibility(View.GONE);
                            votreTel.setTextColor(getResources().getColor(R.color.rougeDark));
                            votreNom.setTextColor(getResources().getColor(R.color.black));


                        }
             //Si le champ nom est vide alors on affiche un toast.
                        else if (nomCommande.equals("") && !telCommande.equals("")){

                            Toast.makeText(getApplicationContext(), getResources().getString
                                    (R.string.toast_champ_vide_nom), Toast.LENGTH_SHORT).show();
                            warningNom.setVisibility(View.VISIBLE);
                            warningTel.setVisibility(View.GONE);
                            votreNom.setTextColor(getResources().getColor(R.color.rougeDark));
                            votreTel.setTextColor(getResources().getColor(R.color.black));

                        }
             //Sinon on affiche un toast expliquant que la commande a était prise en compt.
                        else {
                            Toast.makeText(getApplicationContext(), getResources().getString
                                    (R.string.toast_champ_remplis), Toast.LENGTH_SHORT).show();
                            warningTel.setVisibility(View.GONE);
                            warningNom.setVisibility(View.GONE);
                            votreNom.setTextColor(getResources().getColor(R.color.black));
                            votreTel.setTextColor(getResources().getColor(R.color.black));

                        }

                    }
                });



            }
        });

        backCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Commande.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
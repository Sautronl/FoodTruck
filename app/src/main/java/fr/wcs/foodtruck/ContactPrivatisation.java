package fr.wcs.foodtruck;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sam on 26/09/17.
 */

public class ContactPrivatisation extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactprivatisation);

        EditText editSujet = (EditText) findViewById(R.id.sujet);
        TextView numTel = (TextView) findViewById(R.id.numTel);


        // checkbox
        final CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editSujet= (EditText) findViewById(R.id.sujet);

                if (checkbox.isChecked()) {
                    // est coché
                    String privateOK = editSujet.getResources().getString(R.string.sujet2);
                    editSujet.setText(privateOK);

                } else {
                    // n'est pas coché
                    editSujet.setText("");
                }
            }
        });

        numTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        // bascule sur la page menu en cliquant sur le logo menu

        ImageView boutonRetourMenu = (ImageView)findViewById(R.id.backHomePage);
        final Intent intent2 = new Intent(ContactPrivatisation.this,MainActivity.class);
        boutonRetourMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

          // Message Toast si les champs obligatoires ne sont pas remplis

        Button boutonValider = (Button)findViewById(R.id.boutonEnvoyer);
        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editPrenomNom = (EditText) findViewById(R.id.prenomNom);
                EditText editSujet = (EditText)findViewById(R.id.sujet);
                EditText editMessage = (EditText)findViewById(R.id.message);
                if(editPrenomNom.getText().toString().isEmpty()
                        || editSujet.getText().toString().isEmpty()
                        || editMessage.getText().toString().isEmpty() )
                {

                    CharSequence text = getResources().getString(R.string.messToast);
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Toast messToast = Toast.makeText(context, text, duration);
                    messToast.show();
                }
             // Message Toast de confirmation d'envoie
                else {
                    CharSequence text = getResources().getString(R.string.messToastValider);
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Toast messToast = Toast.makeText(context, text, duration);
                    messToast.show(); }

            }



        });



    }

}
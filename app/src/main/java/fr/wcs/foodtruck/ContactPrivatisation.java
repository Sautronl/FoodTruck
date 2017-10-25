package fr.wcs.foodtruck;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by sam on 26/09/17.
 */

public class ContactPrivatisation extends AppCompatActivity {

    private FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    private DatabaseReference mref = mFirebase.getReference();
    private EditText mPrenomNom;
    private EditText mTel;
    private EditText mSujet;
    private EditText mMessage;
    private Button mBoutonValider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactprivatisation);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ContactPrivatisation.this, MainActivity.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

         mPrenomNom = (EditText) findViewById(R.id.prenomNom);
         mTel = (EditText) findViewById(R.id.tel);
         mSujet = (EditText) findViewById(R.id.sujet);
         mMessage = (EditText) findViewById(R.id.message);
        TextView numTel = (TextView) findViewById(R.id.numTel);
        ImageButton imageBoutonPhone = (ImageButton) findViewById(R.id.imageBoutonPhone);


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

        imageBoutonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        // Message Toast si les champs obligatoires ne sont pas remplis

        mBoutonValider = (Button)findViewById(R.id.boutonEnvoyer);
        mBoutonValider.setOnClickListener(new View.OnClickListener() {
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

        createContact();


    }

    private void createContact() {

         mBoutonValider = (Button)findViewById(R.id.boutonEnvoyer);
         mBoutonValider.setOnClickListener(new View.OnClickListener() {
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
                    messToast.show();
                    ContactAdminModel contact = new ContactAdminModel(UUID.randomUUID().toString(),mPrenomNom.getText().toString(),
                            mTel.getText().toString(), mSujet.getText().toString(),mMessage.getText().toString());

                    mref.child("contact").child(contact.getId()).setValue(contact);
                    clearEditText();
                }
            }
        });
    }

    private void clearEditText() {
        mPrenomNom.setText("");
        mTel.setText("");
        mSujet.setText("");
        mMessage.setText("");
    }
}
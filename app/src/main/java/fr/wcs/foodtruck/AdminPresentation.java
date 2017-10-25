package fr.wcs.foodtruck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPresentation extends AppCompatActivity {

    private EditText mQuiSommesNous;
    private EditText mNosValeurs;
    private Button mMaj;


    private FirebaseDatabase mFirebase;
    private DatabaseReference mAproposRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_presentation);

        mFirebase = FirebaseDatabase.getInstance();
        mAproposRef = mFirebase.getReference("ProposMAJ");

        mQuiSommesNous = (EditText) findViewById(R.id.editTextQsn);
        mNosValeurs = (EditText) findViewById(R.id.editTextNosValeurs);
        mMaj = (Button) findViewById(R.id.buttonMaj);

        mMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AdminPresentation.this,"Vôtre page à bien été mise à jour !",Toast.LENGTH_LONG).show();
                sendApropos();


            }
        });

    }
    protected void sendApropos(){

        String presentation = mQuiSommesNous.getText().toString();
        String  valeurs = mNosValeurs.getText().toString();
        mAproposRef.child("QuiSommesNous").setValue(presentation);
        mAproposRef.child("NosValeurs").setValue(valeurs);

    }
}

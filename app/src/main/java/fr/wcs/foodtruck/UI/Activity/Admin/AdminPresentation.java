package fr.wcs.foodtruck.UI.Activity.Admin;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

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

        LinearLayout layoutPresentationAdmin = (LinearLayout) findViewById(R.id.layoutPresentationAdmin);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(layoutPresentationAdmin,mainfont);

        mFirebase = FirebaseDatabase.getInstance();
        mAproposRef = mFirebase.getReference("ProposMAJ");

        mQuiSommesNous = (EditText) findViewById(R.id.editTextQsn);
        mNosValeurs = (EditText) findViewById(R.id.editTextNosValeurs);
        mMaj = (Button) findViewById(R.id.buttonMaj);

        mMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuiSommesNous.getText().toString().isEmpty() || mNosValeurs.getText().toString().isEmpty()){
                    Toast.makeText(AdminPresentation.this, getResources().getString(R.string.messToast),Toast.LENGTH_SHORT).show();
                }else{
                Toast.makeText(AdminPresentation.this,"Vôtre page à bien été mise à jour !",Toast.LENGTH_LONG).show();
                sendApropos();
                }
            }
        });
        addPres();
        addVal();
    }

    protected void sendApropos(){

        String presentation = mQuiSommesNous.getText().toString();
        String  valeurs = mNosValeurs.getText().toString();
        mAproposRef.child("QuiSommesNous").setValue(presentation);
        mAproposRef.child("NosValeurs").setValue(valeurs);
    }

    protected void addPres(){
        mAproposRef.child("QuiSommesNous").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String prop = dataSnapshot.getValue(String.class);
                    mQuiSommesNous.setText(prop);
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void addVal(){
        mAproposRef.child("NosValeurs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                mNosValeurs.setText(val);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

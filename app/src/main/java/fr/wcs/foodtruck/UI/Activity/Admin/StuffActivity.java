package fr.wcs.foodtruck.UI.Activity.Admin;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.foodtruck.R;

public class StuffActivity extends AppCompatActivity {

    EditText nbBoisson,editText;
    TextView cbboides,questionStuff;
    Button createET, boissonOK,createDrink,createDessert;
    EditText[] edit;
    Integer[] tagStr;
    ConstraintLayout constrainteStuff;
    LinearLayout linearLayoutStuff;
    int nb;
    private FirebaseDatabase mFire;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff);

        nbBoisson = (EditText) findViewById(R.id.nbBoisson);
        linearLayoutStuff = (LinearLayout) findViewById(R.id.linearLayoutStuff);
        createET = (Button) findViewById(R.id.createET);
        boissonOK = (Button) findViewById(R.id.boissonOK);
        createDrink = (Button) findViewById(R.id.createDrink);
        createDessert = (Button) findViewById(R.id.createDessert);
        cbboides = (TextView) findViewById(R.id.cbboides);
        questionStuff = (TextView) findViewById(R.id.questionStuff);
        constrainteStuff = (ConstraintLayout) findViewById(R.id.constrainteStuff);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        createDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDessert.setVisibility(View.GONE);
                createDrink.setVisibility(View.GONE);
                cbboides.setVisibility(View.VISIBLE);
                cbboides.setText(getResources().getString(R.string.cb_boisson));
                nbBoisson.setVisibility(View.VISIBLE);
                createET.setVisibility(View.VISIBLE);
                constrainteStuff.setBackgroundResource(R.drawable.background_boisson);
                questionStuff.setTextColor(getResources().getColor(R.color.blanc));
                nbBoisson.setBackgroundColor(getResources().getColor(R.color.blanc));
                cbboides.setTextColor(getResources().getColor(R.color.blanc));
                if (!nbBoisson.getText().toString().isEmpty()){
                    nbBoisson.setText("");
                }
                displayChoice("menu/Boisson/","boisson");
            }
        });

        createDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDessert.setVisibility(View.GONE);
                createDrink.setVisibility(View.GONE);
                cbboides.setVisibility(View.VISIBLE);
                cbboides.setText(getResources().getString(R.string.cb_dessert));
                nbBoisson.setVisibility(View.VISIBLE);
                createET.setVisibility(View.VISIBLE);
                constrainteStuff.setBackgroundResource(R.drawable.background_dessert);
                questionStuff.setTextColor(getResources().getColor(R.color.black));
                nbBoisson.setBackgroundColor(getResources().getColor(R.color.blanc));
                cbboides.setTextColor(getResources().getColor(R.color.black));
                if (!nbBoisson.getText().toString().isEmpty()){
                    nbBoisson.setText("");
                }
                displayChoice("menu/Dessert/","dessert");
            }
        });
    }

    private void displayChoice(String child,String choix){
        createET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutStuff.setVisibility(View.VISIBLE);
                if (linearLayoutStuff.getChildCount()>0){
                    linearLayoutStuff.removeAllViews();
                }
                if (nbBoisson.getText().toString().isEmpty()){
                    Toast.makeText(StuffActivity.this, "Veuillez remplir le champ", Toast.LENGTH_SHORT).show();
                }else{
                    if (mRef.child(child)!= null){
                        mRef.child(child).removeValue();
                    }
                    nb = Integer.valueOf(nbBoisson.getText().toString());
                    boissonOK.setVisibility(View.VISIBLE);
                    displayEditText(nb,choix);
                }
            }
        });

        boissonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <linearLayoutStuff.getChildCount(); i++) {
                    if (linearLayoutStuff.getChildAt(i) instanceof EditText){
                        int index = (Integer) linearLayoutStuff.getChildAt(i).getTag();
                        String value = edit[index].getText().toString();
                        if(value != null && !value.isEmpty()) {
                            mRef.child(child+choix+i).setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(StuffActivity.this, "Mise a jour effectue avec succes", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                boissonOK.setVisibility(View.GONE);
                linearLayoutStuff.setVisibility(View.GONE);
                createET.setVisibility(View.GONE);
                cbboides.setVisibility(View.GONE);
                nbBoisson.setVisibility(View.GONE);
                createDrink.setVisibility(View.VISIBLE);
                createDessert.setVisibility(View.VISIBLE);
            }
        });
    }

    private void displayEditText(int nombre,String choix){
        edit = new EditText[nombre];
        tagStr = new Integer[nombre];

        for (int i = 0; i < nombre; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            editText = new EditText(this);
            editText.setHint(choix+" N"+ i);
            lp.setMargins(0,20,0,0);
            editText.setLayoutParams(lp);
            editText.setPadding(20, 20, 20, 20);
            editText.setTag(i);
            editText.setTextColor(getResources().getColor(R.color.black));
            editText.setBackgroundResource(R.color.blanc);

            // Add EditText to LinearLayout
            if (linearLayoutStuff != null) {
                linearLayoutStuff.addView(editText);
            }
            edit[i] = editText;
            tagStr[i] = (Integer) editText.getTag();
        }
    }
}



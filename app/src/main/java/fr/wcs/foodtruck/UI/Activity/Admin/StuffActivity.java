package fr.wcs.foodtruck.UI.Activity.Admin;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.wcs.foodtruck.R;

public class StuffActivity extends AppCompatActivity {

    EditText nbBoisson,editText;
    Button createET, boissonOK;
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

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        createET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayoutStuff.getChildCount()>0){
                    linearLayoutStuff.removeAllViews();
                }
                nb = Integer.valueOf(nbBoisson.getText().toString());
                boissonOK.setVisibility(View.VISIBLE);
                displayEditText(nb);
            }
        });
        boissonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <linearLayoutStuff.getChildCount() ; i++) {
                    if (linearLayoutStuff.getChildAt(i) instanceof EditText){
                        EditText et = (EditText) linearLayoutStuff.getChildAt(i).getTag();
                        if(et != null) {
                            String gg = et.getText().toString();
                            mRef.child("menu/Stuff/Boisson"+i).setValue(gg);
                        }
//                        String FireBoisson = linearLayoutStuff.getChildAt(i).getTag().toString();
//                        String gg = editText.getText().toString();
                    }
                }
            }
        });
    }

    private void displayEditText(int nombre){
        for (int i = 1; i < nombre +1; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            editText = new EditText(this);
            editText.setHint("boisson N"+ i);
            lp.setMargins(0,20,0,0);
            editText.setLayoutParams(lp);
            editText.setPadding(20, 20, 20, 20);
            editText.setTag(i);

            // Add EditText to LinearLayout
            if (linearLayoutStuff != null) {
                linearLayoutStuff.addView(editText);
            }
        }
    }

    private void val(int j,String boi){
        boissonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String FireBoisson = editText.getText().toString();
                mRef.child("menu/stuff"+j).setValue(boi);
            }
        });

    }
}


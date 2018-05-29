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

//        if (!nbBoisson.getText().toString().isEmpty()){
        createET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (nb!=0){
//                    removeViewInLayout(nb);
//                }
                nb = Integer.valueOf(nbBoisson.getText().toString());
                boissonOK.setVisibility(View.VISIBLE);
                displayEditText(nb);
                //editText.setOnClickListener(this);

            }
        });
        boissonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <linearLayoutStuff.getChildCount() ; i++) {
                    if (!linearLayoutStuff.getChildAt(i).toString().isEmpty()){
//                        String FireBoisson = linearLayoutStuff.getChildAt(i).getTag().toString();
                        String gg = editText.getText().toString();
                        mRef.child("menu/stuff"+i).setValue(gg);
                    }
                }
            }
        });
        }
//    }

    private void displayEditText(int nombre){
        for (int i = 1; i < nombre +1; i++) {
            editText = new EditText(this);
            editText.setHint("boisson N"+ i);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,20,0,0);
            editText.setLayoutParams(lp);
            editText.setPadding(20, 20, 20, 20);
            editText.setTag("editT" + i);

            // Add EditText to LinearLayout
            if (linearLayoutStuff != null) {
                linearLayoutStuff.addView(editText);
            }
        }
    }

//    @Override
//    public void onClick(View v) {
//        int stat = 123456789;
//        String  id = (String) v.getTag();
//        int number = id -stat;
//        val(number);
//
//        if (id.equals()) {
//            String FireBoisson = editText.getText().toString();
//            val(1, FireBoisson);
//
//        }else if(v.getId()==2+123456789){
//            String ff = editText.getText().toString();
//            val(2,ff);
//        }else if(v.getId()==3+123456789){
//            String ff = editText.getText().toString();
//            val(3,ff);
//        }
//

//            case 4+123456789:
//                String kk = editText.getText().toString();
//
//                val(4,kk);
//                break;
//            case 5+123456789:
//                String ll = editText.getText().toString();
//
//                val(5,ll);
//                break;
//            case 6+123456789:
//                val(6);
//                break;
//            case 7+123456789:
//                val(7);
//                break;
//            case 8+123456789:
//                val(8);
//                break;
//            case 9+123456789:
//                val(9);
//                break;
//            case 10+123456789:
//                val(10);
//                break;



    private void val(int j,String boi){
        boissonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String FireBoisson = editText.getText().toString();
                mRef.child("menu/stuff"+j).setValue(boi);
            }
        });

    }
    void removeViewInLayout(int nb){
        linearLayoutStuff.removeViews(5,nb+3);
//        for(int i = 1; i<linearLayoutStuff.getChildCount(); i++){
//            if(linearLayoutStuff.getChildAt(i) instanceof EditText){
//                linearLayoutStuff.removeView(linearLayoutStuff.getChildAt(i));
//            }
        }
    }


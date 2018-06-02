package fr.wcs.foodtruck.UI.Activity.Admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.foodtruck.R;

public class scheduleActivity extends AppCompatActivity implements View.OnClickListener{

    CheckedTextView lundi,mardi,mercredi,jeudi,vendredi,samedi,dimanche;
    Boolean avaible = true;

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    String[] day;
    int j = 0;
    int count = 0;
    CheckedTextView[] checkTab;
    Button checkJobDay,editJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        lundi = (CheckedTextView) findViewById(R.id.lundiDispo);
        mardi = (CheckedTextView) findViewById(R.id.mardiDispo);
        mercredi = (CheckedTextView) findViewById(R.id.mercrediDispo);
        jeudi = (CheckedTextView) findViewById(R.id.jeudiDispo);
        vendredi = (CheckedTextView) findViewById(R.id.vendrediDispo);
        samedi = (CheckedTextView) findViewById(R.id.samediDispo);
        dimanche = (CheckedTextView) findViewById(R.id.dimancheDispo);
        checkJobDay = (Button) findViewById(R.id.checkJobDay);
        editJob = (Button) findViewById(R.id.editJob);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        lundi.setOnClickListener(this);
        mardi.setOnClickListener(this);
        mercredi.setOnClickListener(this);
        jeudi.setOnClickListener(this);
        vendredi.setOnClickListener(this);
        samedi.setOnClickListener(this);
        dimanche.setOnClickListener(this);
        checkJobDay.setOnClickListener(this);
        editJob.setOnClickListener(this);

    }

    private void displayCheckText() {

        day = new String[]{"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
        checkTab = new CheckedTextView[]{lundi,mardi,mercredi,jeudi,vendredi,samedi,dimanche};

        for (int i = 0; i < day.length; i++) {
            mRef.child("Avaible/" +day[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean jobDispo = dataSnapshot.getValue(Boolean.class);
                    if (jobDispo == true){
                        checkTab[j].setChecked(true);
                        checkTab[j].setEnabled(false);
                    }
                    checkTab[j].setEnabled(false);
                    j++;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        j=0;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.lundiDispo:
                checkParty(lundi);
                checkValid("Avaible/Lundi",avaible);
                break;
            case R.id.mardiDispo:
                checkParty(mardi);
                checkValid("Avaible/Mardi",avaible);
                break;
            case R.id.mercrediDispo:
                checkParty(mercredi);
                checkValid("Avaible/Mercredi",avaible);
                break;
            case R.id.jeudiDispo:
                checkParty(jeudi);
                checkValid("Avaible/Jeudi",avaible);
                break;
            case R.id.vendrediDispo:
                checkParty(vendredi);
                checkValid("Avaible/Vendredi",avaible);
                break;
            case R.id.samediDispo:
                checkParty(samedi);
                checkValid("Avaible/Samedi",avaible);
                break;
            case R.id.dimancheDispo:
                checkParty(dimanche);
                checkValid("Avaible/Dimanche",avaible);
                break;
            case R.id.checkJobDay:
                if (count== 0){
                    displayCheckText();
                    checkJobDay.setVisibility(View.GONE);
                    editJob.setVisibility(View.VISIBLE);
                }
                count++;
                break;
            case R.id.editJob:
                for (int k = 0; k < checkTab.length; k++) {
                    checkTab[k].setEnabled(true);
                }
                editJob.setVisibility(View.GONE);
        }
    }

    private Boolean checkParty(CheckedTextView day){
        if (day.isChecked()){
            avaible = false;
            day.setChecked(false);
        }else{
            avaible = true;
            day.setChecked(true);
        }
        return avaible;
    }

    private void checkValid(String child,Boolean dispo){
       mRef.child(child).setValue(dispo).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               Toast.makeText(scheduleActivity.this, "Prise en compte effectue", Toast.LENGTH_SHORT).show();
           }
       });
    }
}

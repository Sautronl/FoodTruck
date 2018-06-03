package fr.wcs.foodtruck.UI.Activity.User;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Model.ReservationModels;
import fr.wcs.foodtruck.UI.Adapter.AdapterMenuListe;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

/**
 * Created by apprenti on 27/09/17.
 */

public class Commande  extends AppCompatActivity {

    Button btReserverCommande,menuValiderCommande,finCommande;
    String nomRes, telRes, horaireRes,nomBurg,prixBurg,choixBoisson,choixDessert;
    EditText txtNomCommande;
    EditText txtTelCommande;
    Spinner spinnerCommande;
    RadioGroup radioGroup,radioGroupBoissonOne,radioGroupDessert;
    RadioButton menuOk,menuNope;
    ConstraintLayout firstPart,partTwo,partThree;
    RecyclerView menuListe;
    ImageView editInfo;
    TextView textInfo;
    AdapterMenuListe adapterMenu;
    ArrayList<MajPlatDuJour> menuDisplay = new ArrayList<>();
    ArrayList<ReservationModels> reservationCommande = new ArrayList<>();
    RadioButton[] rbutton;
    Integer[] tagRadio;
    RelativeLayout RlBoisson,RlDessert;

    private DatabaseReference mReservRef,mRefStuff,mRefFinal;
    private FirebaseDatabase mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mFirebase = FirebaseDatabase.getInstance();
        mReservRef = mFirebase.getReference();
        mRefStuff = mFirebase.getReference();
        mRefFinal = mFirebase.getReference();

        ScrollView scrollCommande = (ScrollView) findViewById(R.id.scrollCommande);
        Typeface mainfont = Typeface.createFromAsset(getResources().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(scrollCommande,mainfont);

        btReserverCommande = (Button) findViewById(R.id.btReserver);
        txtNomCommande = (EditText) findViewById(R.id.editTextNom);
        txtTelCommande = (EditText) findViewById(R.id.editTextTel);
        spinnerCommande = (Spinner) findViewById(R.id.spinnerCommande);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroupBoissonOne = (RadioGroup) findViewById(R.id.radioGroupBoissonOne);
        radioGroupDessert = (RadioGroup) findViewById(R.id.radioGroupDessert);
        menuOk = (RadioButton) findViewById(R.id.menuOk);
        menuNope = (RadioButton) findViewById(R.id.menuNope);
        menuListe = (RecyclerView) findViewById(R.id.menuListe);
        menuListe.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firstPart = (ConstraintLayout) findViewById(R.id.firstPart);
        partTwo = (ConstraintLayout) findViewById(R.id.partTwo);
        partThree = (ConstraintLayout) findViewById(R.id.partThree);
        editInfo = (ImageView) findViewById(R.id.editInfo);
        textInfo = (TextView) findViewById(R.id.infoModifText);
        menuValiderCommande = (Button) findViewById(R.id.menuValiderCommande);
        finCommande = (Button) findViewById(R.id.finCommande);

        // On crée l'adapter pour le spinner.
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.model_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCommande.setAdapter(adapter);

        spinnerCommande.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Si aucune horaires n'est selectionner on affiche un toast
                if (i == 0) {
                    txtNomCommande.setEnabled(false);
                    txtTelCommande.setEnabled(false);
                }else
                {
                    txtNomCommande.setEnabled(true);
                    txtTelCommande.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btReserverCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNomCommande.getText().toString().isEmpty() || txtTelCommande.getText().toString().isEmpty()){
                    radioGroup.setVisibility(View.GONE);

                }else{
                    firstPart.setVisibility(View.GONE);
                    partTwo.setVisibility(View.VISIBLE);
                    radioGroup.setVisibility(View.VISIBLE);
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {

                            if (checkedId == R.id.menuOk){
                                menuListe.setVisibility(View.VISIBLE);

                                mReservRef.child("menu/").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (menuDisplay.size()> 0) menuDisplay.clear();
                                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                            MajPlatDuJour majPlatDuJour = snapshot.getValue(MajPlatDuJour.class);
                                            menuDisplay.add(majPlatDuJour);
                                        }
                                        adapterMenu = new AdapterMenuListe(getApplicationContext(),menuDisplay);
                                        menuListe.setAdapter(adapterMenu);
                                        adapterMenu.setOnItemClickListener(new AdapterMenuListe.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(int position, MajPlatDuJour majPlatDuJour, Context context, CheckBox checkBoxCommande) {
                                                for (int i = 0; i <menuListe.getChildCount() ; i++) {
                                                    if (position == i){
                                                        menuListe.getChildAt(i).setBackgroundColor(Color.YELLOW);
                                                    }else {
                                                        menuListe.getChildAt(i).setBackgroundColor(Color.WHITE);
                                                    }
                                                }
                                                Reservation("Réservation/",checkBoxCommande,majPlatDuJour);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPart.setVisibility(View.VISIBLE);
                partTwo.setVisibility(View.GONE);
            }
        });
        textInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPart.setVisibility(View.VISIBLE);
                partTwo.setVisibility(View.GONE);
            }
        });
    }

    private void Reservation(String child,CheckBox checkBoxCommande,MajPlatDuJour majPlatDuJour){
             checkBoxCommande.isChecked();
             checkBoxCommande.setVisibility(View.GONE);
             nomRes = txtNomCommande.getText().toString();
             telRes = txtTelCommande.getText().toString();
             horaireRes = spinnerCommande.getItemAtPosition(spinnerCommande.getSelectedItemPosition()).toString();
             prixBurg = majPlatDuJour.getPrix();
             nomBurg = majPlatDuJour.getNomPlat();
            valid(child);
        }

    private void valid(String lol){
        menuValiderCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partTwo.setVisibility(View.GONE);
                partThree.setVisibility(View.VISIBLE);
                menuValiderCommande.setVisibility(View.GONE);
            }
        });
        radioGroupBoissonOne= new RadioGroup(getApplicationContext());
        radioGroupBoissonOne.setOrientation(RadioGroup.VERTICAL);
        radioGroupDessert= new RadioGroup(getApplicationContext());
        radioGroupDessert.setOrientation(RadioGroup.VERTICAL);
        displayRadioBut("menu/Boisson/",radioGroupBoissonOne);
        displayRadioBut("menu/Dessert",radioGroupDessert);
    }

    private void displayRadioBut(String child,RadioGroup group) {


        mRefStuff.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String > drinknbr = new ArrayList<>();
                long buttonRadio = dataSnapshot.getChildrenCount();
                int convButRadio = Integer.parseInt(String.valueOf(buttonRadio));
                rbutton = new RadioButton[convButRadio];
                tagRadio = new Integer[convButRadio];
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String boisNb = snapshot.getValue(String.class);
                    drinknbr.add(boisNb);
                }
                for (int i = 0; i < convButRadio; i++) {
                    RadioButton rbn = new RadioButton(Commande.this);
                    rbn.setTextColor(getResources().getColor(R.color.blanc));
                    rbn.setText(drinknbr.get(i));
                    rbn.setTag(i+154875541);
                    group.addView(rbn);
                    rbutton[i] = rbn;
                    tagRadio[i] = (Integer) rbn.getTag();
                }
                if (child.equals("menu/Boisson/")){
                     RlBoisson = (RelativeLayout) (Commande.this.findViewById(R.id.contentRl));
                    RlBoisson.addView(group);
                    radioGroupBoissonOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int indexRa = radioGroupBoissonOne.getCheckedRadioButtonId();
                            checkRadioGr(checkedId,tagRadio,rbutton,"boisson",RlBoisson,indexRa);
                        }
                    });
                }else{
                     RlDessert = (RelativeLayout) (Commande.this.findViewById(R.id.containerDessert));
                    RlDessert.addView(group);
                    radioGroupDessert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int indexRad = radioGroupDessert.getCheckedRadioButtonId();
                            checkRadioGr(checkedId,tagRadio,rbutton,"dessert",RlDessert,indexRad);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        radioGroupBoissonOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                checkRadioGr(checkedId,tagRadio,rbutton,"boisson",RlBoisson);
//            }
//        });
//
//        radioGroupDessert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                checkRadioGr(checkedId,tagRadio,rbutton,"dessert",RlDessert);
//            }
//        });
    }

    private void checkRadioGr(int checkID,Integer[] tag,RadioButton[] butRad,String choix,RelativeLayout rlChoix,int indexRad) {
//        if (checkID == tag[i]){

//        for (int i = 0; i < rlChoix.getChildCount(); i++) {
//            if (rlChoix.getChildAt(i) instanceof RadioGroup) {
//                int index = (Integer) rlChoix.getChildAt(i).getTag();
        if (choix.equals("boisson")){
            if(checkID == indexRad) {
                choixBoisson = butRad[indexRad].getText().toString();
            }
        }else{
            choixDessert = butRad[indexRad-3].getText().toString();
            }



//        for (int i = 0; i < butRad.length; i++) {
//            if (checkID == tag[i]){
//                if(choix.equals("boisson")){
//                    choixBoisson = butRad[i].getText().toString();
//                }else{
//                    choixDessert = butRad[i].getText().toString();
//                }
//            }
//        }
        finCommande.setVisibility(View.VISIBLE);
        finCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationModels res = new ReservationModels(UUID.randomUUID().toString(), nomRes, telRes, horaireRes,nomBurg,prixBurg,choixBoisson,choixDessert);
                mRefFinal.child("Réservation/"+res.getId()).setValue(res);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Vous etes sur le point d'annuler la commande")
                .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Nope", null).show();
    }
}











































//    protected void createRes() {
//        String nomRes = txtNomCommande.getText().toString();
//        String telRes = txtTelCommande.getText().toString();
//        String horaireRes = spinnerCommande.getItemAtPosition(spinnerCommande.getSelectedItemPosition()).toString();
//
//        ReservationModels res = new ReservationModels(UUID.randomUUID().toString(), nomRes, telRes, horaireRes);
//        mReservRef.child("Réservation/" + res.getId()).setValue(res);
//    }

//    protected void menuCheck(){
//        mReservRef.child("menu/").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (menuDisplay.size()> 0) menuDisplay.clear();
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    MajPlatDuJour majPlatDuJour = snapshot.getValue(MajPlatDuJour.class);
//                    menuDisplay.add(majPlatDuJour);
//                }
//                adapterMenu = new AdapterMenuListe(getApplicationContext(),menuDisplay);
//                menuListe.setAdapter(adapterMenu);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
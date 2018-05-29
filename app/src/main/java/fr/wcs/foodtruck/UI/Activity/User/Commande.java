package fr.wcs.foodtruck.UI.Activity.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    String nomRes, telRes, horaireRes,nomBurg,prixBurg;
    EditText txtNomCommande;
    EditText txtTelCommande;
    Spinner spinnerCommande;
    RadioGroup radioGroup,radioGroupBoissonTwo,radioGroupBoissonOne,radioGroupDessert;
    RadioButton menuOk,menuNope,boissonPepsi,boissonEau,boissonCola,boissonAdel,boissonSprite,boissonLipton;
    ConstraintLayout firstPart,partTwo,partThree;
    RecyclerView menuListe;
    ImageView editInfo;
    TextView textInfo;
    AdapterMenuListe adapterMenu;
    ArrayList<MajPlatDuJour> menuDisplay = new ArrayList<>();
    ArrayList<ReservationModels> reservationCommande = new ArrayList<>();

    private DatabaseReference mReservRef;
    private FirebaseDatabase mFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mFirebase = FirebaseDatabase.getInstance();
        mReservRef = mFirebase.getReference();

        ScrollView scrollCommande = (ScrollView) findViewById(R.id.scrollCommande);
        Typeface mainfont = Typeface.createFromAsset(getResources().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(scrollCommande,mainfont);

        btReserverCommande = (Button) findViewById(R.id.btReserver);
        txtNomCommande = (EditText) findViewById(R.id.editTextNom);
        txtTelCommande = (EditText) findViewById(R.id.editTextTel);
        spinnerCommande = (Spinner) findViewById(R.id.spinnerCommande);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroupBoissonOne = (RadioGroup) findViewById(R.id.radioGroupBoissonOne);
        radioGroupBoissonTwo = (RadioGroup) findViewById(R.id.radioGroupBoissonTwo);
        radioGroupDessert = (RadioGroup) findViewById(R.id.radioGroupDessert);
        menuOk = (RadioButton) findViewById(R.id.menuOk);
        menuNope = (RadioButton) findViewById(R.id.menuNope);
        boissonAdel = (RadioButton) findViewById(R.id.boissonAdelscott);
        boissonCola = (RadioButton) findViewById(R.id.boissonCola);
        boissonEau = (RadioButton) findViewById(R.id.boissonEau);
        boissonLipton = (RadioButton) findViewById(R.id.boissonLipton);
        boissonSprite = (RadioButton) findViewById(R.id.boissonsprite);
        boissonPepsi = (RadioButton) findViewById(R.id.boissonPepsi);
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
                                                switch (position){
                                                    case 0:
                                                        Reservation("Réservation/",checkBoxCommande,majPlatDuJour);
                                                        break;
                                                    case 1:
                                                        Reservation("Réservation/",checkBoxCommande,majPlatDuJour);
                                                        break;
                                                    case 2:
                                                        Reservation("Réservation/",checkBoxCommande,majPlatDuJour);
                                                        break;
                                                    case 3:
                                                        Reservation("Réservation/",checkBoxCommande,majPlatDuJour);
                                                        break;
                                                    case 4:
                                                        Reservation("Réservation/",checkBoxCommande,majPlatDuJour);
                                                        break;

                                                }
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



//        btReserverCommande.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (txtNomCommande.getText().toString().isEmpty() || txtTelCommande.getText().toString().isEmpty()) {
//                    Toast.makeText(Commande.this, getResources().getString(R.string.messToast), Toast.LENGTH_SHORT).show();
//                } else {
//                    createRes();
//                    Intent intent = new Intent(Commande.this, RemerciementCommande.class);
//                    intent.putExtra("heure", "Elle sera prête pour " + spinnerCommande.getItemAtPosition
//                            (spinnerCommande.getSelectedItemPosition()).toString());
//                    intent.putExtra("nom", "Merci " + txtNomCommande.getText().toString());
//
//                    Commande.this.startActivity(intent);
//                    //Le finish permet de ne par revenir sur la page
//                    // Commande dès que l'on a deja commmander.
//                    finish();
//                }
//            }
//        });
    }

    private void Reservation(String child,CheckBox checkBoxCommande,MajPlatDuJour majPlatDuJour){
             checkBoxCommande.isChecked();
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


    }
}








//    ReservationModels res = new ReservationModels(UUID.randomUUID().toString(), nomRes, telRes, horaireRes,nomBurg,prixBurg);
//                mReservRef.child(lol + res.getId()).setValue(res);


































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
package fr.wcs.foodtruck.UI.Activity.Admin;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.wcs.foodtruck.R;

public class HoraireActivity extends AppCompatActivity {

    private RadioGroup radioGroupDay, radioGroupIntervalle;
    private RadioButton radioSemaine, radioJour, radioHorsWE, radioQuinze, radioTrente;
    private Spinner spinnerJour;
    private EditText EditTDebut, EditTFin, EditTDebutMidi, EditTFinMidi, EditTDebutSoir, EditTFinSoir;
    private Spinner spinnerDebutMidiMinute,spinnerFinMidiMinute,spinnerDebutSoirMinute,spinnerFinSoirMinute,spinnerDebutMinute,spinnerFinMinute;
    private RelativeLayout relativeInter, relativeSpinner, relativeCheckD, relativePlageMatin,
            relativePlageMidi, relativePlageSoir;
    private Button horaireValide, ValidCheckHoraire,buttonCheckH;
    private CheckBox checkMatin, checkMidi, checkSoir;
    int intervalle, getCheckedRadio, nbrHours,heureDebut,heureFin;
    String getDay;
    int debutMatin,finMatin,debutMi,finMi,debutS,finS;
    int debutMatinHeure,finMatinHeure,debutMiHeure,finMiHeure,debutSHeure,finSHeure;

    private ArrayList<String> quinze = new ArrayList<>();
    private ArrayList<String> horaireFillMatin = new ArrayList<>();
    private ArrayList<String> horaireFillMidi = new ArrayList<>();
    private ArrayList<String> horaireFillSoir = new ArrayList<>();
    private FirebaseDatabase mFire;
    private DatabaseReference mRefHoraire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaire);


        radioGroupDay = (RadioGroup) findViewById(R.id.radioGroupDay);
        radioGroupIntervalle = (RadioGroup) findViewById(R.id.radioGroupIntervalle);
        spinnerJour = (Spinner) findViewById(R.id.spinnerJour);
        radioSemaine = (RadioButton) findViewById(R.id.radioSemaine);
        radioJour = (RadioButton) findViewById(R.id.radioJour);
        radioHorsWE = (RadioButton) findViewById(R.id.radioHorsWE);
        radioQuinze = (RadioButton) findViewById(R.id.radioQuinze);
        radioTrente = (RadioButton) findViewById(R.id.radioTrente);
        relativeInter = (RelativeLayout) findViewById(R.id.relativeInter);
        relativeSpinner = (RelativeLayout) findViewById(R.id.relativeSpinner);
        relativeCheckD = (RelativeLayout) findViewById(R.id.relativeCheckD);
        relativePlageMatin = (RelativeLayout) findViewById(R.id.relativePlageMatin);
        relativePlageMidi = (RelativeLayout) findViewById(R.id.relativePlageMidi);
        relativePlageSoir = (RelativeLayout) findViewById(R.id.relativePlageSoir);
        horaireValide = (Button) findViewById(R.id.horaireValide);
        buttonCheckH = (Button) findViewById(R.id.buttonCheckH);
        ValidCheckHoraire = (Button) findViewById(R.id.ValidCheckHoraire);
        checkMatin = (CheckBox) findViewById(R.id.checkMatin);
        checkMidi = (CheckBox) findViewById(R.id.checkMidi);
        checkSoir = (CheckBox) findViewById(R.id.checkSoir);
        spinnerDebutMidiMinute = (Spinner) findViewById(R.id.spinnerDebutMidiMinute);
        spinnerFinMidiMinute = (Spinner) findViewById(R.id.spinnerFinMidiMinute);
        spinnerDebutSoirMinute = (Spinner) findViewById(R.id.spinnerDebutSoirMinute);
        spinnerFinSoirMinute = (Spinner) findViewById(R.id.spinnerFinSoirMinute);
        spinnerDebutMinute = (Spinner) findViewById(R.id.spinnerDebutMinute);
        spinnerFinMinute = (Spinner) findViewById(R.id.spinnerTFinMinute);

        quinze.add("minute");
        quinze.add("00");
        quinze.add("15");
        quinze.add("30");

        mFire = FirebaseDatabase.getInstance();
        mRefHoraire = mFire.getReference();

        radioGroupDay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioSemaine:
                        radioSemaine.setEnabled(false);
                        radioHorsWE.setEnabled(false);
                        radioJour.setEnabled(false);
                        //
                        break;
                    case R.id.radioJour:
                        relativeSpinner.setVisibility(View.VISIBLE);
                        radioJour.setEnabled(false);
                        radioSemaine.setEnabled(false);
                        radioHorsWE.setEnabled(false);

                        afficherJour();

                        break;
                    case R.id.radioHorsWE:
                        radioJour.setEnabled(false);
                        radioSemaine.setEnabled(false);
                        radioHorsWE.setEnabled(false);
                        break;
                }
            }
        });

        ValidCheckHoraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMatin.isChecked()){
                    relativePlageMatin.setVisibility(View.VISIBLE);
                    buttonCheckH.setVisibility(View.VISIBLE);
                    checkMatin.setEnabled(false);
                    checkMidi.setEnabled(false);
                    checkSoir.setEnabled(false);
                    setSpinnerIntervalle(spinnerDebutMinute);
                    setSpinnerIntervalle(spinnerFinMinute);
                    getHeure("matin",getDay);
                }else {
                    relativePlageMatin.setVisibility(View.GONE);
                }
                if (checkMidi.isChecked()){
                    relativePlageMidi.setVisibility(View.VISIBLE);
                    buttonCheckH.setVisibility(View.VISIBLE);
                    checkMatin.setEnabled(false);
                    checkMidi.setEnabled(false);
                    checkSoir.setEnabled(false);
                    setSpinnerIntervalle(spinnerDebutMidiMinute);
                    setSpinnerIntervalle(spinnerFinMidiMinute);
                    getHeure("midi",getDay);
                }else {
                    relativePlageMidi.setVisibility(View.GONE);
                }
                if (checkSoir.isChecked()){
                    relativePlageSoir.setVisibility(View.VISIBLE);
                    buttonCheckH.setVisibility(View.VISIBLE);
                    checkMatin.setEnabled(false);
                    checkMidi.setEnabled(false);
                    checkSoir.setEnabled(false);
                    setSpinnerIntervalle(spinnerDebutSoirMinute);
                    setSpinnerIntervalle(spinnerFinSoirMinute);
                    getHeure("soir",getDay);
                }else {
                    relativePlageSoir.setVisibility(View.GONE);
                }if (!checkMatin.isChecked() && !checkMidi.isChecked() && !checkSoir.isChecked()){
                    Toast.makeText(HoraireActivity.this, "Veuillez cocher une/des propositions", Toast.LENGTH_SHORT).show();
                    buttonCheckH.setVisibility(View.GONE);
                }else{
                    buttonCheckH.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void getHeure(String momentJournee, String jourSpinner) {

        EditTDebut = (EditText) findViewById(R.id.EditTDebut);
        EditTFin = (EditText) findViewById(R.id.EditTFin);
        EditTFinMidi = (EditText) findViewById(R.id.EditTFinMidi);
        EditTDebutMidi = (EditText) findViewById(R.id.EditTDebutMidi);
        EditTDebutSoir = (EditText) findViewById(R.id.EditTDebutSoir);
        EditTFinSoir = (EditText) findViewById(R.id.EditTFinSoir);


        ValidCheckHoraire.setEnabled(false);

        buttonCheckH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (momentJournee.equals("matin")){
                    if (EditTDebut.getText().toString().isEmpty() || EditTFin.getText().toString().isEmpty()){
                        Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                    }else{
                        debutMatinHeure = Integer.parseInt(EditTDebut.getText().toString());
                        finMatinHeure = Integer.parseInt(EditTFin.getText().toString());
                        relativeInter.setVisibility(View.VISIBLE);
                        getPlanning(debutMatinHeure,debutMatin,finMatinHeure,finMatin,"matin");
                    }
                }if (momentJournee.equals("midi")){
                    if (EditTDebutMidi.getText().toString().isEmpty() || EditTFinMidi.getText().toString().isEmpty()){
                        Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                    }else{
                        debutMiHeure = Integer.parseInt(EditTDebutMidi.getText().toString());
                        finMiHeure = Integer.parseInt(EditTFinMidi.getText().toString());
                        relativeInter.setVisibility(View.VISIBLE);
                        getPlanning(debutMiHeure,debutMi,finMiHeure,finMi,"midi");
                    }
                }if (momentJournee.equals("soir")){
                    if (EditTDebutSoir.getText().toString().isEmpty() || EditTFinSoir.getText().toString().isEmpty()){
                        Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                    }else{
                        debutSHeure = Integer.parseInt(EditTDebutSoir.getText().toString());
                        finSHeure = Integer.parseInt(EditTFinSoir.getText().toString());
                        relativeInter.setVisibility(View.VISIBLE);
                        getPlanning(debutSHeure,debutS,finSHeure,finMatin,"soir");
                    }
                }
            }
        });

        radioGroupIntervalle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioQuinze:
                        intervalle = 15;
                        radioQuinze.setEnabled(false);
                        radioTrente.setEnabled(false);
                        horaireValide.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioTrente:
                        intervalle = 30;
                        horaireValide.setVisibility(View.VISIBLE);
                        radioQuinze.setEnabled(false);
                        radioTrente.setEnabled(false);
                }
            }
        });

        horaireValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horaireFillMatin.size()>0 && radioJour.isChecked()){
                    setValuePlanning("Matin",horaireFillMatin,getDay);
                }if (horaireFillMidi.size()>0 && radioJour.isChecked()){
                    setValuePlanning("Midi",horaireFillMidi,getDay);
                }if (horaireFillSoir.size()>0 && radioJour.isChecked()){
                    setValuePlanning("Soir",horaireFillSoir,getDay);
                }
            }
        });
    }

    private void getPlanning(int debutSHeure, int debutS, int finSHeure, int finMatin,String etat) {

        switch (etat){
            case "matin":
                getHoraireSpinner(debutSHeure,debutS,finSHeure,finMatin,horaireFillMatin);
               break;
            case "midi":
                getHoraireSpinner(debutSHeure,debutS,finSHeure,finMatin,horaireFillMidi);
                break;
            case "soir":
                getHoraireSpinner(debutSHeure,debutS,finSHeure,finMatin,horaireFillSoir);
                break;
        }
    }

    private void setSpinnerIntervalle(Spinner spinnerH){



        ArrayAdapter<String> adapterDebutMin =  new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, quinze);
        adapterDebutMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerH.setAdapter(adapterDebutMin);


            spinnerH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        if (spinnerH.getId() == R.id.spinnerDebutMinute) {
                            debutMatin = 0;
                        } else if (spinnerH.getId() == R.id.spinnerDebutMidiMinute) {
                            debutMi = 0;
                        } else if (spinnerH.getId() == R.id.spinnerDebutSoirMinute){
                            debutS = 0;
                        }else if (spinnerH.getId() == R.id.spinnerTFinMinute){
                            finMatin =0;
                        }else if(spinnerH.getId() == R.id.spinnerFinMidiMinute){
                            finMi = 0;
                        }else if(spinnerH.getId() == R.id.spinnerFinSoirMinute){
                            finS = 0;
                        }
                        break;
                    case 2:
                        if (spinnerH.getId() == R.id.spinnerDebutMinute) {
                            debutMatin = 15;
                        } else if (spinnerH.getId() == R.id.spinnerDebutMidiMinute) {
                            debutMi = 15;
                        } else if (spinnerH.getId() == R.id.spinnerDebutSoirMinute){
                            debutS = 15;
                        }else if (spinnerH.getId() == R.id.spinnerTFinMinute){
                            finMatin =15;
                        }else if(spinnerH.getId() == R.id.spinnerFinMidiMinute){
                            finMi = 15;
                        }else if(spinnerH.getId() == R.id.spinnerFinSoirMinute){
                            finS = 15;
                        }
                        break;
                    case 3:
                        if (spinnerH.getId() == R.id.spinnerDebutMinute) {
                            debutMatin = 30;
                        } else if (spinnerH.getId() == R.id.spinnerDebutMidiMinute) {
                            debutMi = 30;
                        } else if (spinnerH.getId() == R.id.spinnerDebutSoirMinute){
                            debutS = 30;
                        }else if (spinnerH.getId() == R.id.spinnerTFinMinute){
                            finMatin =30;
                        }else if(spinnerH.getId() == R.id.spinnerFinMidiMinute){
                            finMi = 30;
                        }else if(spinnerH.getId() == R.id.spinnerFinSoirMinute){
                            finS = 30;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void afficherJour(){
        ArrayList<String> day = new ArrayList<>();
        day.add("Selectionner");
        day.add("Lundi");
        day.add("Mardi");
        day.add("Mercredi");
        day.add("Jeudi");
        day.add("Vendredi");
        day.add("Samedi");
        day.add("Dimanche");

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, day);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJour.setAdapter(dayAdapter);

        spinnerJour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        getDay = "Lundi";
                        relativeCheckD.setVisibility(View.VISIBLE);
                        spinnerJour.setEnabled(false);
                        break;
                    case 2:
                        getDay = "Mardi";
                        relativeCheckD.setVisibility(View.VISIBLE);
                        spinnerJour.setEnabled(false);
                        break;
                    case 3:
                        getDay = "Mercredi";
                        relativeCheckD.setVisibility(View.VISIBLE);
                        spinnerJour.setEnabled(false);
                        break;
                    case 4:
                        getDay = "Jeudi";
                        relativeCheckD.setVisibility(View.VISIBLE);
                        spinnerJour.setEnabled(false);
                        break;
                    case 5:
                        getDay = "Vendredi";
                        relativeCheckD.setVisibility(View.VISIBLE);
                        spinnerJour.setEnabled(false);
                        break;
                    case 6:
                        getDay = "Samedi";
                        relativeCheckD.setVisibility(View.VISIBLE);
                        spinnerJour.setEnabled(false);
                        break;
                    case 7:
                        getDay = "Dimanche";
                        relativeCheckD.setVisibility(View.VISIBLE);
                        spinnerJour.setEnabled(false);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getHoraireSpinner(int debutSHeure,int debutS,int finSHeure,int finMatin,ArrayList<String> horaireFill){
        for (int i = debutSHeure; i <=finSHeure; i++) {
            if (i == finSHeure){
                debutS = 0;
                for (int j = debutS; j <=finMatin ; j = j + 15) {
                    if (j==0){
                        String hSpinner = String .valueOf(i) +"h" + String .valueOf(j)+"0";
                        horaireFill.add(hSpinner);
                    }else{
                        String hSpinner = String .valueOf(i) +"h" + String .valueOf(j);
                        horaireFill.add(hSpinner);
                    }
                }
            }else{
                for (int j = debutS; j <60 ; j = j + 15) {
                    if (j==0){
                        String hSpinner = String .valueOf(i) +"h" + String .valueOf(j)+"0";
                        horaireFill.add(hSpinner);
                    }else{
                        String hSpinner = String .valueOf(i) +"h" + String .valueOf(j);
                        horaireFill.add(hSpinner);
                    }
                }
            }
        }
//        horaireFill.add(String.valueOf(finSHeure)+"h");
//        setValueSpinner(whichHours);
    }

    private void setValuePlanning(String child,ArrayList<String> etat,String getDay) {
        if (getDay!=null){
            mRefHoraire.child("Horaire/"+getDay+"/"+child).setValue(etat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(HoraireActivity.this, "Horaire mise a jour", Toast.LENGTH_SHORT).show();
                }
            });
//        }else{
//            mRefHoraire.child("Horaire/"+child).setValue(etat).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Toast.makeText(HoraireActivity.this, "Horaire mise a jour", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
//        ArrayAdapter<String> adapterFinMin =  new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, quinze);
//        adapterFinMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        finMinMat.setAdapter(adapterFinMin);
//
//        ArrayAdapter<String> adapterDebutMinMid =  new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, quinze);
//        adapterDebutMinMid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        debutMinMid.setAdapter(adapterDebutMinMid);
//
//        ArrayAdapter<String> adapterFinMinMid =  new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, quinze);
//        adapterFinMinMid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        finMinMid.setAdapter(adapterFinMinMid);
//
//        ArrayAdapter<String> adapterDebutMinSoir =  new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, quinze);
//        adapterDebutMinSoir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        debutMinSoir.setAdapter(adapterDebutMinSoir);
//
//        ArrayAdapter<String> adapterFinMinSoir =  new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, quinze);
//        adapterFinMinSoir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        finMinSoir.setAdapter(adapterFinMinSoir);
//
//        mFire = FirebaseDatabase.getInstance();
//        mRefHoraire = mFire.getReference();
//
//
//        radioGroupHoraire.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.radioMatin:
//                        RelativeIntervalle.setVisibility(View.VISIBLE);
//                        radioMatin.setEnabled(false);
//                        radioSoir.setEnabled(false);
//                        radioPeinTemps.setEnabled(false);
//                        getCheckedRadio = checkedId;
//                        break;
//                    case R.id.radioSoir:
//                        RelativeIntervalle.setVisibility(View.VISIBLE);
//                        radioMatin.setEnabled(false);
//                        radioSoir.setEnabled(false);
//                        radioPeinTemps.setEnabled(false);
//                        getCheckedRadio = checkedId;
//                        break;
//                    case R.id.radioPleinTemps:
//                        RelativeIntervalle.setVisibility(View.VISIBLE);
//                        radioMatin.setEnabled(false);
//                        radioSoir.setEnabled(false);
//                        radioPeinTemps.setEnabled(false);
//                        getCheckedRadio = checkedId;
//                        break;
//                }
//            }
//        });
//
//        radioGroupIntervalle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                switch (checkedId){
//                    case R.id.radioQuinze:
//                        intervalle = 15;
//                        spinnerDebutHoraire.setVisibility(View.VISIBLE);
//                        radioQuinze.setEnabled(false);
//                        radioTrente.setEnabled(false);
//                        getSpinner(getCheckedRadio,intervalle);
//                        RelativeDayWeek.setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.radioTrente:
//                        intervalle = 30;
//                        spinnerFinHoraire.setVisibility(View.VISIBLE);
//                        radioQuinze.setEnabled(false);
//                        radioTrente.setEnabled(false);
//                        getSpinner(getCheckedRadio,intervalle);
//                        RelativeDayWeek.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//        });
//
//        radioGroupDayOrWeek.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                switch (checkedId){
//                    case R.id.radioAllDay:
//
//                        relativeEditDay.setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.radioOneDay:
//                        relativeEditDay.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//        });
//
//
//        mRefHoraire.child("Horaire/matin15").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Is better to use a List, because you don't know the size
//                // of the iterator returned by dataSnapshot.getChildren() to
//                // initialize the array
//                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                    String valueHours = areaSnapshot.getValue(String.class);
//                    hours.add(valueHours);
//                }
//
//                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
//                ArrayAdapter<String> hoursAdapter = new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, hours);
//                hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerDebutHoraire.setAdapter(hoursAdapter);
//
//
//                // ArrayAdapter<CharSequence> adapterSpinnerMatQuize = ArrayAdapter.createFromResource(this,
////                R.array.model_array, android.R.layout.simple_spinner_item);
////                adapterSpinnerMatQuize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                spinnerDebutHoraire.setAdapter(adapterSpinnerMatQuize);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getSpinner(int checkedRadio,int intervalle) {
//
//        switch (checkedRadio){
//            case R.id.radioMatin:
//                if (intervalle == 15){
//                   getHoraireSpinner(15,11,14,"14h00","matin15");
//                }else{
//                    getHoraireSpinner(30,11,14,"14h00","matin30");
//                }
//                break;
//            case R.id.radioSoir:
//                if (intervalle == 15){
//                    getHoraireSpinner(15,19,22,"22h00","soir15");
//                }else{
//                    getHoraireSpinner(30,19,22,"22h00","soir30");
//                }
//                break;
//            case R.id.radioPleinTemps:
//                if (intervalle == 15){
//                    //
//                }else{
//                    //
//                }
//                break;
//        }
//    }
//
//    private void getHoraireSpinner(int intervalle,int Hmin, int Hmax,String StatH,String whichHours){
//        nbrHours =0;
//        for (int j = Hmin; j <Hmax ; j++) {
//            for (int i = 0; i < 60; i = i +intervalle) {
//                if (i==0){
//                    String hSpinner = String .valueOf(j) +"h" + String .valueOf(i)+"0";
//                    matinQuinze.add(hSpinner);
//                    nbrHours++;
//                }else{
//                    String hSpinner = String .valueOf(j) +"h" + String .valueOf(i);
//                    matinQuinze.add(hSpinner);
//                    nbrHours++;
//                }
//            }
//        }
//        matinQuinze.add(StatH);
//       setValueSpinner(whichHours);
//    }
//
//    private void setValueSpinner(String child) {
//        mRefHoraire.child("Horaire/"+child).setValue(matinQuinze).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(HoraireActivity.this, "Horaire mise a jour", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

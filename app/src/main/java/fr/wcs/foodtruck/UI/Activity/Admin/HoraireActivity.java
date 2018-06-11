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
    private Spinner spinnerDebutMidiMinute, spinnerFinMidiMinute, spinnerDebutSoirMinute, spinnerFinSoirMinute, spinnerDebutMinute, spinnerFinMinute;
    private RelativeLayout relativeInter, relativeSpinner, relativeCheckD, relativePlageMatin,
            relativePlageMidi, relativePlageSoir;
    private Button horaireValide, ValidCheckHoraire, buttonCheckH;
    private CheckBox checkMatin, checkMidi, checkSoir;
    int intervalle, getCheckedRadio, nbrHours, heureDebut, heureFin;
    String getDay;
    int debutMatin, finMatin, debutMi, finMi, debutS, finS,idRadioDay,idRadioWeek;
    int debutMatinHeure, finMatinHeure, debutMiHeure, finMiHeure, debutSHeure, finSHeure;

    private ArrayList<String> quinze = new ArrayList<>();
    private ArrayList<String> etat = new ArrayList<>();
    private ArrayList<String> horaireFillMatin = new ArrayList<>();
    private ArrayList<String> horaireFillMidi = new ArrayList<>();
    private ArrayList<String> horaireFillSoir = new ArrayList<>();
    private ArrayList<String> jourSemaine = new ArrayList<>();
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

        initArray();

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
                        relativeInter.setVisibility(View.VISIBLE);
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

        radioGroupIntervalle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioQuinze:
                        intervalle = 15;
                        radioQuinze.setEnabled(false);
                        radioTrente.setEnabled(false);
                        relativeCheckD.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioTrente:
                        intervalle = 30;
                        relativeCheckD.setVisibility(View.VISIBLE);
                        radioQuinze.setEnabled(false);
                        radioTrente.setEnabled(false);
                }
            }
        });

        ValidCheckHoraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMatin.isChecked()) {
                    relativePlageMatin.setVisibility(View.VISIBLE);
                    buttonCheckH.setVisibility(View.VISIBLE);
                    checkMatin.setEnabled(false);
                    checkMidi.setEnabled(false);
                    checkSoir.setEnabled(false);
                    etat.add("matin");
                    setSpinnerIntervalle(spinnerDebutMinute);
                    setSpinnerIntervalle(spinnerFinMinute);
                    getHeure("matin", getDay);
                } else {
                    relativePlageMatin.setVisibility(View.GONE);
                }
                if (checkMidi.isChecked()) {
                    relativePlageMidi.setVisibility(View.VISIBLE);
                    buttonCheckH.setVisibility(View.VISIBLE);
                    checkMatin.setEnabled(false);
                    checkMidi.setEnabled(false);
                    checkSoir.setEnabled(false);
                    etat.add("midi");
                    setSpinnerIntervalle(spinnerDebutMidiMinute);
                    setSpinnerIntervalle(spinnerFinMidiMinute);
                    getHeure("midi", getDay);
                } else {
                    relativePlageMidi.setVisibility(View.GONE);
                }
                if (checkSoir.isChecked()) {
                    relativePlageSoir.setVisibility(View.VISIBLE);
                    buttonCheckH.setVisibility(View.VISIBLE);
                    checkMatin.setEnabled(false);
                    checkMidi.setEnabled(false);
                    checkSoir.setEnabled(false);
                    etat.add("soir");
                    setSpinnerIntervalle(spinnerDebutSoirMinute);
                    setSpinnerIntervalle(spinnerFinSoirMinute);
                    getHeure("soir", getDay);
                } else {
                    relativePlageSoir.setVisibility(View.GONE);
                }
                if (!checkMatin.isChecked() && !checkMidi.isChecked() && !checkSoir.isChecked()) {
                    Toast.makeText(HoraireActivity.this, "Veuillez cocher une/des propositions", Toast.LENGTH_SHORT).show();
                    buttonCheckH.setVisibility(View.GONE);
                } else {
                    buttonCheckH.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initArray() {
        quinze.add("minute");
        quinze.add("00");
        quinze.add("15");
        quinze.add("30");
        quinze.add("45");

        jourSemaine.add("Lundi");
        jourSemaine.add("Mardi");
        jourSemaine.add("Mercredi");
        jourSemaine.add("Jeudi");
        jourSemaine.add("Vendredi");
        jourSemaine.add("Samedi");
        jourSemaine.add("Dimanche");
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
                for (int i = 0; i < etat.size(); i++) {
                    if (etat.get(i).equals("matin")){
                        if (EditTDebut.getText().toString().isEmpty() || EditTFin.getText().toString().isEmpty()) {
                            Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                        } else {
                            debutMatinHeure = Integer.parseInt(EditTDebut.getText().toString());
                            finMatinHeure = Integer.parseInt(EditTFin.getText().toString());
                            setEnableHoraire();
                            getPlanning(debutMatinHeure, debutMatin, finMatinHeure, finMatin, "matin");
                        }
                    }if (etat.get(i).equals("midi")){
                        if (EditTDebutMidi.getText().toString().isEmpty() || EditTFinMidi.getText().toString().isEmpty()) {
                            Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                        } else {
                            debutMiHeure = Integer.parseInt(EditTDebutMidi.getText().toString());
                            finMiHeure = Integer.parseInt(EditTFinMidi.getText().toString());
                            setEnableHoraire();
                            getPlanning(debutMiHeure, debutMi, finMiHeure, finMi, "midi");
                        }
                    }if (etat.get(i).equals("soir")){
                        if (EditTDebutSoir.getText().toString().isEmpty() || EditTFinSoir.getText().toString().isEmpty()) {
                            Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                        } else {
                            debutSHeure = Integer.parseInt(EditTDebutSoir.getText().toString());
                            finSHeure = Integer.parseInt(EditTFinSoir.getText().toString());
                            setEnableHoraire();
                            getPlanning(debutSHeure, debutS, finSHeure, finMatin, "soir");
                        }
                    }
                }
                for (int i = 0; i < etat.size(); i++) {
                    if (radioJour.isChecked()){
                        removeHours(getDay);
                        if (horaireFillMatin.size() > 0 && radioJour.isChecked()) {
                            setValuePlanning("Matin", horaireFillMatin, getDay,radioSemaine);
                        }
                        if (horaireFillMidi.size() > 0 && radioJour.isChecked()) {
                            setValuePlanning("Midi", horaireFillMidi, getDay,radioSemaine);
                        }
                        if (horaireFillSoir.size() > 0 && radioJour.isChecked()) {
                            setValuePlanning("Soir", horaireFillSoir, getDay, radioSemaine);
                        }
                    }else{
                        removeWeek("Horaire/");
                        if (horaireFillMatin.size() > 0) {
                            setValuePlanningWeek("Matin",horaireFillMatin, jourSemaine);
                        }
                        if (horaireFillMidi.size() > 0 ) {
                            setValuePlanningWeek("Midi",horaireFillMidi, jourSemaine);
                        }
                        if (horaireFillSoir.size() > 0 ) {
                            setValuePlanningWeek("Soir", horaireFillSoir,jourSemaine);
                        }
                    }
                }
            }
        });

//        horaireValide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void removeWeek(String child) {
        mRefHoraire.child(child).removeValue();

    }

    private void setValuePlanningWeek(String child, ArrayList<String> etat,ArrayList<String> jourSemaine) {

        for (int i = 0; i <jourSemaine.size(); i++) {
            mRefHoraire.child("Horaire/" + jourSemaine.get(i) + "/" + child).setValue(etat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(HoraireActivity.this, "Horaire mise a jour", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getPlanning(int debutSHeure, int debutS, int finSHeure, int finMatin, String etat) {

        switch (etat) {
            case "matin":
                getHoraireSpinner(debutSHeure, debutS, finSHeure, finMatin, horaireFillMatin, intervalle);
                break;
            case "midi":
                getHoraireSpinner(debutSHeure, debutS, finSHeure, finMatin, horaireFillMidi, intervalle);
                break;
            case "soir":
                getHoraireSpinner(debutSHeure, debutS, finSHeure, finMatin, horaireFillSoir, intervalle);
                break;
        }
    }

    private void setSpinnerIntervalle(Spinner spinnerH) {

        ArrayAdapter<String> adapterDebutMin = new ArrayAdapter<String>(HoraireActivity.this, android.R.layout.simple_spinner_item, quinze);
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
                        } else if (spinnerH.getId() == R.id.spinnerDebutSoirMinute) {
                            debutS = 0;
                        } else if (spinnerH.getId() == R.id.spinnerTFinMinute) {
                            finMatin = 0;
                        } else if (spinnerH.getId() == R.id.spinnerFinMidiMinute) {
                            finMi = 0;
                        } else if (spinnerH.getId() == R.id.spinnerFinSoirMinute) {
                            finS = 0;
                        }
                        break;
                    case 2:
                        if (spinnerH.getId() == R.id.spinnerDebutMinute) {
                            debutMatin = 15;
                        } else if (spinnerH.getId() == R.id.spinnerDebutMidiMinute) {
                            debutMi = 15;
                        } else if (spinnerH.getId() == R.id.spinnerDebutSoirMinute) {
                            debutS = 15;
                        } else if (spinnerH.getId() == R.id.spinnerTFinMinute) {
                            finMatin = 15;
                        } else if (spinnerH.getId() == R.id.spinnerFinMidiMinute) {
                            finMi = 15;
                        } else if (spinnerH.getId() == R.id.spinnerFinSoirMinute) {
                            finS = 15;
                        }
                        break;
                    case 3:
                        if (spinnerH.getId() == R.id.spinnerDebutMinute) {
                            debutMatin = 30;
                        } else if (spinnerH.getId() == R.id.spinnerDebutMidiMinute) {
                            debutMi = 30;
                        } else if (spinnerH.getId() == R.id.spinnerDebutSoirMinute) {
                            debutS = 30;
                        } else if (spinnerH.getId() == R.id.spinnerTFinMinute) {
                            finMatin = 30;
                        } else if (spinnerH.getId() == R.id.spinnerFinMidiMinute) {
                            finMi = 30;
                        } else if (spinnerH.getId() == R.id.spinnerFinSoirMinute) {
                            finS = 30;
                        }
                        break;
                    case 4:
                        if (spinnerH.getId() == R.id.spinnerDebutMinute) {
                            debutMatin = 45;
                        } else if (spinnerH.getId() == R.id.spinnerDebutMidiMinute) {
                            debutMi = 45;
                        } else if (spinnerH.getId() == R.id.spinnerDebutSoirMinute) {
                            debutS = 45;
                        } else if (spinnerH.getId() == R.id.spinnerTFinMinute) {
                            finMatin = 45;
                        } else if (spinnerH.getId() == R.id.spinnerFinMidiMinute) {
                            finMi = 45;
                        } else if (spinnerH.getId() == R.id.spinnerFinSoirMinute) {
                            finS = 45;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void afficherJour() {
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
                switch (position) {
                    case 1:
                        getDay = "Lundi";
                        if (getDay != null) {
                            relativeInter.setVisibility(View.VISIBLE);
                        }
                        spinnerJour.setEnabled(false);
                        break;
                    case 2:
                        getDay = "Mardi";
                        if (getDay != null) {
                            relativeInter.setVisibility(View.VISIBLE);
                        }
                        spinnerJour.setEnabled(false);
                        break;
                    case 3:
                        getDay = "Mercredi";
                        if (getDay != null) {
                            relativeInter.setVisibility(View.VISIBLE);
                        }
                        spinnerJour.setEnabled(false);
                        break;
                    case 4:
                        getDay = "Jeudi";
                        if (getDay != null) {
                            relativeInter.setVisibility(View.VISIBLE);
                        }
                        spinnerJour.setEnabled(false);
                        break;
                    case 5:
                        getDay = "Vendredi";
                        if (getDay != null) {
                            relativeInter.setVisibility(View.VISIBLE);
                        }
                        spinnerJour.setEnabled(false);
                        break;
                    case 6:
                        getDay = "Samedi";
                        if (getDay != null) {
                            relativeInter.setVisibility(View.VISIBLE);
                        }
                        spinnerJour.setEnabled(false);
                        break;
                    case 7:
                        getDay = "Dimanche";
                        if (getDay != null) {
                            relativeInter.setVisibility(View.VISIBLE);
                        }
                        spinnerJour.setEnabled(false);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getHoraireSpinner(int debutHeure, int debutMin, int finHeure, int finMin, ArrayList<String> horaireFill, int intervalle) {
        int realDebut = debutHeure;
        int diffIntervalleDebut45 = debutMin - intervalle;
        int heurePlus =0;
        int minutePlus=0;
        int minDebutzero = debutMin;

        int checkMinDebut= debutMin;
        int checkMinFin= finMin;
        int checkHeureFin= finHeure;

        if (intervalle==30){
            for (int i = debutHeure; i <=finHeure ; i++) {
                if (checkMinDebut == 0) {
                    if (i == checkHeureFin) {
                        horaireFill.add(String.valueOf(checkHeureFin + "h00"));
                        if (checkMinFin == 30 || checkMinFin==45) {
                            horaireFill.add(String.valueOf(checkHeureFin + "h30"));
                            if (checkMinFin==45){
                                horaireFill.add(String.valueOf(checkHeureFin + "h45"));
                            }
                        }
                    } else {
                        for (int j = debutMin; j < 60; j = j + intervalle) {
                            if (j == 0) {
                                String zero = String.valueOf(i + "h00");
                                horaireFill.add(zero);
                            } else {
                                String trente = String.valueOf(i + "h" + intervalle);
                                horaireFill.add(trente);
                            }
                        }
                    }
                } else if (checkMinDebut == 30) {
                    if (i == checkHeureFin) {
                        horaireFill.add(String.valueOf(checkHeureFin + "h00"));
                        if (checkMinFin == 30 || checkMinFin==45) {
                            horaireFill.add(String.valueOf(checkHeureFin + "h30"));
                            if (checkMinFin==45){
                                horaireFill.add(String.valueOf(checkHeureFin + "h45"));
                            }
                        }else{
                            horaireFill.add(String.valueOf(checkHeureFin + "h15"));
                        }
                    } else {
                        for (int j = debutMin; j < 60; j = j + intervalle) {
                            if (j == 0) {
                                String zero = String.valueOf(i + "h00");
                                horaireFill.add(zero);
                            } else {
                                String trente = String.valueOf(i + "h" + intervalle);
                                horaireFill.add(trente);
                                debutMin = 0;
                            }
                        }
                    }
                } else if (checkMinDebut == 15) {
                    if (i == checkHeureFin) {
                        horaireFill.add(String.valueOf(checkHeureFin + "h15"));
                        if (checkMinFin == 45) {
                            horaireFill.add(String.valueOf(checkHeureFin + "h45"));
                        }if(checkMinFin==30){
                            horaireFill.add(String.valueOf(checkHeureFin + "h30"));
                        }else {
                            horaireFill.add(String.valueOf(checkHeureFin+ "h45"));
                        }
                    } else {
                        for (int j = debutMin; j < 60; j = j + intervalle) {
                            if (j == 15) {
                                String quinze = String.valueOf(i + "h" + j);
                                horaireFill.add(quinze);
                            } else {
                                String trente = String.valueOf(i + "h" + j);
                                horaireFill.add(trente);
                                debutMin = 15;
                            }
                        }
                    }
                } else if (checkMinDebut == 45) {
                    if (i == checkHeureFin) {
                        horaireFill.add(String.valueOf(checkHeureFin + "h15"));
                        if (checkMinFin == 45) {
                            horaireFill.add(String.valueOf(checkHeureFin + "h45"));
                        }else if(checkMinFin==30){
                            horaireFill.add(String.valueOf(checkHeureFin + "h30"));
                        }else {
                            horaireFill.add(String.valueOf(checkHeureFin+ "h45"));
                        }
                    } else {
                        for (int j = debutMin; j < 60; j = j + intervalle) {
                            if (j == 45) {
                                String quinze = String.valueOf(i + "h" + j);
                                horaireFill.add(quinze);
                                debutMin = 15;
                            } else {
                                String trente = String.valueOf(i + "h15");
                                horaireFill.add(trente);
                            }
                        }
                    }
                }
            }
        //Intervalle 15Min
        }else{
                for (int i = debutHeure; i <= finHeure; i++) {
                    if (i == finHeure) {
                        debutMin = 0;
                        for (int j = debutMin; j <= finMin; j = j + intervalle) {
                            if (j == 0) {
                                String hSpinner = String.valueOf(i) + "h" + String.valueOf(j) + "0";
                                horaireFill.add(hSpinner);
                            } else {
                                String hSpinner = String.valueOf(i) + "h" + String.valueOf(j);
                                horaireFill.add(hSpinner);
                            }
                        }
                    } else {
                        if (i != realDebut) {
                            debutMin = 0;
                            for (int j = debutMin; j < 60; j = j + intervalle) {
                                if (j == 0) {
                                    String hSpinner = String.valueOf(i) + "h" + String.valueOf(j) + "0";
                                    horaireFill.add(hSpinner);
                                } else {
                                    String hSpinner = String.valueOf(i) + "h" + String.valueOf(j);
                                    horaireFill.add(hSpinner);
                                }
                            }
                        }else{
                            for (int j = debutMin; j < 60; j = j + intervalle) {
                                if (j == 0) {
                                    String hSpinner = String.valueOf(i) + "h" + String.valueOf(j) + "0";
                                    horaireFill.add(hSpinner);
                                }else {
                                    String hSpinner = String.valueOf(i) + "h" + String.valueOf(j);
                                    horaireFill.add(hSpinner);
                                }
                            }
                        }
                    }
                }
            }
//        horaireValide.setVisibility(View.VISIBLE);
    }

    private void removeHours(String getDay) {
        mRefHoraire.child("Horaire/" + getDay + "/").removeValue();
    }

    private void setValuePlanning(String child, ArrayList<String> etat, String getDay,RadioButton checkWeek) {

        if (getDay != null) {
            mRefHoraire.child("Horaire/" + getDay + "/" + child).setValue(etat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(HoraireActivity.this, "Horaire mise a jour", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setEnableHoraire(){

        EditTDebut.setEnabled(false);
        EditTFin.setEnabled(false);
        EditTFinMidi.setEnabled(false);
        EditTDebutMidi.setEnabled(false);
        EditTDebutSoir.setEnabled(false);
        EditTFinSoir.setEnabled(false);
        buttonCheckH.setEnabled(false);
        spinnerDebutMidiMinute.setEnabled(false);
        spinnerFinMidiMinute.setEnabled(false);
        spinnerDebutSoirMinute.setEnabled(false);
        spinnerFinSoirMinute.setEnabled(false);
        spinnerDebutMinute.setEnabled(false);
        spinnerFinMinute.setEnabled(false);
    }
}
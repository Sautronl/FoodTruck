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
    int debutMatin, finMatin, debutMi, finMi, debutS, finS;
    int debutMatinHeure, finMatinHeure, debutMiHeure, finMiHeure, debutSHeure, finSHeure;

    private ArrayList<String> quinze = new ArrayList<>();
    private ArrayList<String> etat = new ArrayList<>();
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
        quinze.add("45");

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
                            getPlanning(debutMatinHeure, debutMatin, finMatinHeure, finMatin, "matin");
                        }
                    }if (etat.get(i).equals("midi")){
                        if (EditTDebutMidi.getText().toString().isEmpty() || EditTFinMidi.getText().toString().isEmpty()) {
                            Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                        } else {
                            debutMiHeure = Integer.parseInt(EditTDebutMidi.getText().toString());
                            finMiHeure = Integer.parseInt(EditTFinMidi.getText().toString());
                            getPlanning(debutMiHeure, debutMi, finMiHeure, finMi, "midi");
                        }
                    }if (etat.get(i).equals("soir")){
                        if (EditTDebutSoir.getText().toString().isEmpty() || EditTFinSoir.getText().toString().isEmpty()) {
                            Toast.makeText(HoraireActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                        } else {
                            debutSHeure = Integer.parseInt(EditTDebutSoir.getText().toString());
                            finSHeure = Integer.parseInt(EditTFinSoir.getText().toString());
                            getPlanning(debutSHeure, debutS, finSHeure, finMatin, "soir");
                        }
                    }
                }
            }
        });

        horaireValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < etat.size(); i++) {
                    if (horaireFillMatin.size() > 0 && radioJour.isChecked()) {
                        setValuePlanning("Matin", horaireFillMatin, getDay);
                    }
                    if (horaireFillMidi.size() > 0 && radioJour.isChecked()) {
                        setValuePlanning("Midi", horaireFillMidi, getDay);
                    }
                    if (horaireFillSoir.size() > 0 && radioJour.isChecked()) {
                        setValuePlanning("Soir", horaireFillSoir, getDay);
                    }
                }
            }
        });
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

    private void getHoraireSpinner(int debutSHeure, int debutS, int finSHeure, int finMatin, ArrayList<String> horaireFill, int intervalle) {
        for (int i = debutSHeure; i <= finSHeure; i++) {
            if (i == finSHeure) {
                debutS = 0;
                for (int j = debutS; j <= finMatin; j = j + intervalle) {
                    if (j == 0) {
                        String hSpinner = String.valueOf(i) + "h" + String.valueOf(j) + "0";
                        horaireFill.add(hSpinner);
                    } else {
                        String hSpinner = String.valueOf(i) + "h" + String.valueOf(j);
                        horaireFill.add(hSpinner);
                    }
                }
            } else {
                for (int j = debutS; j < 60; j = j + intervalle) {
                    if (j == 0) {
                        String hSpinner = String.valueOf(i) + "h" + String.valueOf(j) + "0";
                        horaireFill.add(hSpinner);
                    } else {
                        String hSpinner = String.valueOf(i) + "h" + String.valueOf(j);
                        horaireFill.add(hSpinner);
                    }
                }
            }
        }
        horaireValide.setVisibility(View.VISIBLE);
    }

    private void setValuePlanning(String child, ArrayList<String> etat, String getDay) {
        if (getDay != null) {
            mRefHoraire.child("Horaire/" + getDay + "/" + child).setValue(etat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(HoraireActivity.this, "Horaire mise a jour", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
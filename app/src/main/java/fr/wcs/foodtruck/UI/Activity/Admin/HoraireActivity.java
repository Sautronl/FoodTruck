package fr.wcs.foodtruck.UI.Activity.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import fr.wcs.foodtruck.R;

public class HoraireActivity extends AppCompatActivity {

    private RadioGroup radioGroupHoraire,radioGroupIntervalle,radioGroupDayOrWeek;
    private RadioButton radioMatin,radioSoir,radioPeinTemps,radioQuinze,radioTrente,radioAllDay,radioOneDay;
    private Spinner spinnerDebutHoraire,spinnerFinHoraire;
    private EditText choixJourHoraire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaire);

        radioGroupDayOrWeek = (RadioGroup) findViewById(R.id.radioGroupDayOrWeek);
        radioGroupHoraire = (RadioGroup) findViewById(R.id.radioGroupHoraire);
        radioGroupIntervalle = (RadioGroup) findViewById(R.id.radioGroupIntervalle);
        choixJourHoraire = (EditText) findViewById(R.id.choixJourHoraire);
        spinnerDebutHoraire = (Spinner) findViewById(R.id.spinnerDebutHoraire);
        spinnerFinHoraire = (Spinner) findViewById(R.id.spinnerFinHoraire);
        radioMatin =(RadioButton) findViewById(R.id.radioMatin);
        radioSoir =(RadioButton) findViewById(R.id.radioSoir);
        radioPeinTemps =(RadioButton) findViewById(R.id.radioPleinTemps);
        radioQuinze =(RadioButton) findViewById(R.id.radioQuinze);
        radioTrente =(RadioButton) findViewById(R.id.radioTrente);
        radioAllDay =(RadioButton) findViewById(R.id.radioAllDay);
        radioOneDay =(RadioButton) findViewById(R.id.radioOneDay);
    }
}

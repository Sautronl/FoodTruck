package fr.wcs.foodtruck.UI.Activity.Admin;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import fr.wcs.foodtruck.R;

public class HoraireActivity extends AppCompatActivity {

    private RadioGroup radioGroupHoraire,radioGroupIntervalle,radioGroupDayOrWeek;
    private RadioButton radioMatin,radioSoir,radioPeinTemps,radioQuinze,radioTrente,radioAllDay,radioOneDay;
    private Spinner spinnerDebutHoraire,spinnerFinHoraire;
    private EditText choixJourHoraire;
    private RelativeLayout RelativeIntervalle,relativeSpinner,RelativeDayWeek,relativeEditDay;

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
        RelativeIntervalle = (RelativeLayout) findViewById(R.id.RelativeIntervalle);
        relativeSpinner = (RelativeLayout)findViewById(R.id.relativeSpinner);
        RelativeDayWeek = (RelativeLayout)findViewById(R.id.RelativeDayWeek);
        relativeEditDay = (RelativeLayout)findViewById(R.id.relativeEditDay);

        radioGroupHoraire.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioMatin:
                        relativeSpinner.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioSoir:
                        relativeSpinner.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioPleinTemps:
                        relativeSpinner.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}

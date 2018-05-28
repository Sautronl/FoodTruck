package fr.wcs.foodtruck.UI.Activity.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.R;

public class ResumeActivity extends AppCompatActivity {

    ListView MenuChoisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

//        MenuChoisi = (ListView) findViewById(R.id.MenuChoisi);
        Bundle b = getIntent().getBundleExtra("bundle");
//        b = getIntent().getBundleExtra("bundle");
//        MajPlatDuJour name = b.getParcelable("bundle");
    }
}

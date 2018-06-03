package fr.wcs.foodtruck.UI.Activity.User;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fr.wcs.foodtruck.Model.ReservationModels;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Model.RemerciementModel;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

public class RemerciementCommande extends AppCompatActivity {

    TextView burgerThanks,boissonThanks,dessertThanks;

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remerciement_commande);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();


        ReservationModels reservDetail = bundle.getParcelable("detailReservation");
//        String heure = getIntent().getStringExtra("heure");

//        RemerciementCommande.this.setTitle(nom);

        ScrollView merciLayout =(ScrollView) findViewById(R.id.scrollThanks);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(merciLayout,mainfont);

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(RemerciementCommande.this, DrawActivity.class);
                startActivity(back);
                finish();
            }
        });

        final TextView tvRemerciementN = (TextView) findViewById(R.id.tvRemerciementNom);
        final TextView tvHeure = (TextView) findViewById(R.id.remerciementHeure);
        burgerThanks = (TextView) findViewById(R.id.burgerThanks);
        boissonThanks = (TextView) findViewById(R.id.boissonThanks);
        dessertThanks = (TextView) findViewById(R.id.dessertThanks);



//        RemerciementModel rem = new RemerciementModel(nom,heure);
        tvRemerciementN.setText(reservDetail.getNomReserv());
        tvHeure.setText(reservDetail.getHoraire());
        burgerThanks.setText(reservDetail.getNomBurger());
        boissonThanks.setText(reservDetail.getChoixBoisson());
        dessertThanks.setText(reservDetail.getChoixDessert());
    }
}

package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminAccueil extends AppCompatActivity {

    private Button mDeco;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_accueil);

        ImageView menu = (ImageView) findViewById(R.id.menu);
        ImageView lieu = (ImageView) findViewById(R.id.lieu);
        ImageView event = (ImageView) findViewById(R.id.event);
        ImageView contact = (ImageView) findViewById(R.id.contact);
        mDeco = (Button) findViewById(R.id.deconnect);

        mAuth = FirebaseAuth.getInstance();

        final Intent disconnect = new Intent(AdminAccueil.this, MainActivity.class);
        mDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(disconnect);
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, AdminMenuDuJour.class);
                startActivity(intent);
            }
        });

        /*lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, Commande.class);
                startActivity(intent);
            }
        });

*/
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, AdminEvent.class);
                startActivity(intent);
            }
        });
/*
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, ContactPrivatisation.class);
                startActivity(intent);
            }
        });*/
    }
}

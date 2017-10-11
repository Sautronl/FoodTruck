package fr.wcs.foodtruck;

import android.app.Presentation;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private long timeElapsed = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView menu = (ImageView) findViewById(R.id.menu);
        ImageView lieu = (ImageView) findViewById(R.id.lieu);
        ImageView presentation = (ImageView) findViewById(R.id.presentation);
        ImageView event = (ImageView) findViewById(R.id.event);
        ImageView contact = (ImageView) findViewById(R.id.contact);
        final ImageView logo = (ImageView) findViewById(R.id.logo);

       logo.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               switch (motionEvent.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                       timeElapsed = motionEvent.getDownTime();
                       break;
                   case MotionEvent.ACTION_UP:
                       timeElapsed = motionEvent.getEventTime() - timeElapsed;
                       if (timeElapsed >= 10000){
                           Intent admin = new Intent(MainActivity.this, AdminActivity.class);
                           startActivity(admin);
                       }
                       break;
                    }
               return true;
           }
       });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuDuJourActivity.class);
                startActivity(intent);
            }
        });

        lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Commande.class);
                startActivity(intent);
            }
        });

        presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, fr.wcs.foodtruck.Presentation.class);
                startActivity(intent);
            }
        });


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactPrivatisation.class);
                startActivity(intent);
            }
        });

        lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Emplacement.class);
                startActivity(intent);
            }
        });

    }
}

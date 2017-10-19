package fr.wcs.foodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long timeElapsed = 0L;
    private int mBackButtonCount = 0;


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

        // Start Service
        Intent serviceIntent = new Intent(this,NotificationService.class);
        startService(serviceIntent);

       logo.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               switch (motionEvent.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                       timeElapsed = motionEvent.getDownTime();
                       break;
                   case MotionEvent.ACTION_UP:
                       timeElapsed = motionEvent.getEventTime() - timeElapsed;
                       if (timeElapsed >= 1000){
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

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
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
    @Override
    protected void onResume() {
        super.onResume();
        mBackButtonCount = 0;
    }

    @Override
    public void onBackPressed() {
        if(mBackButtonCount > 0) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "On va se calmer", Toast.LENGTH_SHORT).show();
        }
    }
}

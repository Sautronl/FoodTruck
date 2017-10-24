package fr.wcs.foodtruck;

import android.*;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long timeElapsed = 0L;
    private int mBackButtonCount = 0;
    private PackageManager pm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView menu = (ImageView) findViewById(R.id.menu);
        ImageView lieu = (ImageView) findViewById(R.id.lieu);
        ImageView presentation = (ImageView) findViewById(R.id.presentation);
        ImageView event = (ImageView) findViewById(R.id.event);
        ImageView contact = (ImageView) findViewById(R.id.contact);
        ImageView facebook = (ImageView) findViewById(R.id.facebookLogo);
        final ImageView logo = (ImageView) findViewById(R.id.logo);

        // Start Service
        Intent serviceIntent = new Intent(this, NotificationService.class);
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
                        if (timeElapsed >= 1000) {
                            Intent admin = new Intent(MainActivity.this, AdminActivity.class);
                            startActivity(admin);
                        }
                        break;
                }
                return true;
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFacebookIntent(getPackageManager(), "https://www.facebook.com/sautron.laurent");
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuDuJourActivity.class);
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
                Intent intent = new Intent(MainActivity.this, ListEmplacement.class);
                startActivity(intent);
            }
        });
    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBackButtonCount = 0;
    }

    @Override
    public void onBackPressed() {
        if (mBackButtonCount > 0) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "On va se calmer", Toast.LENGTH_SHORT).show();
        }
    }
}

package fr.wcs.foodtruck.UI.Activity.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.Calendar;

import fr.wcs.foodtruck.Utils.CloseDay;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Activity.Admin.AdminActivity;

public class MainActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {

    private long timeElapsed = 0L;
    private int mBackButtonCount = 0;
    private Calendar mCalendar;
    private SliderLayout mSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView menu = (ImageView) findViewById(R.id.menu);
        ImageView lieu = (ImageView) findViewById(R.id.lieu);
        ImageView presentation = (ImageView) findViewById(R.id.presentation);
        ImageView event = (ImageView) findViewById(R.id.event);
        ImageView contact = (ImageView) findViewById(R.id.contact);
        ImageView like = (ImageView) findViewById(R.id.like);
        mSlide = findViewById(R.id.slider);
        final ImageView logo = (ImageView) findViewById(R.id.logo);

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add("https://www.revive-adserver.com/media/GitHub.jpg");
        listName.add("JPG - Github");

        listUrl.add("https://tctechcrunch2011.files.wordpress.com/2017/02/android-studio-logo.png");
        listName.add("PNG - Android Studio");

        listUrl.add("http://static.tumblr.com/7650edd3fb8f7f2287d79a67b5fec211/3mg2skq/3bdn278j2/tumblr_static_idk_what.gif");
        listName.add("GIF - Disney");

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(listUrl.get(i));
            mSlide.addSlider(textSliderView);
        }
        mSlide.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlide.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlide.setDuration(3000);
        mSlide.addOnPageChangeListener(MainActivity.this);



        menu.setOnTouchListener(new View.OnTouchListener() {
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

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FACEBOOK_URL = "https://www.facebook.com/samdonnefaim/";
                String FACEBOOK_PAGE_ID = "samdonnefaim";
                String lien;
                PackageManager packageManager = MainActivity.this.getPackageManager();
                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) { //newer versions of fb app
                        lien = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                    } else { //older versions of fb app
                        lien = "fb://page/" + FACEBOOK_PAGE_ID;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    lien = FACEBOOK_URL;
                }
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                facebookIntent.setData(Uri.parse(lien));
                startActivity(facebookIntent);
            }
        });

        mCalendar = Calendar.getInstance();
        final int daysatsun = mCalendar.get(Calendar.DAY_OF_WEEK);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysatsun == 7 || daysatsun == 1) {
                    Intent intentClose = new Intent(MainActivity.this, CloseDay.class);
                    startActivity(intentClose);
                } else {
                    Intent intent = new Intent(MainActivity.this, MenuDuJourActivity.class);
                    startActivity(intent);
                }
            }
        });


        presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Presentation.class);
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
            new AlertDialog.Builder(this)
                    .setTitle("Quitter Sam'donne faim")
                    .setMessage("Etes-vous sûr de vouloir quitter l'app?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mBackButtonCount++;
                            onBackPressed();
                        }
                    }).setNegativeButton("Non", null).show();
        }
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlide.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

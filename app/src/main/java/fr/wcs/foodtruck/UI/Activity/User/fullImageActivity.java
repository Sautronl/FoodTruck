package fr.wcs.foodtruck.UI.Activity.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.UI.Adapter.GalleryAdapter;
import fr.wcs.foodtruck.R;

public class fullImageActivity extends AppCompatActivity {

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private Calendar myCalendar;
    private ImageView imageView;
     private MajPlatDuJour mImgFull = null;
     private ArrayList<MajPlatDuJour> majFull = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

         imageView = (ImageView)findViewById(R.id.fullImg);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        myCalendar = Calendar.getInstance();
        int dayD = myCalendar.get(Calendar.DAY_OF_WEEK);

        switch (dayD){
            case 2:
                mRef = mRef.child("menu/menuLundi/");
                getFullImg();
                break;
            case 3:
                mRef = mRef.child("menu/menuMardi/");
                getFullImg();
                break;
            case 4:
                mRef = mRef.child("menu/menuMercredi/");
                getFullImg();
                break;
            case 5:
                mRef = mRef.child("menu/menuJeudi/");
                getFullImg();
                break;
            case 6:
                mRef = mRef.child("menu/menuVendredi/");
                getFullImg();
                break;
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.6));


    }

    protected void getFullImg(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mImgFull = dataSnapshot.getValue(MajPlatDuJour.class);
                majFull.add(mImgFull);
                if (mImgFull != null) {
                    Glide.with(fullImageActivity.this).load(mImgFull.getUrlImg()).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

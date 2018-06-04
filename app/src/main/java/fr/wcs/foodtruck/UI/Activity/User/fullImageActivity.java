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
import uk.co.senab.photoview.PhotoViewAttacher;

public class fullImageActivity extends AppCompatActivity {

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private Calendar myCalendar;
    private ImageView imageView;
     private MajPlatDuJour mImgFull = null;
     private ArrayList<MajPlatDuJour> majFull = new ArrayList<>();
     PhotoViewAttacher pAttach;

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
                getFullImg("menu/menuLundi/");
                break;
            case 3:
                getFullImg("menu/menuMardi/");
                break;
            case 4:
                getFullImg("menu/menuMercredi/");
                break;
            case 5:
                getFullImg("menu/menuJeudi/");
                break;
            case 6:
                getFullImg("menu/menuVendredi/");
                break;
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.6));

        pAttach = new PhotoViewAttacher(imageView);
        pAttach.update();
    }

    protected void getFullImg(String child){
        mRef.child(child).addValueEventListener(new ValueEventListener() {
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

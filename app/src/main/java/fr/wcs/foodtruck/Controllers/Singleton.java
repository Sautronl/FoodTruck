package fr.wcs.foodtruck.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.foodtruck.Model.MajPlatDuJour;

/**
 * Created by apprenti on 16/01/18.
 */

public class Singleton {

    private static Singleton sInstance;
    private FirebaseDatabase mFire;
     DatabaseReference mRef;
    private MajPlatDuJour majPlatDuJour;

    private Singleton(){
        mFire = FirebaseDatabase.getInstance();
    }

    public static Singleton getsInstance(){
        if (sInstance == null){
            synchronized (Singleton.class) {
                if (sInstance == null) {
                    sInstance = new Singleton();
                }
            }
        }
        return sInstance;
    }

    public void loadMenu(String jour, final TextView nom, final TextView desc, final Button prix, final ImageView img, final Activity activity, final ProgressDialog dialog) {

        mRef = mFire.getReference("menu/");
        mRef.child(jour).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                majPlatDuJour = dataSnapshot.getValue(MajPlatDuJour.class);
                nom.setText(majPlatDuJour.getNomPlat());
                desc.setText(majPlatDuJour.getDescPlat());
                Glide.with(activity).load(majPlatDuJour.getUrlImg()).into(img);
                prix.setText("Prix\n" + majPlatDuJour.getPrix());
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

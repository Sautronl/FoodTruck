package fr.wcs.foodtruck.UI.Activity.Admin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import fr.wcs.foodtruck.Model.SliderModel;
import fr.wcs.foodtruck.R;

public class AdminSliderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mSlide1,mSlide2,mSlide3;
    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private StorageReference mStorage;
    private final static int REQUEST_CODE = 111;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_slider);

        mSlide1 = (ImageView) findViewById(R.id.slide1);
        mSlide2 = (ImageView) findViewById(R.id.slide2);
        mSlide3 = (ImageView) findViewById(R.id.slide3);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        mSlide1.setOnClickListener(this);
        mSlide2.setOnClickListener(this);
        mSlide3.setOnClickListener(this);
        addImgSlider(1);
        addImgSlider(2);
        addImgSlider(3);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case (R.id.slide1):
                x = 1;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selectionner une image"), REQUEST_CODE);
                break;
            case (R.id.slide2):
                x = 2;
                Intent j = new Intent();
                j.setType("image/*");
                j.setAction(j.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(j, "Selectionner une image"), REQUEST_CODE);
                break;
            case (R.id.slide3):
                x = 3;
                Intent k = new Intent();
                k.setType("image/*");
                k.setAction(k.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(k, "Selectionner une image"), REQUEST_CODE);
                break;
        }
    }

    private void addImgSlider(final int x) {
        mRef.child("Test" + x + "/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String slideM = dataSnapshot.getValue(String.class);
                if (x == 1){
                    Glide.with(AdminSliderActivity.this).load(slideM).into(mSlide1);
                }else if (x == 2){
                    Glide.with(AdminSliderActivity.this).load(slideM).into(mSlide2);
                }else{
                    Glide.with(AdminSliderActivity.this).load(slideM).into(mSlide3);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE && resultCode == RESULT_OK) && data != null && data.getData() != null) {

            Uri sliderUri = data.getData();

            StorageReference refStorage = mStorage.child("Test" + x + "/").child(sliderUri.getLastPathSegment());
            refStorage.putFile(sliderUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String slideMod = taskSnapshot.getDownloadUrl().toString();
                    mRef.child("Test" + x + "/").setValue(slideMod);
                    if (x == 1){
                        Glide.with(AdminSliderActivity.this).load(slideMod).into(mSlide1);
                    }else if (x == 2){
                        Glide.with(AdminSliderActivity.this).load(slideMod).into(mSlide2);
                    }else{
                        Glide.with(AdminSliderActivity.this).load(slideMod).into(mSlide3);
                    }
                    Toast.makeText(AdminSliderActivity.this, "Upload", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

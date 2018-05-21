package fr.wcs.foodtruck.UI.Activity.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import fr.wcs.foodtruck.Model.SliderModel;
import fr.wcs.foodtruck.R;

public class AdminSliderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AdminSliderActivity";
    private ImageView mSlide1,mSlide2,mSlide3;
    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private StorageReference mStorage;
    private Button mValideSlider;
    SliderModel mSliderModel = null;
    private Bitmap mBitmap;
    private final static int REQUEST_CODE = 111;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_slider);

        mSlide1 = (ImageView) findViewById(R.id.slide1);
        mSlide2 = (ImageView) findViewById(R.id.slide2);
        mSlide3 = (ImageView) findViewById(R.id.slide3);
        mValideSlider = (Button) findViewById(R.id.valideSlider);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        mSlide1.setOnClickListener(this);
        mSlide2.setOnClickListener(this);
        mSlide3.setOnClickListener(this);
        addImgSlider(1);
        addImgSlider(2);
        addImgSlider(3);

//        mValideSlider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validation(1);
//                validation(2);
//                validation(3);
//            }
//        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case (R.id.slide1):
                x = 1;
                CropImage.activity()
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AdminSliderActivity.this);
                break;
            case (R.id.slide2):
                x = 2;
                CropImage.activity()
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AdminSliderActivity.this);
                break;
            case (R.id.slide3):
                x = 3;
                CropImage.activity()
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AdminSliderActivity.this);
                break;
        }
    }

    private void addImgSlider(final int x) {
        mRef.child("Slider/Slide" + x + "/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SliderModel slideM = dataSnapshot.getValue(SliderModel.class);
                if (x == 1){
                    Glide.with(AdminSliderActivity.this).load(slideM.getSliderUrl()).into(mSlide1);
                }else if (x == 2){
                    Glide.with(AdminSliderActivity.this).load(slideM.getSliderUrl()).into(mSlide2);
                }else{
                    Glide.with(AdminSliderActivity.this).load(slideM.getSliderUrl()).into(mSlide3);
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

//        if ((requestCode == REQUEST_CODE && resultCode == RESULT_OK) && data != null && data.getData() != null) {
//
//            Uri sliderUri = data.getData();
//
//            StorageReference refStorage = mStorage.child("Slider" + x + "/").child(sliderUri.getLastPathSegment());
//            refStorage.putFile(sliderUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    SliderModel slideMod = new SliderModel(taskSnapshot.getDownloadUrl().toString());
//                    mRef.child("Slider/Slide" + x + "/").setValue(slideMod);
//                    if (x == 1){
//                        Glide.with(AdminSliderActivity.this).load(slideMod.getSliderUrl()).into(mSlide1);
//                    }else if (x == 2){
//                        Glide.with(AdminSliderActivity.this).load(slideMod.getSliderUrl()).into(mSlide2);
//                    }else{
//                        Glide.with(AdminSliderActivity.this).load(slideMod.getSliderUrl()).into(mSlide3);
//                    }
//                    Toast.makeText(AdminSliderActivity.this, "Upload", Toast.LENGTH_LONG).show();
//                }
//            });
//        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri sliderUri = result.getUri();
                if (x==1){
                    mSlide1.setImageDrawable(Drawable.createFromPath(sliderUri.getPath()));
                    validation(1);
                }else if (x==2){
                    mSlide2.setImageDrawable(Drawable.createFromPath(sliderUri.getPath()));
                    validation(2);
                }else{
                    mSlide3.setImageDrawable(Drawable.createFromPath(sliderUri.getPath()));
                    validation(3);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exception = result.getError();
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void validation(int x){

        ProgressDialog dialog = new ProgressDialog(AdminSliderActivity.this);
        dialog.setTitle("Mise à jour du Slider");
        dialog.setMessage("veuillez patienter");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();
//
//        mMajPlatDuJour.setNomPlat(mNomPlatDuJour.getText().toString());
//        mMajPlatDuJour.setDescPlat(mDescriptionDuPlat.getText().toString());
//        mMajPlatDuJour.setPrix(mPrixDuPlat.getText().toString());

        if (x==1){
            mSlide1.setDrawingCacheEnabled(true);
            mSlide1.buildDrawingCache();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            mStorage = storage.getReference("Slider" + x + "/");

            mBitmap = mSlide1.getDrawingCache();
        }else if (x==2){
            mSlide2.setDrawingCacheEnabled(true);
            mSlide2.buildDrawingCache();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            mStorage = storage.getReference("Slider" + x + "/");

            mBitmap = mSlide2.getDrawingCache();
        }else{
            mSlide3.setDrawingCacheEnabled(true);
            mSlide3.buildDrawingCache();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            mStorage = storage.getReference("Slider" + x + "/");

            mBitmap = mSlide3.getDrawingCache();
        }
//        mSlide1.setDrawingCacheEnabled(true);
//        mSlide1.buildDrawingCache();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        mStorage = storage.getReference("PlatDuJour/");
//
//        Bitmap bitmap = mSlide1.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorage.putBytes(data);
        uploadTask.addOnFailureListener(exception -> Log.d(TAG, "onFailure: " + exception.getLocalizedMessage())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                mSliderModel.setSliderUrl(downloadUrl.toString());
                mRef.child("Slider/Slide" + x + "/").setValue(mSliderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdminSliderActivity.this, "Maj terminé !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}




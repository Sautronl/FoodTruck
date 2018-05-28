package fr.wcs.foodtruck.UI.Activity.Admin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
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

import java.util.ArrayList;
import java.util.List;

import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.Model.SliderModel;
import fr.wcs.foodtruck.R;

public class SliderConfActivity extends AppCompatActivity {

//    ImageView mSlide1,mSlide2,mSlide3;
//    private int x =0;
//    //private Uri slideImg;
//    private ArrayList<Integer> idSlider = new ArrayList<>();
//    //private StorageReference mStorageSlide;
//    private FirebaseDatabase mSlideFire;
//    private DatabaseReference mDbRefSlide;
//    SliderModel mSliderModel;
//
//    private static final int REQUEST_CODE = 10000;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_slider_conf);
//
//        mSlide1 = (ImageView) findViewById(R.id.slide1);
//        mSlide2 = (ImageView) findViewById(R.id.slide2);
//        mSlide3 = (ImageView) findViewById(R.id.slide3);
//
//        idSlider.add(R.id.slide1);
//        idSlider.add(R.id.slide2);
//        idSlider.add(R.id.slide3);
//
//        mSlideFire = FirebaseDatabase.getInstance();
//        mDbRefSlide = mSlideFire.getReference();
//        //mStorageSlide = FirebaseStorage.getInstance().getReference();
//
//        mSlide1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                x = 1;
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(intent.ACTION_GET_CONTENT);
//                intent.putExtra("num",x);
//                startActivityForResult(Intent.createChooser(intent, "Selectionner une image"), REQUEST_CODE);
//                //addSlide(1);
//            }
//        });
//
//        mSlide2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                x = 2;
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(intent.ACTION_GET_CONTENT);
//                intent.putExtra("num",x);
//                startActivityForResult(Intent.createChooser(intent, "Selectionner une image"), REQUEST_CODE);
//                //addSlide(2);
//            }
//        });
//
//        mSlide3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                x = 3;
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(intent.ACTION_GET_CONTENT);
//                intent.putExtra("num",x);
//                startActivityForResult(Intent.createChooser(intent, "Selectionner une image"), REQUEST_CODE);
//                //addSlide(3);
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if ((requestCode == REQUEST_CODE && resultCode == RESULT_OK) && data != null && data.getData() != null) {
//            //mStorageSlide = FirebaseStorage.getInstance().getReference();
//            final int i = getIntent().getIntExtra("num",x);
//
//            Uri slideImg = data.getData();
//
//            StorageReference ref  = FirebaseStorage.getInstance().getReference().child("Slider/");
//            ref.putFile(slideImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    SliderModel slideModel = new SliderModel(taskSnapshot.getDownloadUrl().toString());
//                    if (i == 1) {
//                        mDbRefSlide.child("Slider/SlideOne/").setValue(slideModel);
//                        Glide.with(SliderConfActivity.this).load(slideModel.getSliderOne()).into(mSlide1);
//
//                    } else if (i == 2) {
//                        mDbRefSlide.child("Slider/SlideTwo/").setValue(slideModel);
//                        Glide.with(SliderConfActivity.this).load(slideModel.getSliderOne()).into(mSlide2);
//                        addSlide(i);
//                    } else {
//                        mDbRefSlide.child("Slider/SlideThree/").setValue(slideModel);
//                        Glide.with(SliderConfActivity.this).load(slideModel.getSliderOne()).into(mSlide3);
//                    }
//                    Toast.makeText(SliderConfActivity.this, "Upload", Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//    protected void addSlide(final int x) {
//        mDbRefSlide.child("Slider/Slide/" + x + "/").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                mSliderModel = dataSnapshot.getValue(SliderModel.class);
//                if (x == 1) {
//                    Glide.with(SliderConfActivity.this).load(mSliderModel.getSliderOne()).into(mSlide1);
//                } else if (x == 2) {
//                    Glide.with(SliderConfActivity.this).load(mSliderModel.getSliderOne()).into(mSlide2);
//                } else {
//                    Glide.with(SliderConfActivity.this).load(mSliderModel.getSliderOne()).into(mSlide3);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}

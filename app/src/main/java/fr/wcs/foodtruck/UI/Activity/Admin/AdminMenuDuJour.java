package fr.wcs.foodtruck.UI.Activity.Admin;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;


public class  AdminMenuDuJour extends AppCompatActivity {

    private EditText mNomPlatDuJour;
    private EditText mDescriptionDuPlat,mPrixDuPlat;
    private Button mMaj;
    private ImageView mMenu;
    private ImageView mImgMenu;
    private StorageReference mStorage;
    private static final int REQUEST_CODE = 20;
    int day;
    private Uri imgUri;
     MajPlatDuJour mMajPlatDuJour = null;

    public static String TAG = "AdminMenuDuJour";

    private FirebaseDatabase mFireMenu;
    private DatabaseReference mDbRefMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_du_jour);

        ScrollView scrollMenuAdmin = (ScrollView) findViewById(R.id.scrollMenuAdmin);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(scrollMenuAdmin,mainfont);

        mNomPlatDuJour = (EditText) findViewById(R.id.nomDuPlat);
        mDescriptionDuPlat = (EditText) findViewById(R.id.descriPlat);
        mPrixDuPlat = (EditText) findViewById(R.id.prix);
        mMaj = (Button) findViewById(R.id.reserverMenu);

        mFireMenu = FirebaseDatabase.getInstance();
        mDbRefMenu = mFireMenu.getReference();

        mStorage = FirebaseStorage.getInstance().getReference();
        mImgMenu = (ImageView) findViewById(R.id.imgDuPlat);

        mImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AdminMenuDuJour.this);
            }
        });

        Intent jour = getIntent();
        day = jour.getIntExtra("day", 0);

        if (day == 0) {
            addMaj("menu/menuLundi/");

        } else if (day == 1) {
            addMaj("menu/menuMardi/");
        }
        else if (day == 2) {
            addMaj("menu/menuMercredi/");
        }
        else if (day == 3) {
            addMaj("menu/menuJeudi/");
        }
        else if (day == 4) {
            addMaj("menu/menuVendredi/");
        }

        mMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (day == 0) {
                    firebaseInfoMenu("menu/menuLundi/");

                } else if (day == 1) {
                    firebaseInfoMenu("menu/menuMardi/");
                }
                else if (day == 2) {
                    firebaseInfoMenu("menu/menuMercredi/");
                }
                else if (day == 3) {
                    firebaseInfoMenu("menu/menuJeudi/");
                }
                else if (day == 4) {
                    firebaseInfoMenu("menu/menuVendredi/");
                }
            }
        });
    }

    private void firebaseInfoMenu(String child){
        mMajPlatDuJour.setNomPlat(mNomPlatDuJour.getText().toString());
        mMajPlatDuJour.setDescPlat(mDescriptionDuPlat.getText().toString());
        mMajPlatDuJour.setPrix(mPrixDuPlat.getText().toString());

        mImgMenu.setDrawingCacheEnabled(true);
        mImgMenu.buildDrawingCache();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference("PlatDuJour/");

        Bitmap bitmap = mImgMenu.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorage.putBytes(data);
        uploadTask.addOnFailureListener(exception -> Log.d(TAG, "onFailure: " + exception.getLocalizedMessage())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                mMajPlatDuJour.setUrlImg(downloadUrl.toString());
                mDbRefMenu.child(child).setValue(mMajPlatDuJour);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imgUri = result.getUri();
                mImgMenu.setImageDrawable(Drawable.createFromPath(imgUri.getPath()));

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exception = result.getError();
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void addMaj(String day){
        mDbRefMenu.child(day).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMajPlatDuJour = dataSnapshot.getValue(MajPlatDuJour.class);
                mNomPlatDuJour.setText(mMajPlatDuJour.getNomPlat());
                mDescriptionDuPlat.setText(mMajPlatDuJour.getDescPlat());
                mPrixDuPlat.setText(mMajPlatDuJour.getPrix());
                Glide.with(getApplicationContext()).load(mMajPlatDuJour.getUrlImg()).into(mImgMenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

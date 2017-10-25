package fr.wcs.foodtruck;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;


public class  AdminMenuDuJour extends AppCompatActivity {

    private EditText mNomPlatDuJour;
    private EditText mDescriptionDuPlat;
    private Button mMaj;
    private ImageView mMenu;
    private ImageView mImgMenu;
    private StorageReference mStorage;
    private static final int REQUEST_CODE = 20;
    private Uri imgUri;


    private FirebaseDatabase mFireMenu;
    private DatabaseReference mDbRefMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_du_jour);

        int day;
        mNomPlatDuJour = (EditText) findViewById(R.id.nomDuPlat);
        mDescriptionDuPlat = (EditText) findViewById(R.id.descriPlat);
        mMaj = (Button) findViewById(R.id.reserverMenu);

        mFireMenu = FirebaseDatabase.getInstance();
        mDbRefMenu = mFireMenu.getReference();

       // mMenu = (ImageView) findViewById(R.id.imgplus);
        mStorage = FirebaseStorage.getInstance().getReference();
        mImgMenu = (ImageView) findViewById(R.id.imgDuPlat);

        Intent jour = getIntent();
        day = jour.getIntExtra("day", 0);


        if (day == 0) {
            mDbRefMenu = mDbRefMenu.child("menu/menuLundi");
            majClick();
            addMaj();

        } else if (day == 1) {
            mDbRefMenu = mDbRefMenu.child("menu/menuMardi");
            majClick();
            addMaj();
        }
        else if (day == 2) {
            mDbRefMenu = mDbRefMenu.child("menu/menuMercredi");
            majClick();
            addMaj();
        }
        else if (day == 3) {
            mDbRefMenu = mDbRefMenu.child("menu/menuJeudi");
            majClick();
            addMaj();
        }
        else if (day == 4) {
            mDbRefMenu = mDbRefMenu.child("menu/menuVendredi");
            majClick();
            addMaj();
        }
    }

    protected void majClick(){
        mMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selectionner une image"), REQUEST_CODE);
            }
        });
    }

     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE && resultCode == RESULT_OK) && data != null && data.getData() != null) {

            imgUri = data.getData();

            StorageReference ref = mStorage.child("image/").child(imgUri.getLastPathSegment());
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    if (mDescriptionDuPlat.getText().toString().isEmpty() || mNomPlatDuJour.getText().toString().isEmpty()) {
                        Toast.makeText(AdminMenuDuJour.this, getResources().getString(R.string.messToast), Toast.LENGTH_SHORT).show();
                    }
                        else{
                        MajPlatDuJour maj = new MajPlatDuJour(mNomPlatDuJour.getText().toString(),
                                mDescriptionDuPlat.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                        mDbRefMenu.setValue(maj);
                        Glide.with(AdminMenuDuJour.this).load(maj.getUrlImg()).into(mImgMenu);
                        Toast.makeText(AdminMenuDuJour.this, "Upload", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    protected void addMaj(){
        mDbRefMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MajPlatDuJour majText = dataSnapshot.getValue(MajPlatDuJour.class);
                mNomPlatDuJour.setText(majText.getNomPlat());
                mDescriptionDuPlat.setText(majText.getDescPlat());
                Glide.with(AdminMenuDuJour.this).load(majText.getUrlImg()).into(mImgMenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

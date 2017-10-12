package fr.wcs.foodtruck;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




public class AdminMenuDuJourLundi extends AppCompatActivity {

    //public static String DB_URL="https://foodtruck-ebd2c.firebaseio.com/";


    private EditText nomPlatDuJour;
    private EditText descriptionDuPlat;
    //private EditText prix;
    private Button maj;
    private ImageView mMenu;
    private ImageView mImgMenu;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 20;

    private FirebaseDatabase mFire;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_du_jour_lundi);

 // initialisation firebase

       // initializeFirebase();

        nomPlatDuJour = (EditText)findViewById(R.id.nomDuPlat);
        descriptionDuPlat =(EditText)findViewById(R.id.descriPlat);
        //prix = (EditText)findViewById(R.id.prixHint);
        maj =(Button)findViewById(R.id.maj);

        mFire = FirebaseDatabase.getInstance();
        mDbRef = mFire.getReference("MajPlatDuJourLundi");

        mMenu =(ImageView) findViewById(R.id.imageView4);
        mStorage = FirebaseStorage.getInstance().getReference();
        mImgMenu = (ImageView)findViewById(R.id.imgDuPlat);


        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        // mFire = new Firebase (DB_URL);

        maj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MajPlatDuJour majLundi = new MajPlatDuJour();
                majLundi.setNomPlatDuJour(nomPlatDuJour.getText().toString());
                majLundi.setDescriptionDuPlat(descriptionDuPlat.getText().toString());
                //majLundi.setPrix(maj.getText().toString());

                mDbRef.setValue(majLundi);

                nomPlatDuJour.setText("");
                descriptionDuPlat.setText("");

            }
        });

        /*mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MajPlatDuJour nomDuPlat = dataSnapshot.getValue(MajPlatDuJour.class);
                String plat = dataSnapshot.getValue(String.class);
                String desc = dataSnapshot.getValue(String.class);
                descriptionDuPlat.setText(desc);
                nomPlatDuJour.setText(plat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            //mProgressDia.setMessage("Upload..");
            // mProgressDia.show();
            Uri uri = data.getData();

            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Glide.with(AdminMenuDuJourLundi.this).load(downloadUri).into(mImgMenu);
                    Toast.makeText(AdminMenuDuJourLundi.this, "Upload", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}

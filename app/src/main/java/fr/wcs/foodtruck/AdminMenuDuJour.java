package fr.wcs.foodtruck;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class  AdminMenuDuJour extends AppCompatActivity {

    private ImageView mMenu;
    private ImageView mImgMenu;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_du_jour);

        mMenu =(ImageView) findViewById(R.id.imgplus);
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
                    Glide.with(AdminMenuDuJour.this).load(downloadUri).into(mImgMenu);
                    Toast.makeText(AdminMenuDuJour.this, "Upload", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        ;
    }

}


package fr.wcs.foodtruck;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


public class  AdminMenuDuJour extends AppCompatActivity {

    int day;
    private EditText mNomPlatDuJour;
    private EditText mDescriptionDuPlat;
    private Button mMaj;
    private ImageView mMenu;
    private ImageView mImgMenu;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 20;

    private FirebaseDatabase mFire;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_du_jour);

        int day;
        mNomPlatDuJour = (EditText) findViewById(R.id.nomDuPlat);
        mDescriptionDuPlat = (EditText) findViewById(R.id.descriPlat);
        mMaj = (Button) findViewById(R.id.reserver);

        mFire = FirebaseDatabase.getInstance();
        mDbRef = mFire.getReference("app/menu");

        mMenu = (ImageView) findViewById(R.id.imgDuPlat);
        mStorage = FirebaseStorage.getInstance().getReference();
        mImgMenu = (ImageView) findViewById(R.id.imgDuPlat);

        Intent jour = getIntent();
        day = jour.getIntExtra("day", 0);

       /* mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });*/

        if (day == 0) {
            mMaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MajPlatDuJour majLundi = new MajPlatDuJour();
                    majLundi.setNomPlatDuJour(mNomPlatDuJour.getText().toString());
                    majLundi.setDescriptionDuPlat(mDescriptionDuPlat.getText().toString());

                    mDbRef.child("menuLundi").setValue(majLundi);
                    Toast.makeText(AdminMenuDuJour.this, "Menu validé", Toast.LENGTH_SHORT).show();
                    //mNomPlatDuJour.setText("");
                    //mDescriptionDuPlat.setText("");
                }
            });
            mMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
            });
            addValueLundi();
        }
        if (day == 1) {
            mMaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MajPlatDuJour majMardi = new MajPlatDuJour();
                    majMardi.setNomPlatDuJour(mNomPlatDuJour.getText().toString());
                    majMardi.setDescriptionDuPlat(mDescriptionDuPlat.getText().toString());

                    mDbRef.child("menuMardi").setValue(majMardi);
                    Toast.makeText(AdminMenuDuJour.this, "Menu validé", Toast.LENGTH_SHORT).show();
                    //mNomPlatDuJour.setText("");
                    //mDescriptionDuPlat.setText("");
                }
            });
            mMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
            });
            addValueMardi();

        }
        if (day == 2) {
            mMaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MajPlatDuJour majMercredi = new MajPlatDuJour();
                    majMercredi.setNomPlatDuJour(mNomPlatDuJour.getText().toString());
                    majMercredi.setDescriptionDuPlat(mDescriptionDuPlat.getText().toString());

                    mDbRef.child("menuMercredi").setValue(majMercredi);
                    Toast.makeText(AdminMenuDuJour.this, "Menu validé", Toast.LENGTH_SHORT).show();
                    //mNomPlatDuJour.setText("");
                    //mDescriptionDuPlat.setText("");
                }
            });
            mMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
            });
            addValueMercredi();
        }
        if (day == 3) {
            mMaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MajPlatDuJour majJeudi = new MajPlatDuJour();
                    majJeudi.setNomPlatDuJour(mNomPlatDuJour.getText().toString());
                    majJeudi.setDescriptionDuPlat(mDescriptionDuPlat.getText().toString());

                    mDbRef.child("menuJeudi").setValue(majJeudi);
                    Toast.makeText(AdminMenuDuJour.this, "Menu validé", Toast.LENGTH_SHORT).show();
                    //mNomPlatDuJour.setText("");
                    //mDescriptionDuPlat.setText("");
                }
            });
            mMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
            });
            addValueJeudi();
        }
        if (day == 4) {
            mMaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MajPlatDuJour majVendredi = new MajPlatDuJour();
                    majVendredi.setNomPlatDuJour(mNomPlatDuJour.getText().toString());
                    majVendredi.setDescriptionDuPlat(mDescriptionDuPlat.getText().toString());

                    mDbRef.child("menuVendredi").setValue(majVendredi);
                    Toast.makeText(AdminMenuDuJour.this, "Menu validé", Toast.LENGTH_SHORT).show();
                    //mNomPlatDuJour.setText("");
                    //mDescriptionDuPlat.setText("");
                }
            });
            mMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
            });
            addValueVendredi();
        }
    }
    protected void addValueLundi() {

        mDbRef.child("menuLundi/nomPlatDuJour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String plat = dataSnapshot.getValue(String.class);
                mNomPlatDuJour.setText(plat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDbRef.child("menuLundi/descriptionDuPlat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String desc = dataSnapshot.getValue(String.class);
                mDescriptionDuPlat.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void addValueMardi() {

        mDbRef.child("menuMardi/nomPlatDuJour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String plat = dataSnapshot.getValue(String.class);
                mNomPlatDuJour.setText(plat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDbRef.child("menuMardi/descriptionDuPlat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String desc = dataSnapshot.getValue(String.class);
                mDescriptionDuPlat.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void addValueMercredi() {
        mDbRef.child("menuMercredi/nomPlatDuJour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String plat = dataSnapshot.getValue(String.class);
                mNomPlatDuJour.setText(plat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDbRef.child("menuMercredi/descriptionDuPlat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String desc = dataSnapshot.getValue(String.class);
                mDescriptionDuPlat.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void addValueJeudi() {
        mDbRef.child("menuJeudi/nomPlatDuJour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String plat = dataSnapshot.getValue(String.class);
                mNomPlatDuJour.setText(plat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDbRef.child("menuJeudi/descriptionDuPlat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String desc = dataSnapshot.getValue(String.class);
                mDescriptionDuPlat.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void addValueVendredi() {
        mDbRef.child("menuVendredi/nomPlatDuJour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String plat = dataSnapshot.getValue(String.class);
                mNomPlatDuJour.setText(plat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDbRef.child("menuVendredi/descriptionDuPlat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String desc = dataSnapshot.getValue(String.class);
                mDescriptionDuPlat.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
}

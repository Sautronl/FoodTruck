package fr.wcs.foodtruck;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ContactAdmin extends AppCompatActivity {

    private ListView mListViewContactAdmin;
    //private ProgressBar circular_progress;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private AdapterContactAdmin mAdapterContact;
    private ContactAdminModel mSelectedContact;
    private Button delete;
    private int mTotal;

    private int mCurrentPosition;

    private List<ContactAdminModel> mList_contact = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);

        //Toolbar personnalisée avec bouton retour à la page précédente
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ContactAdmin.this, ChoixReserveContactAdmin.class);
                startActivity(back);
            }
        });
        //Fin de la toolbar

        //Control
        //circular_progress = (ProgressBar)findViewById(R.id.circular_progress);

        //Liste d'events
        mListViewContactAdmin = (ListView)findViewById(R.id.listeCon);
        mListViewContactAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ContactAdminModel contact = (ContactAdminModel) adapterView.getItemAtPosition(position);
                mCurrentPosition = position;
                for (int i = 0; i < mListViewContactAdmin.getChildCount(); i++) {
                    if(position == i ){
                        mListViewContactAdmin.getChildAt(i).setBackgroundColor(Color.BLACK);
                    }else{
                        mListViewContactAdmin.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        /*delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mDatabaseReference.child("contact").child(mSelectedContact.getId()).removeValue();
                mTotal = mList_contact.size() - 1;
                mList_contact.remove(mTotal);
                mListViewContactAdmin.setAdapter(null);
            }
        });*/

        //Firebase
        initFirebase();
        childFirebaseListener();
        //addEventFirebaseListener();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void childFirebaseListener(){
        mDatabaseReference.child("contact").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // if (mList_contact.size() > 0)
                //    mList_contact.clear();
                //for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ContactAdminModel contactAd = dataSnapshot.getValue(ContactAdminModel.class);
                    mList_contact.add(contactAd);
                    mAdapterContact = new AdapterContactAdmin(ContactAdmin.this, mList_contact);
                    mListViewContactAdmin.setAdapter(mAdapterContact);
               // }
                //mAdapterContact = new AdapterContactAdmin(ContactAdmin.this, mList_contact);
                //mListViewContactAdmin.setAdapter(mAdapterContact);
                //circular_progress.setVisibility(View.INVISIBLE);
                //mListViewContactAdmin.setVisibility(View.VISIBLE);
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(final DataSnapshot dataSnapshot) {
               Button delete = (Button) findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabaseReference.child("contact").removeValue();
                        mTotal = mList_contact.size() - 1;
                        mList_contact.remove(mTotal);
                        mAdapterContact = new AdapterContactAdmin(ContactAdmin.this, mList_contact);
                        mListViewContactAdmin.setAdapter(null);
                    }
                });
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*private void deleteContact(ContactAdminModel mTotal) {
        mDatabaseReference.child("contact").child(mSelectedContact.getId()).removeValue();
        mList_contact.remove(mTotal);
        mListViewContactAdmin.setAdapter(null);
    }*/

}


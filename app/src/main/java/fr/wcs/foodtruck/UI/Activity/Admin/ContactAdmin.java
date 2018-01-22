package fr.wcs.foodtruck.UI.Activity.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.foodtruck.Model.ContactAdminModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Adapter.AdapterContactAdmin;
import fr.wcs.foodtruck.UI.Adapter.AdapterListEmplacement;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;


public class ContactAdmin extends AppCompatActivity {

    private RecyclerView mListViewContactAdmin;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private AdapterContactAdmin mAdapterContact;
    private ContactAdminModel mSelectedContact;
    private List<ContactAdminModel> mList_contact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);

        RelativeLayout layoutContactAdmin = (RelativeLayout) findViewById(R.id.layoutContactAdmin);

        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(layoutContactAdmin,mainfont);

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

        final Button remove = (Button)findViewById(R.id.removeButton);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapterContact == null) {
                    Toast.makeText(ContactAdmin.this, getResources().getString(R.string.suppListeAdmin), Toast.LENGTH_SHORT).show();
                } else {
                    mDatabaseReference.child("contact").removeValue();
                    mListViewContactAdmin.setAdapter(null);
                    mAdapterContact.notifyDataSetChanged();
                }
            }
        });

        //Firebase
        initFirebase();
        childFirebaseListener();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void childFirebaseListener(){
        mDatabaseReference.child("contact/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mList_contact.size()>0){mList_contact.clear();}
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    mSelectedContact = snap.getValue(ContactAdminModel.class);
                    mList_contact.add(mSelectedContact);
                }
                mAdapterContact = new AdapterContactAdmin(ContactAdmin.this, mList_contact);
                mListViewContactAdmin.setAdapter(mAdapterContact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
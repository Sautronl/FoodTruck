package fr.wcs.foodtruck.UI.Activity.User;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import fr.wcs.foodtruck.Model.ContactAdminModel;
import fr.wcs.foodtruck.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    private DatabaseReference mref = mFirebase.getReference();
    private EditText mPrenomNom;
    private EditText mTel;
    private EditText mSujet;
    private EditText mMessage;
    private Button mBoutonValider;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        mPrenomNom = (EditText) view.findViewById(R.id.prenomNom);
        mTel = (EditText) view.findViewById(R.id.tel);
        mSujet = (EditText) view.findViewById(R.id.sujet);
        mMessage = (EditText) view.findViewById(R.id.message);
        ImageButton imageBoutonPhone = (ImageButton) view.findViewById(R.id.imageBoutonPhone);


        // checkbox
        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editSujet= (EditText) view.findViewById(R.id.sujet);

                if (checkbox.isChecked()) {
                    // est coché
                    String privateOK = editSujet.getResources().getString(R.string.sujet2);
                    editSujet.setText(privateOK);

                } else {
                    // n'est pas coché
                    editSujet.setText("");
                }
            }
        });

        imageBoutonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        Intent intent = getActivity().getIntent();

        // Message Toast si les champs obligatoires ne sont pas remplis

        mBoutonValider = (Button)view.findViewById(R.id.boutonEnvoyer);
        mBoutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editPrenomNom = (EditText) view.findViewById(R.id.prenomNom);
                EditText editSujet = (EditText)view.findViewById(R.id.sujet);
                EditText editMessage = (EditText)view.findViewById(R.id.message);

                if(editPrenomNom.getText().toString().isEmpty()
                        || editSujet.getText().toString().isEmpty()
                        || editMessage.getText().toString().isEmpty() )
                {

                    CharSequence text = getResources().getString(R.string.messToast);
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getContext();
                    Toast messToast = Toast.makeText(context, text, duration);
                    messToast.show();
                }
                // Message Toast de confirmation d'envoie
                else {
                    CharSequence text = getResources().getString(R.string.messToastValider);
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getContext();
                    Toast messToast = Toast.makeText(context, text, duration);
                    messToast.show(); }
            }

        });
        createContact(view);

        return view;
    }

    private void createContact(View view) {

        mBoutonValider = (Button)view.findViewById(R.id.boutonEnvoyer);
        mBoutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPrenomNom.getText().toString().isEmpty() || mSujet.getText().toString().isEmpty() || mMessage.getText().toString().isEmpty() || mTel.getText().toString().isEmpty()){

                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.messToast),Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.messToastValider),Toast.LENGTH_SHORT).show();

                    ContactAdminModel contact = new ContactAdminModel(UUID.randomUUID().toString(),
                            mPrenomNom.getText().toString(),
                            mTel.getText().toString(),
                            mSujet.getText().toString(),
                            mMessage.getText().toString());

                    mref.child("contact").child(contact.getId()).setValue(contact);
                    clearEditText();
                }
            }
        });
    }

    private void clearEditText() {
        mPrenomNom.setText("");
        mTel.setText("");
        mSujet.setText("");
        mMessage.setText("");
    }
}

package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import fr.wcs.foodtruck.Model.ContactAdminModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

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
    private CheckBox checkbox;
    private String mPrenomMail,mTelMail,mSujetMail,mMessageMail;
    public static String EMAILCONTACT = "samdonnefaim@outlook.fr";

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        getActivity().setTitle("Contact");

        ScrollView scrollPrivat = view.findViewById(R.id.scrollPrivat);

        Typeface mainfont = Typeface.createFromAsset(getActivity().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(scrollPrivat,mainfont);

        mPrenomNom = (EditText) view.findViewById(R.id.prenomNom);
        mTel = (EditText) view.findViewById(R.id.tel);
        mSujet = (EditText) view.findViewById(R.id.sujet);
        mMessage = (EditText) view.findViewById(R.id.message);
        ImageButton imageBoutonPhone = (ImageButton) view.findViewById(R.id.imageBoutonPhone);

        // checkbox
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editSujet= (EditText) view.findViewById(R.id.sujet);

                if (checkbox.isChecked()) {
                    // est coché
                    mSujet.setText(getResources().getString(R.string.sujet2));

                } else {
                    // n'est pas coché
                    mSujet.setText("");
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
                    createContact(view);
                    CharSequence text = getResources().getString(R.string.messToastValider);
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getContext();
                    Toast messToast = Toast.makeText(context, text, duration);
                    messToast.show(); }
            }

        });
        return view;
    }

    private void createContact(View view) {

        mBoutonValider = (Button)view.findViewById(R.id.boutonEnvoyer);
        mBoutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrenomMail =  mPrenomNom.getText().toString();
                mMessageMail = mMessage.getText().toString();
                mSujetMail = mSujet.getText().toString();
                mTelMail = mTel.getText().toString();
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

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setData(Uri.parse("mailto:"));
//                    i.setType("text/plain");
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[] {EMAILCONTACT});

                    String subject = "";
                    if (!mSujetMail.equals("")) {
                        subject = mSujetMail;
                    }
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);

                    if (!mPrenomMail.equals("") && !mTelMail.equals("") && !mMessageMail.equals("")) {
                        i.putExtra(Intent.EXTRA_TEXT, mPrenomMail+"\n"+mTelMail+"\n"+mMessageMail);
                    }

                    try {
                        startActivity(Intent.createChooser(i, getResources().getString(R.string.messToastValider)));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(), getResources().getString(R.string.messToast), Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}

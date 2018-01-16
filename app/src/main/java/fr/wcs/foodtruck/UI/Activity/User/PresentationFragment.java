package fr.wcs.foodtruck.UI.Activity.User;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Adapter.GalleryAdapter;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresentationFragment extends Fragment {

    private FirebaseDatabase mFirebase;
    private DatabaseReference mAproposRef;
    private TextView mTextViewQsn;
    private TextView mTextViewNv;

    public PresentationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presentation, container, false);

        ScrollView scrollPresentation = (ScrollView) view.findViewById(R.id.scrollPresentation);

//        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
//        SetTypeFace.setAppFont(scrollPresentation,mainfont);

        //Toolbar personnalisée avec bouton retour à la page précédente
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.abs_layout);

        ImageView backButton = (ImageView) view.findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent back = new Intent(MenuDuJour(), MainActivity.class);
//                startActivity(back);
//            }
//        });
        //Fin de la toolbar
        mFirebase = FirebaseDatabase.getInstance();
        mAproposRef = mFirebase.getReference("ProposMAJ");
//        final GridView gridView = (GridView) view.findViewById(R.id.gallery);
//        gridView.setAdapter(new GalleryAdapter(this));
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(MenuDuJour(), fullImageActivity.class);
//                i.putExtra("id", position);
//                startActivity(i);
//
//            }
//        });

        Button button1 = (Button) view.findViewById(R.id.buttonPresentation1);
        Button button2 = (Button) view.findViewById(R.id.buttonPresentation2);
        Button button3 = (Button) view.findViewById(R.id.buttonPresentation3);
        mTextViewQsn = (TextView) view.findViewById(R.id.textViewPresentation1);
        mTextViewNv = (TextView) view.findViewById(R.id.textViewPresentation2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mTextViewQsn.getVisibility() == View.VISIBLE) {
                    mTextViewQsn.setVisibility(View.GONE);
                } else {
                    mTextViewQsn.setVisibility(VISIBLE);
                    mTextViewNv.setVisibility(View.GONE);
                    //gridView.setVisibility(View.GONE);

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTextViewNv.getVisibility() == View.VISIBLE) {
                    mTextViewNv.setVisibility(View.GONE);
                } else {
                    mTextViewNv.setVisibility(VISIBLE);
                    mTextViewQsn.setVisibility(View.GONE);
                   // gridView.setVisibility(View.GONE);
                }
            }
        });

//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (gridView.getVisibility() == View.VISIBLE) {
//                    gridView.setVisibility(View.GONE);
//                } else {
//                    gridView.setVisibility(VISIBLE);
//                    mTextViewNv.setVisibility(View.GONE);
//                    mTextViewQsn.setVisibility(View.GONE);
//                }
//            }
//        });
        ValueAproposListener();
        ValueNosValeursListener();

        return view;
    }

    protected void ValueAproposListener() {

        mAproposRef.child("QuiSommesNous").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String quiSnous = dataSnapshot.getValue(String.class);
                mTextViewQsn.setText(quiSnous);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    protected void ValueNosValeursListener() {

        mAproposRef.child("NosValeurs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nosValeurs = dataSnapshot.getValue(String.class);
                mTextViewNv.setText(nosValeurs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

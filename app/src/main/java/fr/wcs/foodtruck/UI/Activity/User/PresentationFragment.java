package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

        mFirebase = FirebaseDatabase.getInstance();
        mAproposRef = mFirebase.getReference("ProposMAJ");

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
                }
            }
        });
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}

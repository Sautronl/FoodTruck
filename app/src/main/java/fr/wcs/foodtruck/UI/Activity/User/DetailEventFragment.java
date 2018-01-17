package fr.wcs.foodtruck.UI.Activity.User;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.foodtruck.Model.EventModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEventFragment extends Fragment {

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private EventModel mEvent;
    TextView mDesc,mTitre,mDate;

    public DetailEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_event, container, false);

        getActivity().setTitle("Détail de l'événement");

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference("events");

        RelativeLayout relativeEventUserDetail = view.findViewById(R.id.relativeEventUserDetail);

        Typeface mainfont = Typeface.createFromAsset(getActivity().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(relativeEventUserDetail,mainfont);

        Bundle bundle = this.getArguments();
        mEvent = bundle.getParcelable("detailEvent");

        mDesc = view.findViewById(R.id.main_desc);
        mTitre = view.findViewById(R.id.titre_event_detail);
        mDate = view.findViewById(R.id.date_detail);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTitre.setText(mEvent.getName());
                mDate.setText(mEvent.getDate());
                mDesc.setText(mEvent.getDetails());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}

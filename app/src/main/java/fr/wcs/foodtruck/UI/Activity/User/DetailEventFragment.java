package fr.wcs.foodtruck.UI.Activity.User;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.wcs.foodtruck.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEventFragment extends Fragment {

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;

    public DetailEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_event, container, false);

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference("events");

        Bundle bundle = this.getArguments();
        bundle.getParcelable("detailEvent");



        return view;
    }

}

package fr.wcs.foodtruck.UI.Activity.User;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.wcs.foodtruck.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CloseFragment extends Fragment {


    public CloseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_close, container, false);

        return view;
    }

}
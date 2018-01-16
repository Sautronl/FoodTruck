package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.foodtruck.Model.ListJourEmplacementModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Adapter.AdapterListEmplacement;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapListFragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference coordonnerRef = database.getReference("Coordonner");
    private ProgressDialog mDialog;
    private AdapterListEmplacement mAdapter;
    private ArrayList<ListJourEmplacementModel> mListJ = new ArrayList<>() ;
    private String mAdresse;
    private String[] mDay;
    private int mI;


    private RecyclerView mListViewResults;

    public MapListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_list, container, false);

        getActivity().setTitle("Nos emplacements");

        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("Nos Emplacements");
        mDialog.setCancelable(false);
        mDialog.setMessage("En cours de chargement..");
        mDialog.show();

        mListViewResults = (RecyclerView) view.findViewById(R.id.listEmplacement);

        mListViewResults.setLayoutManager(new LinearLayoutManager(getContext()));

//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), MapsActivity.class);
//                intent.putExtra("jourMarkeur", i);
//                startActivity(intent);
//            }
//        });
        addAdrs();
        return view;
    }

    private void addAdrs(){
        mDay = new String[]{"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};
        mI = 0;
        coordonnerRef.orderByKey().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot daySnapshot : dataSnapshot.getChildren()) {
                    mAdresse = (String) daySnapshot.child("adrs").getValue();
                    mListJ.add(new ListJourEmplacementModel(mDay[mI],mAdresse));
                    mDay[mI] = mDay[mI];
                    mI++;
                }
                mAdapter = new AdapterListEmplacement(mListJ, getActivity());
                mAdapter.setOnItemClick(new AdapterListEmplacement.OnItemSelected() {
                    @Override
                    public void onItemClick(int index) {
                        for (int i = 0; i < index+1; i++) {
                            Intent intent = new Intent(getActivity(), MapsActivity.class);
                            intent.putExtra("jourMarkeur", index);
                            startActivity(intent);

                        }
                    }
                });
                mListViewResults.setAdapter(mAdapter);
                mDialog.dismiss();
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

package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapListFragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference coordonnerRef = database.getReference("Coordonner");
    DatabaseReference mRefStatus;
    private ProgressDialog mDialog;
    private AdapterListEmplacement mAdapter;
    private ArrayList<ListJourEmplacementModel> mListJ = new ArrayList<>() ;
    private String[] mDay;
    private int mI = 0;
    private int mJ = 0;
    private ArrayList<String> mDispo = new ArrayList<>();


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

        LinearLayout emplacementLinear = view.findViewById(R.id.emplacementLinear);

        mRefStatus = database.getReference();

        Typeface mainfont = Typeface.createFromAsset(getActivity().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(emplacementLinear,mainfont);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("Nos Emplacements");
        mDialog.setCancelable(false);
        mDialog.setMessage("En cours de chargement..");
        mDialog.show();

        mListViewResults = (RecyclerView) view.findViewById(R.id.listEmplacement);

        mListViewResults.setLayoutManager(new LinearLayoutManager(getContext()));
        addAdrs();
        return view;
    }

    private void addAdrs(){
        mDay = new String[]{"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
       // mI = 0;

        mRefStatus.child("Avaible/"+mDay[mI]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean stat = dataSnapshot.getValue(Boolean.class);
                if (stat) {
                    mDispo.add(mDay[mI]);
                    if (mI == mDay.length-1) {
                        checkD(mDispo);
                    } else {
                        mI++;
                        addAdrs();
                    }
                }else{
                    mI++;
                    addAdrs();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void checkD(ArrayList<String> mDispo) {
        mJ = 0;
         coordonnerRef.orderByKey().addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for (DataSnapshot daySnapshot : dataSnapshot.getChildren()) {
                     if (daySnapshot.getKey().contains(mDispo.get(mJ))){
                         String ad = (String) daySnapshot.child("adrs").getValue();
                         mListJ.add(new ListJourEmplacementModel(mDispo.get(mJ),ad));
                         mJ++;
                     }
                 }
                 mAdapter = new AdapterListEmplacement(mListJ, getActivity());
                 mAdapter.setOnItemClick(new AdapterListEmplacement.OnItemSelected() {
                     @Override
                     public void onItemClick(int index, String jourD) {
                         Intent intent = new Intent(getActivity(), MapsActivity.class);
                         intent.putExtra("jourMarkeur", jourD);
                         startActivity(intent);
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
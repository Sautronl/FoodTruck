package fr.wcs.foodtruck.UI.Activity.User;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.wcs.foodtruck.Model.EventModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Adapter.ListEventAdapter;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private ListView list_data;
    private ProgressBar circular_progress;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ListEventAdapter mAdapter;
    private ProgressDialog mDialog;

    private int mCurrentPosition;

    private List<EventModel> list_events = new ArrayList<>();

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("Evénements");
        mDialog.setCancelable(false);
        mDialog.setMessage("En cours de chargement..");
        mDialog.show();

        //Liste d'events
        list_data = (ListView)view.findViewById(R.id.list_data);
        /*list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EventModel event = (EventModel)adapterView.getItemAtPosition(position);
                mCurrentPosition = position;
                for (int i = 0; i < list_data.getChildCount(); i++) {
                    if(position == i ){
                        list_data.getChildAt(i).setBackgroundColor(Color.BLACK);
                    }else{
                        list_data.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });*/
        //Firebase
        initFirebase();
        addEventFirebaseListener();
        return view;
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(getContext());
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("events");
    }

    private void addEventFirebaseListener() {
        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_events.size() > 0)
                    list_events.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventModel event = postSnapshot.getValue(EventModel.class);
                    list_events.add(event);
                }
                Collections.reverse(list_events);
                mAdapter = new ListEventAdapter(getActivity(), list_events);
                list_data.setAdapter(mAdapter);
                mDialog.dismiss();
                list_data.setVisibility(View.VISIBLE);
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

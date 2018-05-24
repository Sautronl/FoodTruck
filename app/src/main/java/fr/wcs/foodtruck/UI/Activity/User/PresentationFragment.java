package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Adapter.GalleryAdapter;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresentationFragment extends Fragment implements ViewPagerEx.OnPageChangeListener,View.OnClickListener{

    private FirebaseDatabase mFirebase;
    private DatabaseReference mAproposRef;
    private TextView mTextViewQsn;
    private TextView mTextViewNv;
    private SliderLayout mSlide;

    public PresentationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presentation, container, false);

        ScrollView scrollPresentation = view.findViewById(R.id.scrollPresentation);

        getActivity().setTitle("Notre histoire");

        Typeface mainfont = Typeface.createFromAsset(getActivity().getAssets(),Constant.GOTHAM);
        SetTypeFace.setAppFont(scrollPresentation,mainfont);

        mFirebase = FirebaseDatabase.getInstance();
        mAproposRef = mFirebase.getReference("ProposMAJ");

        Button button1 = (Button) view.findViewById(R.id.buttonPresentation1);
        Button button2 = (Button) view.findViewById(R.id.buttonPresentation2);
        Button button3 = (Button) view.findViewById(R.id.buttonPresentation3);
        mTextViewQsn = (TextView) view.findViewById(R.id.textViewPresentation1);
        mTextViewNv = (TextView) view.findViewById(R.id.textViewPresentation2);
        mSlide = view.findViewById(R.id.slider);

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add("https://media-cdn.tripadvisor.com/media/photo-s/07/50/f6/d1/john-s-burger.jpg");
        listName.add("JPG - Github");

        listUrl.add("https://img.20mn.fr/I3MMnj6MTK-2H8zKiq3Xjg/830x532_burger-black-og-vincent-boccara");
        listName.add("PNG - Android Studio");

        listUrl.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpzS2JniRWr_en1FiRMOO-WUIJih6Px4JEN3YiOq1__iRFk7Ao");
        listName.add("GIF - Disney");

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(listUrl.get(i));
            mSlide.addSlider(textSliderView);
        }
        mSlide.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlide.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlide.setDuration(3000);
        mSlide.addOnPageChangeListener(PresentationFragment.this);

        mSlide.setOnClickListener(this);

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
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlide.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case 0:
                Intent i = new Intent(getActivity(), fullImageActivity.class);
                i.putExtra("pic",0);
                startActivity(i);
                //((Activity) getActivity()).overridePendingTransition(0,0);
                break;
            case 1:
                Intent j= new Intent(getActivity(), fullImageActivity.class);
                j.putExtra("pic",1);
                startActivity(j);
                break;
            case 2:
                Intent k = new Intent(getActivity(), fullImageActivity.class);
                k.putExtra("pic",2);
                startActivity(k);
                break;
        }
    }
}

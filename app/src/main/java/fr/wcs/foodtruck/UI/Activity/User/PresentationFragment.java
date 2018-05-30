package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import fr.wcs.foodtruck.Model.SliderModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresentationFragment extends Fragment implements ViewPagerEx.OnPageChangeListener{

    private FirebaseDatabase mFirebase;
    private DatabaseReference mRefAbout;
    private TextView mTextViewQsn;
    private TextView mTextViewNv;
    private SliderLayout mSlide;
     SliderModel mSliderModel;
    private ArrayList<String > sliderArray = new ArrayList<>();

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
        mRefAbout = mFirebase.getReference();

        Button button1 = (Button) view.findViewById(R.id.buttonPresentation1);
        Button button2 = (Button) view.findViewById(R.id.buttonPresentation2);
        Button button3 = (Button) view.findViewById(R.id.buttonPresentation3);
        mTextViewQsn = (TextView) view.findViewById(R.id.textViewPresentation1);
        mTextViewNv = (TextView) view.findViewById(R.id.textViewPresentation2);
        mSlide = view.findViewById(R.id.slider);

        startSlider("Slider/");
        ValueAproposListener("ProposMAJ/QuiSommesNous");
        ValueNosValeursListener("ProposMAJ/NosValeurs");

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

        return view;
    }

    protected void startSlider(String child){
        for (int i = 1; i < 4; i++) {
            mRefAbout.child(child+"Slide"+ i + "/").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String conv = dataSnapshot.getValue(String .class);
                    sliderArray.add(conv);
                    if (sliderArray.size()>2){
                        getSlider();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    protected void getSlider(){
        for (int i = 0; i < sliderArray.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(sliderArray.get(i));
            mSlide.addSlider(textSliderView);
        }
        mSlide.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlide.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlide.setDuration(3000);
        mSlide.addOnPageChangeListener(PresentationFragment.this);
    }

    protected void ValueAproposListener(String child) {
        mRefAbout.child(child).addValueEventListener(new ValueEventListener() {
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

    protected void ValueNosValeursListener(String child) {
        mRefAbout.child(child).addValueEventListener(new ValueEventListener() {
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
}

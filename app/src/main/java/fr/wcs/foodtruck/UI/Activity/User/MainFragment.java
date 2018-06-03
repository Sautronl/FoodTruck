package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.Calendar;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Activity.Admin.AdminActivity;
import fr.wcs.foodtruck.Utils.CloseDay;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private long timeElapsed = 0L;
    private Calendar mCalendar;

    Integer[] id;
    int count;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().setTitle("Accueil");

        ScrollView mainUserLayout = view.findViewById(R.id.mainUserLayout);
        Typeface mainfont = Typeface.createFromAsset(getActivity().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(mainUserLayout,mainfont);

        ImageView menu = (ImageView) view.findViewById(R.id.menu);
        ImageView lieu = (ImageView) view.findViewById(R.id.lieu);
        ImageView presentation = (ImageView) view.findViewById(R.id.presentation);
        ImageView event = (ImageView) view.findViewById(R.id.event);
        ImageView contact = (ImageView) view.findViewById(R.id.contact);
        ImageView like = (ImageView) view.findViewById(R.id.like);
        ImageView star = (ImageView) view.findViewById(R.id.star);
        final ImageView logo = (ImageView) view.findViewById(R.id.logo);

        id = new Integer[]{R.id.menu,R.id.lieu,R.id.presentation,R.id.event,R.id.contact,R.id.like,R.id.star};

        if (count == 0){
            for (int i = 0; i < id.length; i++) {
                YoYo.with(Techniques.RollIn)
                        .duration(3000)
                        .playOn(view.findViewById(id[i]));
            }
            count++;
        }

        mCalendar = Calendar.getInstance();

        lieu.setOnClickListener(this);
        presentation.setOnClickListener(this);
        contact.setOnClickListener(this);
        menu.setOnClickListener(this);
        event.setOnClickListener(this);
        like.setOnClickListener(this);

        star.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timeElapsed = motionEvent.getDownTime();
                        break;
                    case MotionEvent.ACTION_UP:
                        timeElapsed = motionEvent.getEventTime() - timeElapsed;
                        if (timeElapsed >= 1000){
                            Intent admin = new Intent(getActivity(), AdminActivity.class);
                            startActivity(admin);
                        }
                        break;
                }
                return true;
            }
        });

        return view;
    }

    protected void fragmentTransaction(Fragment fragment){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
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
        final int daysatsun = mCalendar.get(Calendar.DAY_OF_WEEK);
        switch (v.getId()){
            case R.id.menu:
//                if (daysatsun == 7 || daysatsun == 1) {
//                    Intent intentClose = new Intent(getActivity(), CloseDay.class);
//                    startActivity(intentClose);
//                } else {
                    Fragment fragment = new MenuFragment();
                    fragmentTransaction(fragment);
//                }
                break;
            case R.id.event:
                Fragment fragmentBis = new EventFragment();
                fragmentTransaction(fragmentBis);
                break;
            case R.id.contact:
                Fragment frag = new ContactFragment();
                fragmentTransaction(frag);
                break;
            case R.id.lieu:
                Fragment fr = new MapListFragment();
                fragmentTransaction(fr);
                break;
            case R.id.presentation:
                Fragment fra = new PresentationFragment();
                fragmentTransaction(fra);
                break;
            case R.id.like:
                String FACEBOOK_URL = "https://www.facebook.com/samdonnefaim/";
                String FACEBOOK_PAGE_ID = "samdonnefaim";
                String lien;
                PackageManager packageManager = getActivity().getPackageManager();
                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) { //newer versions of fb app
                        lien = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                    } else { //older versions of fb app
                        lien = "fb://page/" + FACEBOOK_PAGE_ID;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    lien = FACEBOOK_URL;
                }
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                facebookIntent.setData(Uri.parse(lien));
                startActivity(facebookIntent);
        }
    }
}

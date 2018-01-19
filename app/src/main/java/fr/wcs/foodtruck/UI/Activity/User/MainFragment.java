package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;

import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.Calendar;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Activity.Admin.AdminActivity;
import fr.wcs.foodtruck.Utils.CloseDay;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ViewPagerEx.OnPageChangeListener{


    private long timeElapsed = 0L;
    private int mBackButtonCount = 0;
    private Calendar mCalendar;
    private SliderLayout mSlide;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().setTitle("Accueil");

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView menu = (ImageView) view.findViewById(R.id.menu);
        ImageView lieu = (ImageView) view.findViewById(R.id.lieu);
        ImageView presentation = (ImageView) view.findViewById(R.id.presentation);
        ImageView event = (ImageView) view.findViewById(R.id.event);
        ImageView contact = (ImageView) view.findViewById(R.id.contact);
        ImageView like = (ImageView) view.findViewById(R.id.like);
        ImageView star = (ImageView) view.findViewById(R.id.star);
        mSlide = view.findViewById(R.id.slider);
        final ImageView logo = (ImageView) view.findViewById(R.id.logo);

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
        //mSlide.setDuration(3000);
        mSlide.addOnPageChangeListener(MainFragment.this);

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

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        mCalendar = Calendar.getInstance();
        final int daysatsun = mCalendar.get(Calendar.DAY_OF_WEEK);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysatsun == 7 || daysatsun == 1) {
                    Intent intentClose = new Intent(getActivity(), CloseDay.class);
                    startActivity(intentClose);
                } else {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    Fragment fragment = new MenuFragment();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.container,fragment);
                    fragmentTransaction.commit();
                }
            }
        });


        presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Fragment fragment = new PresentationFragment();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container,fragment);
                fragmentTransaction.commit();
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Fragment fragment = new EventFragment();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container,fragment);
                fragmentTransaction.commit();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Fragment fragment = new ContactFragment();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container,fragment);
                fragmentTransaction.commit();
            }
        });

        lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Fragment fragment = new MapListFragment();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container,fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
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
    public void onPageSelected(final int position) {
//        Intent i = new Intent(getActivity(),fullImageActivity.class);
//        i.putExtra("id",position);
//        startActivity(i);

        mSlide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //int id = mSlide.getCurrentPosition();
                switch (position){
                    case  1:
                        Intent i = new Intent(getActivity(),fullImageActivity.class);
                        i.putExtra("id",position);
                        startActivity(i);
                        break;
                    case  2:
                        Intent j = new Intent(getActivity(),fullImageActivity.class);
                        j.putExtra("id",position);
                        startActivity(j);
                        break;
                    case  3:
                        Intent k = new Intent(getActivity(),fullImageActivity.class);
                        k.putExtra("id",position);
                        startActivity(k);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
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

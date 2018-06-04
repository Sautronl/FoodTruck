package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.arclayout.ArcLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import fr.wcs.foodtruck.Controllers.Singleton;
import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.CloseDay;
import fr.wcs.foodtruck.Utils.Constant;
import fr.wcs.foodtruck.Utils.SetTypeFace;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private FirebaseDatabase mFire;
    private DatabaseReference mDbRef,mRefJob;
    private DatabaseReference mDbRefCoor;
    private TextView mNomBurger;
    private TextView mDescriptionMenu,adressMenuUser;
    private TextView mAdress;
    private Calendar myCalendar;
   // private ImageView mImgplatMenu;
    private ProgressDialog mDialog;
    ScrollView scrollMenu;
    Boolean isOpen;
    Button mPrixButton;
    ArcLayout arcLayout;
    KenBurnsView kbvImg;

    Singleton mSingleton;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        getActivity().setTitle("Nos plats");

        mSingleton =Singleton.getsInstance();

      // ImageView backButton = (ImageView)view.findViewById(R.id.backButton);

        mFire = FirebaseDatabase.getInstance();
        mDbRef = mFire.getReference("menu/");
        mDbRefCoor = mFire.getReference("Coordonner");

        mNomBurger = (TextView) view.findViewById(R.id.burger);
        mDescriptionMenu = (TextView) view.findViewById(R.id.descriPlat);
        //mAdress = (TextView)view.findViewById(R.id.adress);
       // mImgplatMenu = (ImageView) view.findViewById(R.id.imgDuPlatMenu);
        mPrixButton =(Button) view.findViewById(R.id.buttonPrix);
         scrollMenu = (ScrollView) view.findViewById(R.id.scrollMenu);
        arcLayout = (ArcLayout) view.findViewById(R.id.diagonalLayout);
        kbvImg = (KenBurnsView) view.findViewById(R.id.imgPlatMenuXXL);
        adressMenuUser = (TextView)view.findViewById(R.id.adressMenuUser);

        Typeface mainfont = Typeface.createFromAsset(getActivity().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(scrollMenu,mainfont);



        //TextView voirFormules = (TextView)view.findViewById(R.id.totheformules);
//        SpannableString formuleSS = new SpannableString("Découvrez nos formules >");
//        formuleSS.setSpan(new UnderlineSpan(), 0, formuleSS.length(), 0);
//        voirFormules.setText(formuleSS);

        scrollMenu.setVisibility(View.GONE);
        Button reserver = (Button) view.findViewById(R.id.reserver);
//        TextView decouvrez = (TextView) view.findViewById(R.id.totheformules);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("Plat du jour");
        mDialog.setCancelable(false);
        mDialog.setMessage("En cours de chargement..");
        mDialog.show();

        checkDay();

        kbvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),fullImageActivity.class);
                startActivity(i);
            }
        });

        final Intent intent = new Intent(getActivity(), Commande.class);
        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
//
//        final Intent intent2 = new Intent(getActivity(), FormuleActivity.class);
//        decouvrez.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(intent2);
//            }
//        });
        return view;
    }


    protected void checkDay() {

        myCalendar = Calendar.getInstance();
        int dayD = myCalendar.get(Calendar.DAY_OF_WEEK);

        if (dayD == 2) {
            checkIsOpen("Lundi","menuLundi","1 Lundi/adrs");
        } else if (dayD == 3) {
            checkIsOpen("Mardi","menuMardi","2 Mardi/adrs");
        } else if (dayD == 4) {
            checkIsOpen("Mercredi","menuMercredi","3 Mercredi/adrs");
        } else if (dayD == 5) {
            checkIsOpen("Jeudi","menuJeudi","4 Jeudi/adrs");
        } else if (dayD == 6) {
            checkIsOpen("Vendredi","menuVendredi","5 Vendredi/adrs");
        } else if (dayD == 7) {
            checkIsOpenWeekendBeta("Samedi","menuVendredi","5 Vendredi/adrs");
        }else if (dayD == 1) {
            checkIsOpenWeekendBeta("Dimanche","menuVendredi","5 Vendredi/adrs");
        }
    }

    private void fragTransact(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = new CloseFragment();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void checkIsOpen(String dayJob, final String menuDay, final String AdrsDay){
        mRefJob = mFire.getReference("Avaible/");
        mRefJob.child(dayJob).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean isOpen2 = dataSnapshot.getValue(Boolean.class);
                isOpen = isOpen2;
                if (isOpen!= null && isOpen) {
                    mDbRef.child(menuDay+"/").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            MajPlatDuJour majP = dataSnapshot.getValue(MajPlatDuJour.class);
                            mNomBurger.setText(majP.getNomPlat());
                            mDescriptionMenu.setText(majP.getDescPlat());
                            Glide.with(getActivity()).load(majP.getUrlImg()).into(kbvImg);
                            mPrixButton.setText("Prix\n" + majP.getPrix());
                            mDialog.dismiss();
                            scrollMenu.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });                    if (mDbRefCoor != mDbRef) {
                        majEmplacement(AdrsDay,getView());
                        adressMenuUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent(4);
                            }
                        });
                    }
                }else{
                    mDialog.dismiss();
                    fragTransact();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkIsOpenWeekendBeta(String dayJobWeekend, final String menuDayWeekend, final String AdrsDayWeekend){
        mRefJob = mFire.getReference("Avaible/");
        mRefJob.child(dayJobWeekend).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean isOpen2 = dataSnapshot.getValue(Boolean.class);
                isOpen = isOpen2;
                if (isOpen!= null && isOpen) {
                    mDbRef.child(menuDayWeekend+"/").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            MajPlatDuJour majP = dataSnapshot.getValue(MajPlatDuJour.class);
                            mNomBurger.setText(majP.getNomPlat());
                            mDescriptionMenu.setText(majP.getDescPlat());
                            Glide.with(getActivity()).load(majP.getUrlImg()).into(kbvImg);
                            mPrixButton.setText("Prix\n" + majP.getPrix());
                            mDialog.dismiss();
                            scrollMenu.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if (mDbRefCoor != mDbRef) {
                        majEmplacement(AdrsDayWeekend,getView());
                        adressMenuUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent(4);
                            }
                        });
                    }
                }else{
                    mDialog.dismiss();
                    fragTransact();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void majEmplacement(String emp,View view){
        adressMenuUser = (TextView)view.findViewById(R.id.adressMenuUser);
        mDbRefCoor.child(emp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ad = dataSnapshot.getValue(String.class);
                SpannableString adrsMenu = new SpannableString(ad);
                adrsMenu.setSpan(new UnderlineSpan(), 0, adrsMenu.length(), 0);
                adressMenuUser.setText(adrsMenu);            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void intent(int x){
        Intent mapdetail = new Intent(getActivity(), MapsActivity.class);
        mapdetail.putExtra("jourMarkeur", x);
        startActivity(mapdetail);
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

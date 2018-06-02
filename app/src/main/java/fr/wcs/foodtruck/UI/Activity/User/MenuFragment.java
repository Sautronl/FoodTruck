package fr.wcs.foodtruck.UI.Activity.User;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    private DatabaseReference mDbRef;
    private DatabaseReference mDbRefCoor;
    private TextView mNomBurger;
    private TextView mDescriptionMenu;
    private TextView mAdress;
    private Calendar myCalendar;
    private ImageView mImgplatMenu;
    private ProgressDialog mDialog;
    Button mPrixButton;

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

        ImageView backButton = (ImageView)view.findViewById(R.id.backButton);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("Plat du jour");
        mDialog.setCancelable(false);
        mDialog.setMessage("En cours de chargement..");
        mDialog.show();

        mFire = FirebaseDatabase.getInstance();
        mDbRef = mFire.getReference("menu");
        mDbRefCoor = mFire.getReference("Coordonner");

        mNomBurger = (TextView) view.findViewById(R.id.burger);
        mDescriptionMenu = (TextView) view.findViewById(R.id.descriPlat);
        mAdress = (TextView)view.findViewById(R.id.adress);
        mImgplatMenu = (ImageView) view.findViewById(R.id.imgDuPlatMenu);
        mPrixButton =(Button) view.findViewById(R.id.buttonPrix);
        ScrollView scrollMenu = (ScrollView) view.findViewById(R.id.scrollMenu);

        Typeface mainfont = Typeface.createFromAsset(getActivity().getAssets(), Constant.GOTHAM);
        SetTypeFace.setAppFont(scrollMenu,mainfont);

        TextView voirFormules = (TextView)view.findViewById(R.id.totheformules);
        SpannableString formuleSS = new SpannableString("Découvrez nos formules >");
        formuleSS.setSpan(new UnderlineSpan(), 0, formuleSS.length(), 0);
        voirFormules.setText(formuleSS);

        Button reserver = (Button) view.findViewById(R.id.reserver);
        TextView decouvrez = (TextView) view.findViewById(R.id.totheformules);

        checkDay();

        mImgplatMenu.setOnClickListener(new View.OnClickListener() {
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

        final Intent intent2 = new Intent(getActivity(), FormuleActivity.class);
        decouvrez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

        return view;
    }


    protected void checkDay() {

        myCalendar = Calendar.getInstance();
        int dayD = myCalendar.get(Calendar.DAY_OF_WEEK);

        if (dayD == 2) {
            mSingleton.loadMenu("menuLundi", mNomBurger, mDescriptionMenu, mPrixButton, mImgplatMenu, getActivity(), mDialog);
            if (mDbRefCoor != mDbRef) {
                majEmplacement("1 Lundi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(0);
                    }
                });
            }
        } else if (dayD == 3) {
            mSingleton.loadMenu("menuMardi", mNomBurger, mDescriptionMenu, mPrixButton, mImgplatMenu, getActivity(), mDialog);
            if (mDbRefCoor != mDbRef) {
                majEmplacement("2 Mardi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(1);
                    }
                });
            }
        } else if (dayD == 4) {
            mSingleton.loadMenu("menuMercredi", mNomBurger, mDescriptionMenu, mPrixButton, mImgplatMenu, getActivity(), mDialog);
            if (mDbRefCoor != mDbRef) {
                majEmplacement("3 Mercredi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(2);
                    }
                });
            }
        } else if (dayD == 5) {
            mSingleton.loadMenu("menuJeudi", mNomBurger, mDescriptionMenu, mPrixButton, mImgplatMenu, getActivity(), mDialog);
            if (mDbRefCoor != mDbRef) {
                majEmplacement("4 Jeudi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(3);
                    }
                });
            }
        } else if (dayD == 6) {
            mSingleton.loadMenu("menuVendredi", mNomBurger, mDescriptionMenu, mPrixButton, mImgplatMenu, getActivity(), mDialog);
            if (mDbRefCoor != mDbRef) {
                majEmplacement("5 Vendredi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(4);
                    }
                });
            }
        } else if (dayD == 7) {
            mSingleton.loadMenu("menuVendredi", mNomBurger, mDescriptionMenu, mPrixButton, mImgplatMenu, getActivity(), mDialog);
            if (mDbRefCoor != mDbRef) {
                majEmplacement("5 Vendredi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(4);
                    }
                });
            }
        }else if (dayD == 1) {
            mSingleton.loadMenu("menuVendredi", mNomBurger, mDescriptionMenu, mPrixButton, mImgplatMenu, getActivity(), mDialog);
            if (mDbRefCoor != mDbRef) {
                majEmplacement("5 Vendredi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(4);
                    }
                });
            }
//            else {
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Fragment fragment = new CloseFragment();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
        }
    }

    protected void majEmplacement(String emp){
        mDbRefCoor.child(emp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ad = dataSnapshot.getValue(String.class);
                mAdress.setText(ad);
            }

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

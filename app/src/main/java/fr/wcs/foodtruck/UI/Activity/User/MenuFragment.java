package fr.wcs.foodtruck.UI.Activity.User;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Utils.CloseDay;

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

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);


        ImageView backButton = (ImageView)view.findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent back = new Intent(MenuDuJour(), MainActivity.class);
//                startActivity(back);
//            }
//        });

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

//        Typeface mainfont = Typeface.createFromAsset(getAssets(), Constant.GOTHAM);
//        SetTypeFace.setAppFont(scrollMenu,mainfont);

        TextView voirFormules = (TextView)view.findViewById(R.id.totheformules);
        SpannableString formuleSS = new SpannableString("Découvrez nos formules >");
        formuleSS.setSpan(new UnderlineSpan(), 0, formuleSS.length(), 0);
        voirFormules.setText(formuleSS);

        Button reserver = (Button) view.findViewById(R.id.reserver);
        TextView decouvrez = (TextView) view.findViewById(R.id.totheformules);

        checkDay();

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
            majMenu("menuLundi");
            if (mDbRefCoor != mDbRef) {
                majEmplacement("1 Lundi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mapdetail = new Intent(getActivity(), MapsActivity.class);
                        mapdetail.putExtra("jourMarkeur", 0);
                        startActivity(mapdetail);
                    }
                });
            }
        }else if (dayD == 3) {
            majMenu("menuMardi");
            if (mDbRefCoor != mDbRef) {
                majEmplacement("2 Mardi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mapdetail = new Intent(getActivity(), MapsActivity.class);
                        mapdetail.putExtra("jourMarkeur", 1);
                        startActivity(mapdetail);
                    }
                });
            }
        }else if (dayD == 4) {
            majMenu("menuMercredi");
            if (mDbRefCoor != mDbRef) {
                majEmplacement("3 Mercredi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mapdetail = new Intent(getActivity(), MapsActivity.class);
                        mapdetail.putExtra("jourMarkeur", 2);
                        startActivity(mapdetail);
                    }
                });
            }
        }else if (dayD == 5) {
            majMenu("menuJeudi");
            if (mDbRefCoor != mDbRef) {
                majEmplacement("4 Jeudi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mapdetail = new Intent(getActivity(), MapsActivity.class);
                        mapdetail.putExtra("jourMarkeur", 3);
                        startActivity(mapdetail);
                    }
                });
            }
        }else if (dayD == 6) {
            majMenu("menuVendredi");
            if (mDbRefCoor != mDbRef) {
                majEmplacement("5 Vendredi/adrs");
                mAdress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mapdetail = new Intent(getActivity(), MapsActivity.class);
                        mapdetail.putExtra("jourMarkeur", 4);
                        startActivity(mapdetail);
                    }
                });
            }
        }
        else{
            Intent intentClose = new Intent(getActivity(), CloseDay.class);
            startActivity(intentClose);
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

    private void majMenu(String jour){
        mDbRef.child(jour).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MajPlatDuJour maj = dataSnapshot.getValue(MajPlatDuJour.class);
                mNomBurger.setText(maj.getNomPlat());
                mDescriptionMenu.setText(maj.getDescPlat());
                Glide.with(getContext()).load(maj.getUrlImg()).into(mImgplatMenu);
                mPrixButton.setText("Prix\n" +maj.getPrix());
                mDialog.dismiss();
                //mPrixButton.setTextColor(getResources().getColor(R.color.black));
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

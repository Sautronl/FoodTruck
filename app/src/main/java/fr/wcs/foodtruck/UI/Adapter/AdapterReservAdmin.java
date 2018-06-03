package fr.wcs.foodtruck.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.Model.ReservationModels;

/**
 * Created by apprenti on 23/10/17.
 */

public class AdapterReservAdmin extends RecyclerView.Adapter<AdapterReservAdmin.ViewHolder>{

    private Activity activity;
    private List<ReservationModels> reserve;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public AdapterReservAdmin(Activity activity, List<ReservationModels> reserve){
        this.activity = activity;
        this.reserve = reserve;
    }

    @Override
    public AdapterReservAdmin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_admin_reservation_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterReservAdmin.ViewHolder holder, int position) {
        holder.display(reserve.get(position));
    }

    @Override
    public int getItemCount() {
        return reserve.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomItem,boissonItem,dessertItem,nitem,titem,heureItem;
        Button removeSelectRes;

        public ViewHolder(View itemView) {
            super(itemView);
            nitem = itemView.findViewById(R.id.nItem);
            titem = itemView.findViewById(R.id.tItem);
            heureItem = itemView.findViewById(R.id.heureItem);
            nomItem = itemView.findViewById(R.id.nomBurgerItem);
            boissonItem = itemView.findViewById(R.id.boissonItem);
            dessertItem = itemView.findViewById(R.id.dessertItem);
            removeSelectRes = itemView.findViewById(R.id.removeSelectRes);
        }

        public void display(final ReservationModels reserve ){

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference("Réservation/");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeSelectRes.setVisibility(View.VISIBLE);
                    removeSelectRes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDatabaseReference.child(reserve.getId()).removeValue();
                        }
                    });
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeSelectRes.setVisibility(View.GONE);
                            display(reserve);
                        }
                    });
                }
            });
            nitem.setText(reserve.getNomReserv());
            titem.setText(reserve.getNumTelReserv());
            heureItem.setText(reserve.getHoraire());
            nomItem.setText(reserve.getNomBurger());
            boissonItem.setText(reserve.getChoixBoisson());
            dessertItem.setText(reserve.getChoixDessert());
        }
    }
}
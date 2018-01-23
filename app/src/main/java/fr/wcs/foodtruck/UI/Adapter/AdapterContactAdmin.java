package fr.wcs.foodtruck.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fr.wcs.foodtruck.Model.ContactAdminModel;
import fr.wcs.foodtruck.R;

public class AdapterContactAdmin extends RecyclerView.Adapter<AdapterContactAdmin.ViewHolder> {
     private Activity activity;
     private List<ContactAdminModel> listCont;
     private FirebaseDatabase mFire;
     private DatabaseReference mRef;

    public AdapterContactAdmin(Activity activity, List<ContactAdminModel> listCont) {
        this.activity = activity;
        this.listCont = listCont;
    }

    @Override
    public AdapterContactAdmin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_admin_contact_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterContactAdmin.ViewHolder holder, int position) {
        holder.display(listCont.get(position));
    }

    @Override
    public int getItemCount() {
        return listCont.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNom;
        TextView textViewTel;
        TextView textViewSujet;
        TextView textViewMessage;
        Button removeButtonContactAdm;

        public ViewHolder(View itemView) {
            super(itemView);

             textViewNom = itemView.findViewById(R.id.lvNomContactAdmin);
             textViewTel = itemView.findViewById(R.id.lvTelContactAdmin);
             textViewSujet = itemView.findViewById(R.id.lvSujetContactAdmin);
             textViewMessage = itemView.findViewById(R.id.lvMessageContactAdmin);
             removeButtonContactAdm = itemView.findViewById(R.id.removeButtonContactAdm);
        }

        public void display(final ContactAdminModel contact){

            mFire = FirebaseDatabase.getInstance();
            mRef = mFire.getReference("contact/");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeButtonContactAdm.setVisibility(View.VISIBLE);
                    removeButtonContactAdm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mRef.child(contact.getId()).removeValue();
                            notifyDataSetChanged();
                        }
                    });
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeButtonContactAdm.setVisibility(View.GONE);
                            display(contact);
                        }
                    });
                }
            });
            textViewNom.setText(contact.getNom());
            textViewTel.setText(contact.getTel());
            textViewSujet.setText(contact.getSujet());
            textViewMessage.setText(contact.getMessage());
        }
    }
}
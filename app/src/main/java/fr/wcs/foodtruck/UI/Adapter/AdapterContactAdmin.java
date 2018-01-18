package fr.wcs.foodtruck.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.wcs.foodtruck.Model.ContactAdminModel;
import fr.wcs.foodtruck.R;

public class AdapterContactAdmin extends RecyclerView.Adapter<AdapterContactAdmin.ViewHolder> {
     private Activity activity;
     private List<ContactAdminModel> listCont;
     private OnItemClickListener listener = null;

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
        holder.display(holder,listCont.get(position),position,listener);
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

        public ViewHolder(View itemView) {
            super(itemView);

             textViewNom = itemView.findViewById(R.id.lvNomContactAdmin);
             textViewTel = itemView.findViewById(R.id.lvTelContactAdmin);
             textViewSujet = itemView.findViewById(R.id.lvSujetContactAdmin);
             textViewMessage = itemView.findViewById(R.id.lvMessageContactAdmin);
        }

        public void display(final AdapterContactAdmin.ViewHolder holder, final ContactAdminModel contact,final int index,final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onItemClick(holder,contact,index);
                        getAdapterPosition();
                    }
                }
            });
            textViewNom.setText(contact.getNom());
            textViewTel.setText(contact.getTel());
            textViewSujet.setText(contact.getSujet());
            textViewMessage.setText(contact.getMessage());
        }
    }

    public void setOnItemClick(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(ViewHolder viewHolder,ContactAdminModel contact,int index);
    }

//    @Override
//    public int getCount() {
//        return listCont.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listCont.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemView = inflater.inflate(R.layout.activity_admin_contact_item,null);
//
//        TextView textViewNom = (TextView) itemView.findViewById(R.id.lvNomContactAdmin);
//        TextView textViewTel = (TextView) itemView.findViewById(R.id.lvTelContactAdmin);
//        TextView textViewSujet = (TextView) itemView.findViewById(R.id.lvSujetContactAdmin);
//        TextView textViewMessage = (TextView) itemView.findViewById(R.id.lvMessageContactAdmin);
//
//        textViewNom.setText(listCont.get(position).getNom());
//        textViewTel.setText(listCont.get(position).getTel());
//        textViewSujet.setText(listCont.get(position).getSujet());
//        textViewMessage.setText(listCont.get(position).getMessage());
//
//        return itemView;
//    }
}
package fr.wcs.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterContactAdmin extends BaseAdapter {
     private Activity activity;
     private List<ContactAdminModel> listCont;
     private LayoutInflater inflater;

    public AdapterContactAdmin(Activity activity, List<ContactAdminModel> listCont) {
        this.activity = activity;
        this.listCont = listCont;
    }

    @Override
    public int getCount() {
        return listCont.size();
    }

    @Override
    public Object getItem(int position) {
        return listCont.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_admin_contact_item,null);

        TextView textViewNom = (TextView) itemView.findViewById(R.id.lvNomContactAdmin);
        TextView textViewTel = (TextView) itemView.findViewById(R.id.lvTelContactAdmin);
        TextView textViewSujet = (TextView) itemView.findViewById(R.id.lvSujetContactAdmin);
        TextView textViewMessage = (TextView) itemView.findViewById(R.id.lvMessageContactAdmin);

        textViewNom.setText(listCont.get(position).getNom());
        textViewTel.setText(listCont.get(position).getTel());
        textViewSujet.setText(listCont.get(position).getSujet());
        textViewMessage.setText(listCont.get(position).getMessage());

        return itemView;
    }
}
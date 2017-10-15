package fr.wcs.foodtruck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class AdapterContactAdmin extends BaseAdapter {
    private Context context;
    private ArrayList<ContactAdminModel> items;

    public AdapterContactAdmin(Context context, ArrayList<ContactAdminModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.textelistcontactadmin, parent, false);
        }


        ContactAdminModel currentItem = (ContactAdminModel) getItem(position);


        TextView textViewnom = (TextView)
                convertView.findViewById(R.id.lvNomContactAdmin);
        TextView textViewObjet = (TextView)
                convertView.findViewById(R.id.lvObjetContactAdmin);



        textViewnom.setText(currentItem.getNom().toString());
        textViewObjet.setText(currentItem.getTel().toString());


        return convertView;
    }

}

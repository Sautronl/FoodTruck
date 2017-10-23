package fr.wcs.foodtruck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Code: il faut franchir les maquignons.
 */

public class AdapterListLocalisation extends ArrayAdapter<ListLocalisationModel> {

    public AdapterListLocalisation(Context context, List<ListLocalisationModel> jour) {
        super(context, 0, jour);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_admin_list_emplacement_item,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.jour = convertView.findViewById(R.id.jours);;
            convertView.setTag(viewHolder);
        }

        ListLocalisationModel jour = getItem(position);
        viewHolder.jour.setText(jour.getJour());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView jour;
        public TextView text;


    }
}

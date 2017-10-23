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

public class AdapterListEmplacement extends ArrayAdapter<ListJourEmplacementModel> {

    public AdapterListEmplacement(Context context, List<ListJourEmplacementModel> jour) {
        super(context, 0, jour);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.text_list_emplacement,parent, false);
        }

        EmplacementViewHolder viewHolder = (EmplacementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new EmplacementViewHolder();
            viewHolder.jourEmplacement = convertView.findViewById(R.id.jourEmplacement);
            viewHolder.jourAdresse = convertView.findViewById(R.id.jourAdresse);

            convertView.setTag(viewHolder);
        }

        ListJourEmplacementModel jour = getItem(position);
        viewHolder.jourEmplacement.setText(jour.getJour());
        viewHolder.jourAdresse.setText(jour.getAdresse());

        return convertView;
    }

    private class EmplacementViewHolder {
        public TextView jourEmplacement;
        public TextView jourAdresse;


    }
}


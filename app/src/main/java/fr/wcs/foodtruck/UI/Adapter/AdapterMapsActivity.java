package fr.wcs.foodtruck.UI.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import fr.wcs.foodtruck.Model.MapsActivityModel;
import fr.wcs.foodtruck.R;

/**
 * Code: il faut franchir les maquignons.
 */

public class AdapterMapsActivity extends ArrayAdapter<MapsActivityModel> {

    public AdapterMapsActivity(Context context, List<MapsActivityModel> coord) {
        super(context, 0, coord);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_maps,parent, false);
        }

        MapsViewHolder viewHolder = (MapsViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MapsViewHolder();

            convertView.setTag(viewHolder);
        }

        MapsActivityModel coord = getItem(position);
        viewHolder.lat.parseDouble(String.valueOf(coord.getLat()));
        viewHolder.lon.parseDouble(String.valueOf(coord.getLon()));

        return convertView;
    }

    private class MapsViewHolder {
        public Double lat;
        public Double lon;


    }
}


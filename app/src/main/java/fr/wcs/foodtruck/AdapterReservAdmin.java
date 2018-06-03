package fr.wcs.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by apprenti on 23/10/17.
 */

public class AdapterReservAdmin extends BaseAdapter{

    private Activity activity;
    private List<ReservationModels> reserve;
    private LayoutInflater inflater;

    public AdapterReservAdmin(Activity activity, List<ReservationModels> reserve){
        this.activity = activity;
        this.reserve = reserve;
    }

    @Override
    public int getCount() {
        return reserve.size();
    }

    @Override
    public Object getItem(int i) {
        return reserve.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.activity_admin_reservation_item,null);

        TextView nitem = (TextView)item.findViewById(R.id.nItem);
        TextView titem = (TextView)item.findViewById(R.id.tItem);
        TextView heureItem = (TextView)item.findViewById(R.id.heureItem);

        nitem.setText(reserve.get(i).getNomReserv());
        titem.setText(reserve.get(i).getNumTelReserv());
        heureItem.setText(reserve.get(i).getHoraire());

        return item;
    }
}
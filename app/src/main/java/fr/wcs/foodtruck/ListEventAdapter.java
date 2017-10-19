package fr.wcs.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListEventAdapter extends BaseAdapter {
    private Activity activity;
    private List<EventModel> lstEvents;
    private LayoutInflater inflater;


    public ListEventAdapter(Activity activity, List<EventModel> lstEvents) {
        this.activity = activity;
        this.lstEvents = lstEvents;
    }

    @Override
    public int getCount() {
        return lstEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return lstEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_event_item,null);

        TextView txtName = (TextView)itemView.findViewById(R.id.list_name);
        TextView txtDetail = (TextView)itemView.findViewById(R.id.list_details);
        TextView txtDate = (TextView)itemView.findViewById(R.id.list_date);

        txtName.setText(lstEvents.get(i).getName());
        txtDetail.setText(lstEvents.get(i).getDetails());
        if (lstEvents.get(i).getDate() != null){
            txtDate.setText(lstEvents.get(i).getDate().toString());
        }


        return itemView;
    }
}

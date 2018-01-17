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

import fr.wcs.foodtruck.Model.EventModel;
import fr.wcs.foodtruck.R;

public class ListEventAdapter extends RecyclerView.Adapter<ListEventAdapter.ViewHolder> {
    private Activity activity;
    private List<EventModel> lstEvents;
    private LayoutInflater inflater;


    public ListEventAdapter(Activity activity, List<EventModel> lstEvents) {
        this.activity = activity;
        this.lstEvents = lstEvents;
    }

    @Override
    public ListEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_event_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListEventAdapter.ViewHolder holder, int position) {
        holder.display(lstEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return lstEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
//        TextView txtDetail;
//        TextView txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.list_name);
//            txtDetail = itemView.findViewById(R.id.list_details);
//            txtDate = itemView.findViewById(R.id.list_date);
        }

        public void display(EventModel eventModel){
            txtName.setText(eventModel.getName());
//            txtDetail.setText(eventModel.getDetails());
//            txtDate.setText(eventModel.getDate());
        }
    }

}


//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemView = inflater.inflate(R.layout.list_event_item,null);
//
//        TextView txtName = (TextView)itemView.findViewById(R.id.list_name);
//        TextView txtDetail = (TextView)itemView.findViewById(R.id.list_details);
//        TextView txtDate = (TextView)itemView.findViewById(R.id.list_date);
//
//        txtName.setText(lstEvents.get(i).getName());
//        txtDetail.setText(lstEvents.get(i).getDetails());
//        if (lstEvents.get(i).getDate() != null){
//            txtDate.setText(lstEvents.get(i).getDate().toString());
//        }
//        return itemView;
//    }
//}


//<TextView
//            android:id="@+id/list_date"
//                    android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:textColor="@color/blanc"
//                    android:layout_marginLeft="20dp"
//                    android:layout_marginRight="20dp"
//                    android:text="Date"
//                    android:layout_below="@+id/list_name"
//                    android:textStyle="italic"
//                    android:textSize="18sp" />
//
//<TextView
//            android:id="@+id/list_details"
//                    android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:layout_below="@id/list_date"
//                    android:layout_marginBottom="10dp"
//                    android:layout_marginLeft="20dp"
//                    android:layout_marginRight="20dp"
//                    android:text="Details"
//                    android:textColor="@color/blanc"
//                    android:textSize="18sp" />
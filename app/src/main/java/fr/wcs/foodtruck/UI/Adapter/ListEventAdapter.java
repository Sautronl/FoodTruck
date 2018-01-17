package fr.wcs.foodtruck.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.wcs.foodtruck.Model.EventModel;
import fr.wcs.foodtruck.R;
import fr.wcs.foodtruck.UI.Activity.User.DetailEventFragment;
import fr.wcs.foodtruck.UI.Activity.User.DrawActivity;

public class ListEventAdapter extends RecyclerView.Adapter<ListEventAdapter.ViewHolder> {
    private Activity activity;
    private List<EventModel> lstEvents;
    private OnItemClikSelected listener;

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
        holder.display(lstEvents.get(position),activity);
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

        public void display(final EventModel eventModel, final Activity activity){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = ((DrawActivity)activity).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new DetailEventFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("detailEvent",eventModel);
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.container,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
            txtName.setText(eventModel.getName());
//            txtDetail.setText(eventModel.getDetails());
//            txtDate.setText(eventModel.getDate());
        }
    }

    public void setOnItemClick(OnItemClikSelected listener){
        this.listener = listener;
    }

    public interface OnItemClikSelected{
        void onItemClick(int index);
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



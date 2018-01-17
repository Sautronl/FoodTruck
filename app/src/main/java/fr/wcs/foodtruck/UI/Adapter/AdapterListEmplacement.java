package fr.wcs.foodtruck.UI.Adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import java.util.List;

import fr.wcs.foodtruck.Model.ListJourEmplacementModel;
import fr.wcs.foodtruck.R;

/**
 * Code: il faut franchir les maquignons.
 */

public class AdapterListEmplacement extends RecyclerView.Adapter<AdapterListEmplacement.ViewHolder> {

    private List<ListJourEmplacementModel> mListJour;
    private Activity activity;
    private OnItemSelected listener;

    public AdapterListEmplacement(List<ListJourEmplacementModel> mListJour,Activity activity) {
        this.mListJour = mListJour;
        this.activity = activity;
    }

    @Override
    public AdapterListEmplacement.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_emplacement_item, parent, false);

        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(AdapterListEmplacement.ViewHolder holder, int position) {
        holder.display(mListJour.get(position),listener,position);
    }

    @Override
    public int getItemCount() {
        return mListJour.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView jourAdresse;
        TextView jourEmplacement;

        public ViewHolder(View itemView) {
            super(itemView);
            jourAdresse = itemView.findViewById(R.id.jourAdresse);
            jourEmplacement = itemView.findViewById(R.id.jourEmplacement);
        }
        public void display(final ListJourEmplacementModel mListJour, final OnItemSelected listener, final int position){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                }
            });
                jourAdresse.setText(mListJour.getAdresse());
                jourEmplacement.setText(mListJour.getJour());
            }
        }

    public void setOnItemClick(OnItemSelected listener){
        this.listener = listener;
    }

    public interface OnItemSelected{
        void onItemClick(int index);
    }
}


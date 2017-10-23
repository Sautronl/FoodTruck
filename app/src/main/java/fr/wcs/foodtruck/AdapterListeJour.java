package fr.wcs.foodtruck;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by apprenti on 16/10/17.
 */

public class AdapterListeJour extends BaseAdapter {
    private Activity activity;
    private List<ListeJourModel> liste_jour;
    private LayoutInflater inflater;

    public AdapterListeJour(Activity activity, List<ListeJourModel> liste_jour){
        this.activity = activity;
        this.liste_jour = liste_jour;
    }

    @Override
    public int getCount() {
        return liste_jour.size();
    }

    @Override
    public Object getItem(int i) {
        return liste_jour.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

      /*  inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_liste_menu_du_jour_item,null);

        TextView lundi = (TextView) itemView.findViewById(R.id.lundi);
        lundi.setText(liste_jour.get(i).getDay());

        return itemView;*/
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.activity_liste_menu_du_jour_item,parent, false);
            viewHolder = new ViewHolder(convertView); //si vue actuelle pas initialiser alors en creer une.
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // sinon recuperer le TAG.
        }

        ListeJourModel currentItem = (ListeJourModel) getItem(i);
        viewHolder.dayO.setText(currentItem.getDay());

        return convertView;

    }
    private class ViewHolder {
        TextView dayO;


        public ViewHolder(View view) {
            dayO = (TextView)view.findViewById(R.id.lundi);
        }
    }
}



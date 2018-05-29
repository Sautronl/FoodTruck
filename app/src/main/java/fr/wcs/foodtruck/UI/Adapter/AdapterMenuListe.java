package fr.wcs.foodtruck.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fr.wcs.foodtruck.Model.MajPlatDuJour;
import fr.wcs.foodtruck.Model.ReservationModels;
import fr.wcs.foodtruck.R;

public class AdapterMenuListe extends RecyclerView.Adapter<AdapterMenuListe.ViewHolder> {

    private Context context;
    private ArrayList<MajPlatDuJour> menuDisplay;
    private OnItemClickListener listener = null;

    public AdapterMenuListe(Context context, ArrayList<MajPlatDuJour> menuDisplay) {
        this.context = context;
        this.menuDisplay = menuDisplay;
    }

    @Override
    public AdapterMenuListe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.liste_menureserve_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.display(menuDisplay.get(position),context,listener,position);
    }

    @Override
    public int getItemCount() {
        return menuDisplay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgMenuReserve;
        TextView prixReserve,nomReserve,descriptionReserve;
        CheckBox checkBox;
        ArrayList<ReservationModels> panier = new ArrayList<>();

        public ViewHolder(View itemView) {
            super(itemView);

            imgMenuReserve = (ImageView) itemView.findViewById(R.id.menu_img_reserve);
            prixReserve = (TextView) itemView.findViewById(R.id.menu_prix_reserve);
            nomReserve = (TextView) itemView.findViewById(R.id.nom_burger_reserve);
            descriptionReserve = (TextView) itemView.findViewById(R.id.menu_description_reserve);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxCommande);

        }

        public void display(final MajPlatDuJour menu, final Context context,OnItemClickListener listener,int position){

            if (checkBox.isChecked()){

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onItemClick(position,menu,context,checkBox);
                    }
//                    menu = new MajPlatDuJour(menu.getNomPlat(),menu.getDescPlat(),menu.getUrlImg(),menu.getPrix());
//                    FragmentManager fragmentManager = ((DrawActivity)activity).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Fragment fragment = new DetailEventFragment();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("detailEvent",eventModel);
//                    fragment.setArguments(bundle);
//                    fragmentTransaction.replace(R.id.container,fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                    Bundle bundle = new Bundle();
//                    Intent intent = new Intent(context,ResumeActivity.class);
//                    intent.putExtra("bundle", bundle);
//                    context.startActivity(intent);
//
//                    new AlertDialog.Builder(context)
//                            .setTitle(menu.getNomPlat())
//                            .setMessage("Voulez vous ce Burger?")
//                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    reserver.add(menu);
//                                }
//                            }).setNegativeButton("Non", null).show();
                }
            });
            Glide.with(context).load(menu.getUrlImg()).into(imgMenuReserve);
            prixReserve.setText(menu.getPrix());
            nomReserve.setText(menu.getNomPlat());
            descriptionReserve.setText(menu.getDescPlat());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
         void onItemClick(int position,MajPlatDuJour majPlatDuJour,Context context,CheckBox checkBoxCommande);
    }
}

package fr.wcs.foodtruck;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

//import fr.wcs.foodtruck.AdminMenuDuJour.AdminMenuDuJourJeudi;

public class AdminListeMenuDuJour extends AppCompatActivity {

    /*int[] IMAGES = {R.drawable.crayon_orange,R.drawable.crayon_orange,R.drawable.crayon_orange,
            R.drawable.crayon_orange,R.drawable.crayon_orange,};
    String[] JOURS = {"LUNDI","MARDI","MERCREDI","JEUDI","VENDREDI"};*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_menu_du_jour);



        ImageView lundiEdit = (ImageView) findViewById(R.id.penLundi);
        lundiEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListeMenuDuJour.this, AdminMenuDuJourLundi.class);
                startActivity(intent);
            }
        });

        ImageView mardiEdit = (ImageView) findViewById(R.id.penMardi);
        mardiEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListeMenuDuJour.this, AdminMenuDuJourMardi.class);
                startActivity(intent);
            }
        });

        ImageView mercrediEdit = (ImageView) findViewById(R.id.penMercredi);
        mercrediEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListeMenuDuJour.this, AdminMenuDuJourMercredi.class);
                startActivity(intent);
            }
        });

        ImageView jeudiEdit = (ImageView) findViewById(R.id.penJeudi);
        jeudiEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListeMenuDuJour.this, AdminMenuDuJourJeudi.class);
                startActivity(intent);
            }
        });


        ImageView vendrediEdit = (ImageView) findViewById(R.id.penVendredi);
        vendrediEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListeMenuDuJour.this, AdminMenuDuJourVendredi.class);
                startActivity(intent);
            }
        });





        //CustomAdapter customAdapter = new CustomAdapter();
        //listView.setAdapter(customAdapter);
    }

    /*class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.activity_liste_menu_du_jour, null);
            ImageView imageViewLundi = (ImageView)view.findViewById(R.id.penLundi);
            ImageView imageViewMardi = (ImageView)view.findViewById(R.id.penMardi);
            ImageView imageViewMercredi = (ImageView)view.findViewById(R.id.penMercredi);
            ImageView imageViewJeudi = (ImageView)view.findViewById(R.id.penJeudi);
            ImageView imageViewVendredi = (ImageView)view.findViewById(R.id.penVendredi);

            TextView texViewLundi = (TextView)findViewById(R.id.listeLundi);
            TextView texViewMardi = (TextView)findViewById(R.id.listeMardi);
            TextView texViewMercredi = (TextView)findViewById(R.id.listeMercredi);
            TextView texViewJeudi = (TextView)findViewById(R.id.listeJeudi);
            TextView texViewVendredi = (TextView)findViewById(R.id.listeVendredi);

            imageViewLundi.setImageResource(IMAGES[i]);
            imageViewMardi.setImageResource(IMAGES[i]);
            imageViewMercredi.setImageResource(IMAGES[i]);
            imageViewJeudi.setImageResource(IMAGES[i]);
            imageViewVendredi.setImageResource(IMAGES[i]);

            texViewLundi.setText(JOURS[i]);
            texViewMardi.setText(JOURS[i]);
            texViewMercredi.setText(JOURS[i]);
            texViewJeudi.setText(JOURS[i]);
            texViewVendredi.setText(JOURS[i]);

            return view;
        }
    }*/

}

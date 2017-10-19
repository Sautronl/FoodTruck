package fr.wcs.foodtruck;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class AdminListeMenuDuJour extends AppCompatActivity {

    private ListView mListeJour;
    private List<ListeJourModel> mLtJour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_menu_du_jour);

        mListeJour = (ListView) findViewById(R.id.liste);
        mListeJour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View convertView, int i, long l) {
                Intent intent = new Intent(AdminListeMenuDuJour.this, AdminMenuDuJour.class);
                intent.putExtra("day", i);
                startActivity(intent);
            }
        });
        afficherListeTweets();
    }

    private List<ListeJourModel> afficherListeTweets(){
        mLtJour = new ArrayList<>();
        mLtJour.add(new ListeJourModel("Lundi"));
        mLtJour.add(new ListeJourModel("Mardi"));
        mLtJour.add(new ListeJourModel("Mercredi"));
        mLtJour.add(new ListeJourModel("Jeudi"));
        mLtJour.add(new ListeJourModel("Vendredi"));

        AdapterListeJour adapter = new AdapterListeJour(AdminListeMenuDuJour.this, mLtJour);
        mListeJour.setAdapter(adapter);

        return mLtJour;
    }
}

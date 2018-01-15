package fr.wcs.foodtruck.UI.Activity.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import fr.wcs.foodtruck.UI.Adapter.GalleryAdapter;
import fr.wcs.foodtruck.R;

public class fullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        GalleryAdapter adapter = new GalleryAdapter(this);

        ImageView imageView = (ImageView)findViewById(R.id.fullImg);
        imageView.setImageResource(adapter.images[position]);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.6));


    }
}

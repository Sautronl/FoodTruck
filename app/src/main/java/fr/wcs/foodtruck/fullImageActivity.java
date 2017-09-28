package fr.wcs.foodtruck;

import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

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
    }
}

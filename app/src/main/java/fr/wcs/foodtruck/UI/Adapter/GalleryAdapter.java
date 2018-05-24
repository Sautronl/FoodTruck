package fr.wcs.foodtruck.UI.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import fr.wcs.foodtruck.R;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    public int i;

//    public Integer[] images = {
//            R.drawable.burger01, R.drawable.burger02,
//            R.drawable.burger03, R.drawable.burger04,
//            R.drawable.burger05 };

    public GalleryAdapter(Context c,int i) {
        this.context = c;
        this.i = i;
    }

    @Override
    public int getCount() {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(i);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
        return imageView;
    }
}

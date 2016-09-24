package com.example.krith.wardrobe.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krith.wardrobe.R;
import com.example.krith.wardrobe.WardrobeItem;

import java.io.File;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by krith on 23/09/16.
 */

public class ViewPagerAdapter_S extends PagerAdapter implements Action1<List<WardrobeItem>> {

    private Context context;
    LayoutInflater inflater;
    public static String path = null;
    public int position;
    private List<WardrobeItem> items = Collections.EMPTY_LIST;

    @BindView(R.id.item)
    ImageView image;

    public ViewPagerAdapter_S(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void call(List<WardrobeItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.inflater.inflate(R.layout.image_item, container, false);
        ButterKnife.bind(this, view);
        path = items.get(position).getData();
        this.position = position;
        image.setImageURI(getImageUri(path));
        container.addView(view);
        return view;
    }

    public Uri getImageUri(String path) {
        Uri uriFromPath = Uri.fromFile(new File(path));
        return uriFromPath;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void shuffleList() {
        Collections.shuffle(items);
        notifyDataSetChanged();
    }

    public int getCurrentPosition() {
        return position;
    }

    public String getCurrentItemData(int position) {
        return items.get(position).getData();
    }
}

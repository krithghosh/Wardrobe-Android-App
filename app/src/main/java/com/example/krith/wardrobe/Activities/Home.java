package com.example.krith.wardrobe.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.krith.wardrobe.Database.DbOpenHelper;
import com.example.krith.wardrobe.Events.FavouriteEvent;
import com.example.krith.wardrobe.Events.ShuffleEvent;
import com.example.krith.wardrobe.Fragments.PantFragment;
import com.example.krith.wardrobe.Fragments.ShirtFragment;
import com.example.krith.wardrobe.R;
import com.example.krith.wardrobe.Utility;
import com.squareup.sqlbrite.BriteDatabase;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity {

    private int imageTask = 0;
    private String type = null;
    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;
    public static final String AUTHORITY = "com.example.krith.wardrobe.providers.authority.ImageProvider";
    public static final String BASE_PATH = "wardrobe";
    public static final String IMAGE = "data";
    public static final String TYPE = "type";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    @Inject
    BriteDatabase db;

    @Inject
    DbOpenHelper dbOpenHelper;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.btn_shuffle)
    ImageButton shuffleBtn;

    @BindView(R.id.btn_favourite)
    ImageButton favouriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(R.string.home_title);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.shirtLayout, ShirtFragment.newInstance())
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.pantLayout, PantFragment.newInstance())
                .commit();
    }

    @OnClick(R.id.btn_shuffle)
    public void shuffle(View view) {
        new ShuffleEvent().post();
    }

    @OnClick(R.id.btn_favourite)
    public void favourite(View view) {
        new FavouriteEvent().post();
        Utility.setNotification(getApplicationContext());
        Toast.makeText(getApplicationContext(), "Your combination is saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

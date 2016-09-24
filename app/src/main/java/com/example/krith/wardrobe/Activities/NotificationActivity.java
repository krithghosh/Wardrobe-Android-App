package com.example.krith.wardrobe.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.krith.wardrobe.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @BindView(R.id.shirtLayout)
    ImageView shirtImage;

    @BindView(R.id.pantLayout)
    ImageView pantImage;

    @BindView(R.id.main_layout)
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        pref = getSharedPreferences(getString(R.string.PREF), MODE_PRIVATE);

        String path = pref.getString(getString(R.string.shirt), "");
        shirtImage.setImageURI(getImageUri(path));

        path = pref.getString(getString(R.string.pant), "");
        pantImage.setImageURI(getImageUri(path));

        Snackbar.make(mainLayout, getString(R.string.new_combination), Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                }).show();
    }

    public Uri getImageUri(String path) {
        Uri uriFromPath = Uri.fromFile(new File(path));
        return uriFromPath;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}

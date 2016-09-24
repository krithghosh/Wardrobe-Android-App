package com.example.krith.wardrobe.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krith.wardrobe.Events.FavouriteEvent;
import com.example.krith.wardrobe.Events.ShuffleEvent;
import com.example.krith.wardrobe.R;
import com.example.krith.wardrobe.Utility;
import com.example.krith.wardrobe.Adapters.ViewPagerAdapter_S;
import com.example.krith.wardrobe.WardrobeApp;
import com.example.krith.wardrobe.WardrobeItem;
import com.squareup.sqlbrite.BriteDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.content.Context.MODE_PRIVATE;

public class ShirtFragment extends Fragment {

    @BindView(R.id.shirt_view_pager)
    ViewPager viewPager;

    @BindView(R.id.btn_add_shirt)
    ImageButton addShirtBtn;

    @Inject
    BriteDatabase db;

    @BindView(R.id.addShirtText)
    TextView textView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private int imageTask = 0;
    private String type = null;
    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;

    private static ViewPagerAdapter_S adapter;

    private Subscription subscription;

    public static ShirtFragment newInstance() {
        return new ShirtFragment();
    }

    @Subscribe
    public void onEvent(ShuffleEvent event) {
        if (adapter.getCount() > 1) {
            Random random = new Random();
            int i = random.nextInt(adapter.getCount()) + 1;
            while (i == adapter.getCurrentPosition()) {
                i = random.nextInt(adapter.getCount()) + 1;
            }
            viewPager.setCurrentItem(i);
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onEvent(FavouriteEvent event) {
        Log.d("CurrentData ", "Shirt- " + adapter.getCurrentItemData(viewPager.getCurrentItem()));
        editor.putString(getString(R.string.shirt), adapter.getCurrentItemData(viewPager.getCurrentItem()));
        editor.commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        WardrobeApp.getComponent(getActivity()).inject(this);
        adapter = new ViewPagerAdapter_S(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        pref = getContext().getSharedPreferences(getString(R.string.PREF), MODE_PRIVATE);
        editor = pref.edit();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shirt, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        String query = WardrobeItem.QUERY_SHIRT;
        String table = WardrobeItem.TABLE;
        subscription = db.createQuery(table, query)
                .mapToList(WardrobeItem.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);

        if (adapter.getCount() == 0) {
            textView.setText(R.string.add_shirt_image);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        subscription.unsubscribe();
    }

    @OnClick(R.id.btn_add_shirt)
    public void addShirt(View view) {
        type = getString(R.string.shirt);
        selectImage(getString(R.string.add_shirt));
    }

    private void selectImage(String msg) {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(msg);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getContext());
                switch (item) {
                    case REQUEST_CAMERA:
                        imageTask = item;
                        if (result)
                            cameraIntent();
                        break;

                    case SELECT_FILE:
                        imageTask = item;
                        if (result)
                            galleryIntent();
                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType(getString(R.string.set_image_type));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (imageTask == REQUEST_CAMERA)
                        cameraIntent();
                    else if (imageTask == SELECT_FILE)
                        galleryIntent();
                } else {
                    /*Snackbar.make(Home.coordinatorLayout, R.string.permission_deny, Snackbar.LENGTH_SHORT).show();*/
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AppCompatActivity.RESULT_OK) {
            String realPath = "";
            try {
                if (requestCode == SELECT_FILE) {
                    realPath = Utility.getRealPathFromURI_API19(getContext(), data.getData());
                    insertData(realPath);
                } else if (requestCode == REQUEST_CAMERA) {
                    insertData(Utility.convertCameraImageToBitmap(data));
                } else {
                    /*Snackbar.make(Home.coordinatorLayout, R.string.issue, Snackbar.LENGTH_SHORT).show();*/
                }
            } catch (Exception e) {
                // handle error
            }
            return;
        }
        /*Snackbar.make(Home.coordinatorLayout, R.string.issue, Snackbar.LENGTH_SHORT).show();*/
    }

    public void insertData(String data) throws UnsupportedEncodingException {

        db.insert(WardrobeItem.TABLE, new WardrobeItem.Builder()
                .id(System.currentTimeMillis())
                .type(type)
                .data(data)
                .build());
    }
}

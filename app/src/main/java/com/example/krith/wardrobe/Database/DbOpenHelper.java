package com.example.krith.wardrobe.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.krith.wardrobe.WardrobeItem;

/**
 * Created by krith on 23/09/16.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static DbOpenHelper mInstance = null;
    private Context context;

    private static final String CREATE_WARDROBE_ITEM = "CREATE TABLE "
            + WardrobeItem.TABLE + "("
            + WardrobeItem.ID + " INTEGER NOT NULL PRIMARY KEY,"
            + WardrobeItem.TYPE + " TEXT NOT NULL,"
            + WardrobeItem.DATA + " BLOB NOT NULL"
            + ")";


    public DbOpenHelper(Context context) {
        super(context, "wardrobe.db", null, VERSION);
        this.context = context;
    }

    public static DbOpenHelper getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new DbOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_WARDROBE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase insertValues(long id, String type, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(WardrobeItem.TABLE, null, new WardrobeItem.Builder()
                .id(id)
                .type(type)
                .data(data)
                .build());
        return db;
    }
}

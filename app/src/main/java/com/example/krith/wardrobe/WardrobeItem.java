package com.example.krith.wardrobe;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.krith.wardrobe.Database.Db;

import rx.functions.Func1;

/**
 * Created by krith on 23/09/16.
 */

public class WardrobeItem {

    public static final String TABLE = "wardrobe_item";

    public static final String ID = "_id";
    public static final String TYPE = "type";
    public static final String DATA = "data";

    private long id;
    private String type;
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static String QUERY_SHIRT = "SELECT " + ID + ", " + TYPE + ", " + DATA + " FROM " + TABLE + " WHERE " + TYPE + "='shirt'";
    public static String QUERY_PANT = "SELECT " + ID + ", " + TYPE + ", " + DATA + " FROM " + TABLE + " WHERE " + TYPE + "='pant'";

    public static Func1<Cursor, WardrobeItem> MAPPER = new Func1<Cursor, WardrobeItem>() {

        @Override
        public WardrobeItem call(Cursor cursor) {

            WardrobeItem wardrobeItem = new WardrobeItem();
            wardrobeItem.setId(Db.getLong(cursor, ID));
            wardrobeItem.setType(Db.getString(cursor, TYPE));
            wardrobeItem.setData(Db.getString(cursor, DATA));
            return wardrobeItem;
        }
    };

    public static final class Builder {
        private final ContentValues contentValues = new ContentValues();

        public Builder id(long id) {
            contentValues.put(ID, id);
            return this;
        }

        public Builder type(String type) {
            contentValues.put(TYPE, type);
            return this;
        }

        public Builder data(String data) {
            contentValues.put(DATA, data);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }
}

package ru.fducha.apartmentnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fducha on 18.03.15.
 */
public class DB {

    private static final String DB_NAME = "apartmentDB";
    private static final int DB_VERSION = 1;

    public static final String DB_TABLE_APARTMENTS = "apartments";
    public static final String DB_TABLE_BUILD_TYPES = "buildTypes";
    public static final String DB_TABLE_PREVIEWS = "previews";
    private static final String DB_TABLE_AGENCY = "agency";
    public static final String DB_TABLE_APARTMENT_NOTES = "apartmentNotes";

    public static final String DB_FIELD_APARTMENTS_ID = "_id";
    public static final String DB_FIELD_APARTMENTS_STREET = "street";
    public static final String DB_FIELD_APARTMENTS_BUILD_NO = "buildNo";
    public static final String DB_FIELD_APARTMENTS_HOUSING = "housing";
    public static final String DB_FIELD_APARTMENTS_PRICE = "price";
    public static final String DB_FIELD_APARTMENTS_FLOOR = "floor";
    public static final String DB_FIELD_APARTMENTS_TOTAL_FLOORS = "totalFloors";
    public static final String DB_FIELD_APARTMENTS_COUNT_ROOMS = "countRooms";
    public static final String DB_FIELD_APARTMENTS_HAS_BALCONY = "hasBalcony";
    public static final String DB_FIELD_APARTMENTS_BUILD_TYPE_ID = "buildTypeId";
    public static final String DB_FIELD_APARTMENTS_YEAR_BUILD = "yearBuild";
    public static final String DB_FIELD_APARTMENTS_AGENCY_NAME = "name";
    public static final String DB_FIELD_APARTMENTS_AGENT_NAME = "agentName";
    public static final String DB_FIELD_APARTMENTS_AGENT_PHONE = "phone";

    public static final String DB_FIELD_BUILD_TYPES_ID = "_id";
    public static final String DB_FIELD_BUILD_TYPES_TYPE = "type";

    public static final String DB_FIELD_PREVIEWS_ID = "id";
    public static final String DB_FIELD_PREVIEWS_APARTMENT_ID = "apartmentId";
    public static final String DB_FIELD_PREVIEWS_DATE = "date";
    public static final String DB_FIELD_PREVIEWS_TIME = "time";
    public static final String DB_FIELD_PREVIEWS_IS_DONE = "isDone";

    public static final String DB_FIELD_APARTMENT_NOTES_ID = "id";
    public static final String DB_FIELD_APARTMENT_NOTES_APARTMENT_ID = "apartmentId";
    public static final String DB_FIELD_APARTMENT_NOTES_NOTE = "note";

    private static final String DB_CREATE_APARTMENTS_TABLE =
            "create table " + DB_TABLE_APARTMENTS + " ( " +
                    DB_FIELD_APARTMENTS_ID + " integer primary key autoincrement, " +
                    DB_FIELD_APARTMENTS_STREET + " text, " +
                    DB_FIELD_APARTMENTS_BUILD_NO + " integer, " +
                    DB_FIELD_APARTMENTS_HOUSING + " text, " +
                    DB_FIELD_APARTMENTS_PRICE + " integer, " +
                    DB_FIELD_APARTMENTS_FLOOR + " integer, " +
                    DB_FIELD_APARTMENTS_TOTAL_FLOORS + " integer, " +
                    DB_FIELD_APARTMENTS_COUNT_ROOMS + " integer, " +
                    DB_FIELD_APARTMENTS_HAS_BALCONY + " boolean, " +
                    DB_FIELD_APARTMENTS_BUILD_TYPE_ID + " integer, " +
                    DB_FIELD_APARTMENTS_YEAR_BUILD + " integer, " +
                    DB_FIELD_APARTMENTS_AGENCY_NAME + " text, " +
                    DB_FIELD_APARTMENTS_AGENT_NAME + " text, " +
                    DB_FIELD_APARTMENTS_AGENT_PHONE + " text" +
            ");";

    private static final String DB_CREATE_BUILD_TYPES_TABLE =
            "create table " + DB_TABLE_BUILD_TYPES + " ( " +
                    DB_FIELD_BUILD_TYPES_ID + " integer primary key autoincrement, " +
                    DB_FIELD_BUILD_TYPES_TYPE+ " text" +
            ");";

    private static final String DB_CREATE_PREVIEWS_TABLE =
            "create table " + DB_TABLE_PREVIEWS + " ( " +
                    DB_FIELD_PREVIEWS_ID + " integer primary key autoincrement, " +
                    DB_FIELD_PREVIEWS_APARTMENT_ID + " integer, " +
                    DB_FIELD_PREVIEWS_DATE + " integer, " +
                    DB_FIELD_PREVIEWS_TIME + " integer, " +
                    DB_FIELD_PREVIEWS_IS_DONE + " boolean" +
            ");";

    private static final String DB_CREATE_APARTMENT_NOTES_TABLE =
            "create table " + DB_TABLE_APARTMENT_NOTES + " ( " +
                    DB_FIELD_APARTMENT_NOTES_ID + " integer primary key autoincrement, " +
                    DB_FIELD_APARTMENT_NOTES_APARTMENT_ID + " text, " +
                    DB_FIELD_APARTMENT_NOTES_NOTE + " text" +
            ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    public void addApartment(Apartment apartment) {
        ContentValues cv = new ContentValues();
        cv.put(DB_FIELD_APARTMENTS_STREET, apartment.getStreet());
        cv.put(DB_FIELD_APARTMENTS_BUILD_NO, apartment.getBuildNo());
        cv.put(DB_FIELD_APARTMENTS_HOUSING, apartment.getHousing());
        cv.put(DB_FIELD_APARTMENTS_PRICE, apartment.getPrice());
        cv.put(DB_FIELD_APARTMENTS_FLOOR, apartment.getFloor());
        cv.put(DB_FIELD_APARTMENTS_TOTAL_FLOORS, apartment.getTotalFloors());
        cv.put(DB_FIELD_APARTMENTS_COUNT_ROOMS, apartment.getCountRooms());
        cv.put(DB_FIELD_APARTMENTS_HAS_BALCONY, apartment.hasBalcony());
        cv.put(DB_FIELD_APARTMENTS_BUILD_TYPE_ID, apartment.getBuildTypeId());
        cv.put(DB_FIELD_APARTMENTS_YEAR_BUILD, apartment.getYearBuild());
        cv.put(DB_FIELD_APARTMENTS_AGENCY_NAME, apartment.getAgencyName());
        cv.put(DB_FIELD_APARTMENTS_AGENT_NAME, apartment.getAgentName());
        cv.put(DB_FIELD_APARTMENTS_AGENT_PHONE, apartment.getAgentPhone());
        mDB.insert(DB_TABLE_APARTMENTS, null, cv);
    }

    public Apartment getApartmentById(int _id) {
        String[] args = new String[] {"" + _id};
        Cursor cursor = mDB.query(DB_TABLE_APARTMENTS, null, "_id = ?", args, null, null, null);
        if (cursor.getCount() == 1) {
            if (cursor.moveToFirst()) {
                Apartment ap = new Apartment();
                ap.setId(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_ID)));
                ap.setStreet(cursor.getString(cursor.getColumnIndex(DB_FIELD_APARTMENTS_STREET)));
                ap.setBuildNo(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_BUILD_NO)));
                ap.setHousing(cursor.getString(cursor.getColumnIndex(DB_FIELD_APARTMENTS_HOUSING)));
                ap.setPrice(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_PRICE)));
                ap.setFloor(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_FLOOR)));
                ap.setTotalFloors(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_TOTAL_FLOORS)));
                ap.setCountRooms(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_COUNT_ROOMS)));
                ap.setBalcony(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_HAS_BALCONY)) > 0);
                ap.setBuildTypeId(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_BUILD_TYPE_ID)));
                ap.setYearBuild(cursor.getInt(cursor.getColumnIndex(DB_FIELD_APARTMENTS_YEAR_BUILD)));
                ap.setAgencyName(cursor.getString(cursor.getColumnIndex(DB_FIELD_APARTMENTS_AGENCY_NAME)));
                ap.setAgentName(cursor.getString(cursor.getColumnIndex(DB_FIELD_APARTMENTS_AGENT_NAME)));
                ap.setAgentPhone(cursor.getString(cursor.getColumnIndex(DB_FIELD_APARTMENTS_AGENT_PHONE)));

                return ap;
            }
        }

        return new Apartment();
    }

    public Cursor getAllApartments() {
        return mDB.query(DB_TABLE_APARTMENTS, null, null, null, null, null, null);
    }

    public List<String> getAllBuildTypes() {
        List<String> types = new ArrayList<String>();
        Cursor cursor = mDB.query(DB_TABLE_BUILD_TYPES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                types.add(cursor.getString(cursor.getColumnIndex(DB_FIELD_BUILD_TYPES_TYPE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return types;
    }

    public int getBuildTypeIdByName(String type) {
        String[] args = new String[] {"" + type};
        Cursor cursor = mDB.query(DB_TABLE_BUILD_TYPES, null, DB_FIELD_BUILD_TYPES_TYPE + " = ?", args, null, null, null);

//        String query = "SELECT * FROM " + DB_TABLE_BUILD_TYPES +
//                " WHERE " + DB_FIELD_BUILD_TYPES_TYPE + " = " + type + ";";
//        Cursor cursor = mDB.rawQuery("", null);

//        Log.d("log", "count cursor = " + cursor.getCount());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
//            Log.d("log", "cursor id type = " + cursor.getInt(0));
            return cursor.getInt(0);
        } else
            return -1;
    }

    public String getBuildTypeById(int id) {
        if (id == -1)
            return "";
        String[] args = new String[] {"" + id};
        Cursor cursor = mDB.query(DB_TABLE_BUILD_TYPES, null, DB_FIELD_BUILD_TYPES_ID + " = ?", args, null, null, null);
//        Log.d("log", "count cursor = " + cursor.getCount());
//        cursor.moveToFirst();
//        Log.d("log", "type cursor = " + cursor.getString(1));
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
//            Log.d("log", cursor.getString(cursor.getColumnIndex(DB_FIELD_BUILD_TYPES_TYPE)));
            return cursor.getString(cursor.getColumnIndex(DB_FIELD_BUILD_TYPES_TYPE));
        } else
            return "";
    }

    private boolean hasBuildType(String type) {
        String[] args = new String[] {"" + type};
        Cursor cursor = mDB.query(DB_TABLE_BUILD_TYPES, null, DB_FIELD_BUILD_TYPES_TYPE + " = ?", args, null, null, null);
        if (cursor.getCount() > 0)
            return true;
        else return false;
    }

    public void addBuildType(String type){
        if (!type.isEmpty() && !hasBuildType(type)) {
            //
        }
    }

    public int getCountRecordsInTable(String _table) {
        return mDB.query(_table, null, null, null, null, null, null).getCount();
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_APARTMENTS_TABLE);
            db.execSQL(DB_CREATE_BUILD_TYPES_TABLE);

            ContentValues cv = new ContentValues();
            cv.put(DB_FIELD_BUILD_TYPES_TYPE, "Хрущевка");
            db.insert(DB_TABLE_BUILD_TYPES, null, cv);
            cv.put(DB_FIELD_BUILD_TYPES_TYPE, "Брежневка");
            db.insert(DB_TABLE_BUILD_TYPES, null, cv);
            cv.put(DB_FIELD_BUILD_TYPES_TYPE, "93 серия");
            db.insert(DB_TABLE_BUILD_TYPES, null, cv);
            cv.put(DB_FIELD_BUILD_TYPES_TYPE, "Улучшенной планировки");
            db.insert(DB_TABLE_BUILD_TYPES, null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //
        }
    }
}

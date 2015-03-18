package ru.fducha.apartmentnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fducha on 18.03.15.
 */
public class DB {

    private static final String DB_NAME = "apartmentDB";
    private static final int DB_VERSION = 1;

    private static final String DB_TABLE_APARTMENTS = "apartments";
    private static final String DB_TABLE_BUILD_TYPES = "buildTypes";
    private static final String DB_TABLE_PREVIEWS = "previews";
    private static final String DB_TABLE_AGENCY = "agency";
    private static final String DB_TABLE_APARTMENT_NOTES = "apartmentNotes";

    public static final String DB_FIELD_APARTMENTS_ID = "id";
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
    public static final String DB_FIELD_APARTMENTS_PREVIEW_ID = "previewId";
    public static final String DB_FIELD_APARTMENTS_AGENCY_ID = "agencyId";

    public static final String DB_FIELD_BUILD_TYPES_ID = "id";
    public static final String DB_FIELD_BUILD_TYPES_TYPE = "type";

    public static final String DB_FIELD_PREVIEWS_ID = "id";
    public static final String DB_FIELD_PREVIEWS_DATE = "date";
    public static final String DB_FIELD_PREVIEWS_TIME = "time";
    public static final String DB_FIELD_PREVIEWS_IS_DONE = "isDone";

    public static final String DB_FIELD_AGENCY_ID = "id";
    public static final String DB_FIELD_AGENCY_NAME = "name";
    public static final String DB_FIELD_AGENCY_AGENT_NAME = "agentName";
    public static final String DB_FIELD_AGENCY_PHONE = "phone";

    public static final String DB_FIELD_APARTMENT_NOTES_ID = "id";
    public static final String DB_FIELD_APARTMENT_NOTES_APARTMENT_ID = "apartmentId";
    public static final String DB_FIELD_APARTMENT_NOTES_NOTE = "note";

    private static final String DB_CREATE_APARTMENTS_TABLE =
            "create table " + DB_TABLE_APARTMENTS + "(" +
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
                    DB_FIELD_APARTMENTS_PREVIEW_ID + " integer, " +
                    DB_FIELD_APARTMENTS_AGENCY_ID + " integer" +
            ");";

    private static final String DB_CREATE_BUILD_TYPES_TABLE =
            "create table " + DB_TABLE_BUILD_TYPES + "(" +
                    DB_FIELD_BUILD_TYPES_ID + " integer primary key autoincrement, " +
                    DB_FIELD_BUILD_TYPES_TYPE+ " text" +
            ");";

    private static final String DB_CREATE_PREVIEWS_TABLE =
            "create table " + DB_TABLE_PREVIEWS + "(" +
                    DB_FIELD_PREVIEWS_ID + " integer primary key autoincrement, " +
                    DB_FIELD_PREVIEWS_DATE + " integer, " +
                    DB_FIELD_PREVIEWS_TIME + " integer, " +
                    DB_FIELD_PREVIEWS_IS_DONE + " boolean" +
            ");";

    private static final String DB_CREATE_AGENCY_TABLE =
            "create table " + DB_TABLE_AGENCY + "(" +
                    DB_FIELD_AGENCY_ID+ " integer primary key autoincrement, " +
                    DB_FIELD_AGENCY_NAME + " text, " +
                    DB_FIELD_AGENCY_AGENT_NAME + " text, " +
                    DB_FIELD_AGENCY_PHONE + " text" +
            ");";

    private static final String DB_CREATE_APARTMENT_NOTES_TABLE =
            "create table " + DB_TABLE_APARTMENT_NOTES + "(" +
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

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //
        }
    }
}

package ru.fducha.apartmentnotes;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    Button btnAddApartment;
    ListView lvApartments;
    DB db;
    SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new DB(this);
        db.open();

        String[] from = new String[] {
                DB.DB_FIELD_APARTMENTS_STREET,
                DB.DB_FIELD_APARTMENTS_BUILD_NO,
                DB.DB_FIELD_APARTMENTS_HOUSING,
                DB.DB_FIELD_APARTMENTS_PRICE,
                DB.DB_FIELD_APARTMENTS_FLOOR,
                DB.DB_FIELD_APARTMENTS_TOTAL_FLOORS,
                DB.DB_FIELD_APARTMENTS_COUNT_ROOMS,
                DB.DB_FIELD_APARTMENTS_HAS_BALCONY,
//                DB.DB_FIELD_APARTMENTS_BUILD_TYPE_ID,
                DB.DB_FIELD_APARTMENTS_YEAR_BUILD,
                DB.DB_FIELD_APARTMENTS_AGENCY_NAME,
                DB.DB_FIELD_APARTMENTS_AGENT_NAME,
                DB.DB_FIELD_APARTMENTS_AGENT_PHONE
        };

        int[] to = new int[] {
                R.id.tvStreet,
                R.id.tvBuild,
                R.id.tvHousing,
                R.id.tvPrice,
                R.id.tvFloor,
                R.id.tvTotalFloor,
                R.id.tvCountRooms,
                R.id.cbBalcony,
//                R.id.spBuildTypes,
                R.id.tvYearBuild,
                R.id.tvAgency,
                R.id.tvAgentName,
                R.id.tvPhone
        };

        scAdapter = new SimpleCursorAdapter(this, R.layout.aprtment_item, null, from, to, 0);
        lvApartments = (ListView) findViewById(R.id.lvApartments);
        lvApartments.setAdapter(scAdapter);

        btnAddApartment = (Button) findViewById(R.id.btnAddApartment);
        btnAddApartment.setOnClickListener(this);

        getLoaderManager().initLoader(0, null, this);

//        getLoaderManager().getLoader(0).forceLoad();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddApartment) {
            Intent intent = new Intent(this, ApartmentActivity.class);
            intent.putExtra("apartmentId", -1);
            startActivity(intent);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {
        DB db;

        public MyCursorLoader(Context ctx, DB db) {
            super(ctx);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor c = db.getAllApartments();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return c;
        }
    }

}

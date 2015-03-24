package ru.fducha.apartmentnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


public class ApartmentActivity extends Activity implements View.OnClickListener {

    Spinner spBuildTypes;
    Button btnCancelApartment, btnSaveApartment;
    ImageButton btnAddBuildType;
    EditText etStreet, etBuildNo, etHousing, etFloor, etTFloors,
             etCountRooms, etYearBuild, etPrice, etAgency,
             etAgentName, etAgentPhone;
    CheckBox cbBalcony;
    DB db;

    Apartment m_apartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);

        btnCancelApartment = (Button) findViewById(R.id.btnCancelApartment);
        btnSaveApartment = (Button) findViewById(R.id.btnSaveApartment);
        btnAddBuildType = (ImageButton) findViewById(R.id.btnAddBuildType);

        etStreet = (EditText) findViewById(R.id.etStreet);
        etBuildNo = (EditText) findViewById(R.id.etBuildNo);
        etHousing = (EditText) findViewById(R.id.etHausing);
        etFloor = (EditText) findViewById(R.id.etFloor);
        etTFloors = (EditText) findViewById(R.id.etTFloors);
        etCountRooms = (EditText) findViewById(R.id.etCountRooms);
        cbBalcony = (CheckBox) findViewById(R.id.cbBalcony);
        etYearBuild = (EditText) findViewById(R.id.etYearBuild);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etAgency = (EditText) findViewById(R.id.etAgency);
        etAgentName = (EditText) findViewById(R.id.etAgentName);
        etAgentPhone = (EditText) findViewById(R.id.etAgentPhone);

        btnSaveApartment.setOnClickListener(this);
        btnCancelApartment.setOnClickListener(this);
        btnAddBuildType.setOnClickListener(this);

        db = new DB(this);
        db.open();

        Intent intent = getIntent();
        int apId = intent.getIntExtra("apartmentId", -1);

        m_apartment = db.getApartmentById(apId);

        setApartmentDataToWidgets();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apartment, menu);
        return true;
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
        switch (v.getId()) {
            case R.id.btnCancelApartment:
                finish();
                break;
            case R.id.btnSaveApartment:
                addApartment();
//                Toast.makeText(this, db.DB_TABLE_APARTMENTS + " has recs: " +
//                        db.getCountRecordsInTable(db.DB_TABLE_APARTMENTS), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Apartment on " + m_apartment.getStreet() + " " +
                                m_apartment.getBuildNo() + m_apartment.getHousing() + " was added.",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btnAddBuildType:
                Toast.makeText(this, "add type build", Toast.LENGTH_SHORT).show();
                showDialogAddingBuildType();
                break;
        }
    }

    private void addApartment() {
        String street = etStreet.getText().toString();
        int buildNo =  Integer.parseInt(etBuildNo.getText().toString());
        String housing = etHousing.getText().toString();
        int floor = Integer.parseInt(etFloor.getText().toString());
        int totalFloors = Integer.parseInt(etTFloors.getText().toString());
        int countRooms = Integer.parseInt(etCountRooms.getText().toString());
        boolean isBalcony = cbBalcony.isChecked();
        int year = Integer.parseInt(etYearBuild.getText().toString());
        int price = Integer.parseInt(etPrice.getText().toString());
        String agency = etAgency.getText().toString();
        String agentName = etAgentName.getText().toString();
        String agentPhone = etAgentPhone.getText().toString();
        m_apartment = new Apartment(street, buildNo, housing, price, floor, totalFloors,
                countRooms, isBalcony, -1, year, agency, agentName, agentPhone);
        if (!m_apartment.isEmpty())
            db.addApartment(m_apartment);
    }

    private void showDialogAddingBuildType() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Тип дома");
        alert.setMessage("Введите новый тип дома");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                //Toast.makeText(this, "new type " + value, Toast.LENGTH_SHORT).show();
                Log.d("log", value);
            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        alert.show();
    }

    private void setApartmentDataToWidgets() {
        etStreet.setText(m_apartment.getStreet());
        if (m_apartment.getBuildNo() == 0)
            etBuildNo.setText("");
        else
            etBuildNo.setText("" + m_apartment.getBuildNo());
        etHousing.setText(m_apartment.getHousing());
        if (m_apartment.getFloor() == 0)
            etFloor.setText("");
        else
            etFloor.setText("" + m_apartment.getFloor());
        if (m_apartment.getTotalFloors() == 0)
            etTFloors.setText("");
        else
            etTFloors.setText("" + m_apartment.getTotalFloors());
        if (m_apartment.getCountRooms() == 0)
            etCountRooms.setText("");
        else
            etCountRooms.setText("" + m_apartment.getCountRooms());
        cbBalcony.setChecked(m_apartment.hasBalcony());
        if (m_apartment.getYearBuild() == 0)
            etYearBuild.setText("");
        else
            etYearBuild.setText("" + m_apartment.getYearBuild());
        if (m_apartment.getPrice() == 0)
            etPrice.setText("");
        else
            etPrice.setText("" + m_apartment.getPrice());
        etAgency.setText("" + m_apartment.getAgencyName());
        etAgentName.setText("" + m_apartment.getAgentName());
        etAgentPhone.setText("" + m_apartment.getAgentPhone());
    }
}

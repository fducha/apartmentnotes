// TODO will make input masks for input fields

package ru.fducha.apartmentnotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class ApartmentActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner spBuildTypes;
    Button btnCancelApartment, btnSaveApartment;
    ImageButton btnEditBuildTypes, btnOpenCalls;
    EditText etStreet, etBuildNo, etHousing, etFloor, etTFloors,
             etCountRooms, etYearBuild, etPrice, etAgency,
             etAgentName, etAgentPhone;
    CheckBox cbBalcony;
    DB db;

    Apartment m_apartment;

    final String LOG_ADD_APART = "LOG_ADD_APART";
    final String LOG_APART_EDIT_AP = "LOG_APART_EDIT";

    final int PICK_CONTACT = 100;
    private static final String[] phoneProtection = new String[] {ContactsContract.CommonDataKinds.Phone.DATA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);

        btnCancelApartment = (Button) findViewById(R.id.btnCancelApartment);
        btnSaveApartment = (Button) findViewById(R.id.btnSaveApartment);
        btnEditBuildTypes = (ImageButton) findViewById(R.id.btnEditBuildTypes);
        btnOpenCalls = (ImageButton) findViewById(R.id.btnOpenCalls);

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
        btnEditBuildTypes.setOnClickListener(this);
        btnOpenCalls.setOnClickListener(this);

        db = new DB(this);
        db.open();

        spBuildTypes = (Spinner) findViewById(R.id.spBuildTypes);
        spBuildTypes.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        int apId = (int) intent.getLongExtra("apartmentId", -1);
        Log.d(LOG_APART_EDIT_AP, "get id = " + apId);

        m_apartment = db.getApartmentById(apId);

        setApartmentDataToWidgets();

        // hide soft keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etStreet.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancelApartment:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnSaveApartment:

                Log.d(LOG_APART_EDIT_AP, "apartment id = " + m_apartment.getId());

                // save new Apartment
                if (m_apartment.getId() == -1) {
                    addApartment();
                    Log.d(LOG_APART_EDIT_AP, "I'm here add");
                } else {
                    // update exists Apartment
                    Log.d(LOG_APART_EDIT_AP, "I'm here edit");
                    getApartmentDataFromWidgets();
                    db.editApartment(m_apartment);
                }

                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btnEditBuildTypes:
                //Toast.makeText(this, "add type build", Toast.LENGTH_SHORT).show();
                //showDialogAddingBuildType();
                break;
            case R.id.btnOpenCalls:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, PICK_CONTACT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT){
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Log.d(LOG_APART_EDIT_AP, contactData.toString());
                if (contactData == null) return;
                Cursor c = getContentResolver().query(contactData, phoneProtection, null, null, null);
                if (c == null) return;
                try {
                    while (c.moveToNext()) {
                        String phone = c.getString(0);
                        etAgentPhone.setText(phone);
                    }
                } finally {
                    c.close();
                }
            }
        }
    }

    private void addApartment() {
        getApartmentDataFromWidgets();

        if (!m_apartment.isEmpty()) {
            db.addApartment(m_apartment);
            Toast.makeText(this, "Apartment on " + m_apartment.getStreet() + " " +
                            m_apartment.getBuildNo() + m_apartment.getHousing() + " was added.",
                    Toast.LENGTH_SHORT).show();
            Log.d(LOG_ADD_APART, "Apartment's fields are NOT empty.");
        } else {
            Log.d(LOG_ADD_APART, "Apartment's fields are empty.");
        }
    }

    private void getApartmentDataFromWidgets() {
        String street = etStreet.getText().toString();

        int buildNo = 0;
        try {
            buildNo = Integer.parseInt(etBuildNo.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(LOG_ADD_APART, "fail build No");
        }

        String housing = etHousing.getText().toString();
        Log.d(LOG_ADD_APART, "" + housing);

        int floor = 0;
        try {
            floor = Integer.parseInt(etFloor.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(LOG_ADD_APART, "fail floor");
        }
        Log.d(LOG_ADD_APART, "" + floor);

        int totalFloors = 0;
        try {
            totalFloors = Integer.parseInt(etTFloors.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(LOG_ADD_APART, "fail total floors");
        }
        Log.d(LOG_ADD_APART, "" + totalFloors);

        int countRooms = 0;
        try {
            countRooms = Integer.parseInt(etCountRooms.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(LOG_ADD_APART, "fail count rooms");
        }
        Log.d(LOG_ADD_APART, "" + countRooms);

        boolean isBalcony = cbBalcony.isChecked();
        Log.d(LOG_ADD_APART, "" + isBalcony);

        int type = m_apartment.getBuildTypeId();
        Log.d(LOG_ADD_APART, "" + db.getBuildTypeById(type));

        int year = 0;
        try {
            year = Integer.parseInt(etYearBuild.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(LOG_ADD_APART, "fail years");
        }
        Log.d(LOG_ADD_APART, "" + year);

        int price = 0;
        try {
            price = Integer.parseInt(etPrice.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(LOG_ADD_APART, "fail price");
        }
        Log.d(LOG_ADD_APART, "" + price);

        String agency = etAgency.getText().toString();
        String agentName = etAgentName.getText().toString();
        String agentPhone = etAgentPhone.getText().toString();

        if (m_apartment.getId() == -1) {
            m_apartment = new Apartment(street, buildNo, housing, price, floor, totalFloors,
                    countRooms, isBalcony, type, year, agency, agentName, agentPhone);
        } else {
            m_apartment.setStreet(street);
            m_apartment.setBuildNo(buildNo);
            m_apartment.setHousing(housing);
            m_apartment.setPrice(price);
            m_apartment.setFloor(floor);
            m_apartment.setTotalFloors(totalFloors);
            m_apartment.setCountRooms(countRooms);
            m_apartment.setBalcony(isBalcony);
            m_apartment.setBuildTypeId(type);
            m_apartment.setAgencyName(agency);
            m_apartment.setAgentName(agentName);
            m_apartment.setAgentPhone(agentPhone);
        }

    }

    private void loadSpinnerData() {
        List<String> bTypes = db.getAllBuildTypes();
//        Toast.makeText(this, "count = " + bTypes.size(), Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuildTypes.setAdapter(adapter);
//        Log.d("log", "spiner build = " + m_apartment.getBuildTypeId());
        if (m_apartment.getBuildTypeId() != -1) {
            String currentType = db.getBuildTypeById(m_apartment.getBuildTypeId());
            spBuildTypes.setSelection(bTypes.indexOf(currentType));
        }
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

        loadSpinnerData();

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type = parent.getItemAtPosition(position).toString();
//        Log.d("log", "select build = " + type);
        int i = db.getBuildTypeIdByName(type);
//        Log.d("log", "select build id = " + i);
        m_apartment.setBuildTypeId(i);
//        Log.d("log", "apartment build id = " + m_apartment.getBuildTypeId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }
}

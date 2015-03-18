package ru.fducha.apartmentnotes;

/**
 * Created by fducha on 18.03.15.
 */
public class Apartment {

    private int m_id;
    private String m_street;
    private int m_buildNo;
    private String m_housing;
    private int m_price;
    private int m_floor;
    private int m_totalFloors;
    private int m_countRooms;
    private boolean m_hasBalcony;
    private int m_buildTypeId;
    private int m_yearBuild;
    private int m_agencyId;

    Apartment(String street, int buildNo, String housing, int price,
              int floor, int totalFloors, int countRooms, boolean hasBalcony,
              int buildTypeId, int yearBuild, int agencyId) {
        setStreet(street);
        setBuildNo(buildNo);
        setHousing(housing);
        setPrice(price);
        setFloor(floor);
        setTotalFloors(totalFloors);
        setCountRooms(countRooms);
        setBalcony(hasBalcony);
        setBuildTypeId(buildTypeId);
        setYearBuild(yearBuild);
        setAgencyId(agencyId);
    }

    public void setId(int id) {
        m_id = id;
    }

    public int getId() {
        return m_id;
    }

    public void setStreet(String _street) {
        m_street = _street;
    }

    public String getStreet() {
        return m_street;
    }

    public void setBuildNo(int _buildNo) {
        m_buildNo = _buildNo;
    }

    public int getBuildNo() {
        return m_buildNo;
    }

    public void setHousing(String _house) {
        m_housing = _house;
    }

    public String getHousing() {
        return m_housing;
    }

    public void setPrice(int _price) {
        m_price = _price;
    }

    public int getPrice() {
        return m_price;
    }

    public void setFloor(int _floor) {
        m_floor = _floor;
    }

    public int getFloor() {
        return m_floor;
    }

    public void setTotalFloors(int _tf) {
        m_totalFloors = _tf;
    }

    public int getTotalFloors() {
        return m_totalFloors;
    }

    public void setCountRooms(int _cr) {
        m_countRooms= _cr;
    }

    public int getCountRooms() {
        return m_countRooms;
    }

    public void setBalcony(boolean _b) {
        m_hasBalcony = _b;
    }

    public boolean hasBalcony() {
        return m_hasBalcony;
    }

    public void setBuildTypeId(int _btId) {
        m_buildTypeId = -_btId;
    }

    public int getBuildTypeId() {
        return m_buildTypeId;
    }

    public void setYearBuild(int _yb) {
        m_yearBuild = _yb;
    }

    public int getYearBuild() {
        return m_yearBuild;
    }

    public void setAgencyId(int _agId) {
        m_agencyId = _agId;
    }

    public int getAgencyId() {
        return m_agencyId;
    }
}

package com.gulley.dustin.data;

import org.json.JSONObject;

public class Location extends BaseData {

    protected String street;      //Street address
    protected String city;        //City address
    protected String state;       //State address
    protected String zip;         //Zip address
    protected float latitude;     //Location latitude
    protected float longitude;    //Locatoin longitude

    /**
     * Street getter
     * @return
     */
    public String getStreet() {
        return this.street;
    }

    /**
     * City getter
     * @return
     */
    public String getCity() {
        return this.city;
    }

    /**
     * State getter
     * @return
     */
    public String getState() {
        return this.state;
    }

    /**
     * Zip getter
     * @return
     */
    public String getZip() {
        return this.zip;
    }

    /**
     * Latitude getter
     * @return
     */
    public float getLatitude() {
        return this.latitude;
    }

    /**
     * Longitude getter
     * @return
     */
    public float getLongitude() {
        return this.longitude;
    }

    /**
     * Street setter
     * @param street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * City setter
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * State setter
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Zip setter
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Latitude setter
     * @param latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Longitude setter
     * @param longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Address as a string
     * @return
     */
    public String getAddress() {
        return this.street + " " + this.city + " " + this.state + " " + this.zip;
    }

    /**
     * Location as a string
     * @return
     */
    public String toString() {
        String tempString = super.toString();
        tempString += ", street: " + this.street;
        tempString += ", city: " + this.city;
        tempString += ", state: " + this.state;
        tempString += ", zip: " + this.zip;
        tempString += ", latitude: " + Float.toString(this.latitude);
        tempString += ", longitude: " + Float.toString(this.longitude);

        return tempString;
    }

    /**
     * Return location as json data
     * @return
     */
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("street", this.street);
        obj.put("city", this.city);
        obj.put("state", this.state);
        obj.put("zip", this.zip);
        obj.put("latitude", this.latitude);
        obj.put("longitude", this.longitude);

        return obj;
    }

    /**
     * No arg constructor
     */
    public Location() {
        super();
    }

    /**
     * JSON constructor
     * @param obj
     */
    public Location(JSONObject obj) {
        super();

        if(obj.has("street")) {
            this.street = obj.getString("street");
        }

        if(obj.has("city")) {
            this.city = obj.getString("city");
        }

        if(obj.has("state")) {
            this.state = obj.getString("state");
        }

        if(obj.has("zip")) {
            this.zip = obj.getString("zip");
        }

        if(obj.has("latitude")) {
            this.latitude = obj.getFloat("latitude");
        }

        if(obj.has("longitude")) {
            this.longitude = obj.getFloat("longitude");
        }

    }

    /**
     * Args constructor
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param latitude
     * @param longitude
     */
    public Location(String street, String city, String state, String zip, float latitude, float longitude) {
        super();
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}

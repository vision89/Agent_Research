package com.gulley.dustin.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Work extends Area {

    protected ArrayList<HourRange> lunchHours = new ArrayList<HourRange>();   //Lunch hour range
    protected ArrayList<HourRange> workHours = new ArrayList<HourRange>();    //Work hour range

    /**
     * Get lunch hour ranges
     * @return
     */
    public HourRange[] getLunchHours() {
        return this.lunchHours.toArray(new HourRange[this.lunchHours.size()]);
    }

    /**
     * Get work hour ranges
     * @return
     */
    public HourRange[] getWorkHours() {
        return this.workHours.toArray(new HourRange[this.workHours.size()]);
    }

    /**
     * Removes hour ranges from lunch/work range lists
     * @param rangeList
     * @param range
     */
    private void removeGenericHours(ArrayList<HourRange> rangeList, HourRange range) {
        int i = 0;
        for(HourRange hr : rangeList) {
            if(range.getDay() == hr.getDay()) {
                break;
            }
            ++i;
        }
        if(i < rangeList.size()) {
            rangeList.remove(i);
        }
    }

    /**
     * Remove hour range from lunch hours
     * @param range
     */
    public void removeLunchHoursRange(HourRange range) {
        removeGenericHours(this.lunchHours, range);
    }

    /**
     * Remove hour range from work hours
     * @param range
     */
    public void removeWorkHoursRange(HourRange range) {
        removeGenericHours(this.workHours, range);
    }

    /**
     * Generic function for adding lunch/work ranges
     * @param rangeList
     * @param range
     */
    private void addGenericHours(ArrayList<HourRange> rangeList, HourRange range) {
        boolean insertRange = true;

        for(HourRange hr : rangeList) {
            if(range.getDay() == hr.getDay()) {
                insertRange = false;
                break;
            }
        }

        if(insertRange == true) {
            rangeList.add(range);
        }
    }

    /**
     * Add the lunch hour ranges
     * @param range
     */
    public void addLunchHours(HourRange range) {
        addGenericHours(this.lunchHours, range);
    }

    /**
     * Add the work hour ranges
     * @param range
     */
    public void addWorkHours(HourRange range) {
        addGenericHours(this.workHours, range);
    }

    /**
     * Returns work data as JSON
     * @return
     */
    public JSONObject toJSON() {

        JSONObject obj = super.toJSON();
        JSONArray lunchHoursArray = new JSONArray();
        JSONArray workHoursArray = new JSONArray();

        for(HourRange hr : this.lunchHours) {
            lunchHoursArray.put(hr.toJSON());
        }

        for(HourRange hr : this.workHours) {
            workHoursArray.put(hr.toJSON());
        }

        obj.put("lunchHours", lunchHoursArray);
        obj.put("workHours", workHoursArray);

        return obj;

    }

    /**
     * No arg constructor
     */
    public Work() {
        super();
    }

    public Work(JSONObject obj) {
        super();

        if(obj.has("street")) {
            this.street = obj.getString("street");
        }

        if(obj.has("city")) {
            this.city = obj.getString("city");
        }

        if(obj.has("state")) {
            this.state = obj.getString("State");
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

        if(obj.has("radius")) {
            this.radius = obj.getInt("radius");
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
     * @param radius
     */
    public Work(String street, String city, String state, String zip, float latitude, float longitude, int radius) {
        super(street, city, state, zip, latitude, longitude, radius);
    }

}

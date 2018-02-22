package com.gulley.dustin.data;

import org.json.JSONObject;

public class Area extends Location {

    protected int radius; //Radius from location to search

    /**
     * Radius getter
     * @return
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Radius setter
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public JSONObject toJson() {

        JSONObject obj = super.toJSON();
        obj.put("radius", this.radius);

        return obj;

    }

    /**
     * No arg constructor
     */
    public Area() {
        super();
    }

    /**
     * JSON Constructor
     * @param obj
     */
    public Area(JSONObject obj) {

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
    public Area(String street, String city, String state, String zip, float latitude, float longitude, int radius) {
        super(street, city, state, zip, latitude, longitude);
        this.radius = radius;
    }

}

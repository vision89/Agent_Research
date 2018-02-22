package com.gulley.dustin.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class Route extends BaseData {

    public float duration;      //Travel duration
    public float distance;      //Travel distance
    public String startAddress; //Start address
    public String endAddress;   //End address
    public JSONArray steps;     //Number of steps to get there

    /**
     * Duration setter
     * @param duration
     */
    public void setDuration(float duration) { this.duration = duration; }

    /**
     * Distance setter
     * @param distance
     */
    public void setDistance(float distance) { this.distance = distance; }

    /**
     * Start Address setter
     * @param startAddress
     */
    public void setStartAddress(String startAddress) { this.startAddress = startAddress; }

    /**
     * End Address setter
     * @param endAddress
     */
    public void setEndAddress(String endAddress) { this.endAddress = endAddress; }

    /**
     * Steps setter
     * @param steps
     */
    public void setSteps(JSONArray steps) { this.steps = steps; }

    /**
     * Duration getter
     * @return
     */
    public float getDuration() { return this.duration; }

    /**
     * Distance getter
     * @return
     */
    public float getDistance() { return this.distance; }

    /**
     * Start Address getter
     * @return
     */
    public String getStartAddress() { return this.getStartAddress(); }

    /**
     * End Address getter
     * @return
     */
    public String getEndAddress() { return this.getEndAddress(); }

    /**
     * Steps getter
     * @return
     */
    public JSONArray getSteps() { return this.steps; }

    /**
     * Route to string
     * @return
     */
    public String toString() {
        return "Duration: " + this.duration + ", Distance: " + this.distance + ", Street Address: " + this.startAddress +
                ", End Address: " + this.endAddress + ", Steps: " + this.steps.toString();
    }

    /**
     * Route as JSON
     * @return
     */
    public JSONObject toJSON() {

        JSONObject obj = new JSONObject();
        obj.put("duration", this.duration);
        obj.put("distance", this.distance);
        obj.put("startAddress", this.startAddress);
        obj.put("endAddress", this.endAddress);
        obj.put("steps", this.steps);

        return obj;

    }

    /**
     * No arg constructor
     */
    public Route() { super(); }

    /**
     * JSON constructor
     * @param obj
     */
    public Route(JSONObject obj) {
        super();

        if(obj.has("duration")) {
            this.duration = obj.getFloat("duration");
        }

        if(obj.has("distance")) {
            this.distance = obj.getFloat("distance");
        }

        if(obj.has("startAddress")) {
            this.startAddress = obj.getString("startAddress");
        }

        if(obj.has("endAddress")) {
            this.endAddress = obj.getString("endAddress");
        }

        if(obj.has("steps")) {
            this.steps = obj.getJSONArray("steps");
        }

    }

    /**
     * Arged constructor
     * @param duration
     * @param distance
     * @param startAddress
     * @param endAddress
     * @param steps
     */
    public Route(float duration, float distance, String startAddress, String endAddress, JSONArray steps) {
        super();

        this.duration = duration;
        this.distance = distance;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.steps = steps;
    }

}

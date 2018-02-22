package com.gulley.dustin.data;

import org.json.JSONObject;

public class HourRange extends BaseData {

    protected int day;          //Calendar day
    protected int hoursBegin;   //Hour as minutes past midnight
    protected int hoursEnd;     //Hour as minutes past midnight

    /**
     * Day Getter
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * Hours Begin Getter
     * @return
     */
    public int getHoursBegin() {
        return hoursBegin;
    }

    /**
     * Hours End Getter
     * @return
     */
    public int getHoursEnd() {
        return hoursEnd;
    }

    /**
     * Day Setter
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Hours Begin Setter
     * @param hoursBegin
     */
    public void setHoursBegin(int hoursBegin) {
        this.hoursBegin = hoursBegin;
    }

    /**
     * Hours End Setter
     * @param hoursEnd
     */
    public void setHoursEnd(int hoursEnd) {
        this.hoursEnd = hoursEnd;
    }

    /**
     * Return HourRange as JSON data
     * @return
     */
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("day", this.day);
        obj.put("hoursBegin", this.hoursBegin);
        obj.put("hoursEnd", this.hoursEnd);

        return obj;
    }

    /**
     * No arg constructor
     */
    public HourRange() {
        super();
    }

    /**
     * JSON Constructor
     * @param obj
     */
    public HourRange(JSONObject obj) {
        super();

        if(obj.has("day")) {
            this.day = obj.getInt("day");
        }

        if(obj.has("hoursBegin")) {
            this.hoursBegin = obj.getInt("hoursBegin");
        }

        if(obj.has("hoursEnd")) {
            this.hoursEnd = obj.getInt("hoursEnd");
        }
    }

    /**
     * Arged constructor
     * @param day
     * @param hoursBegin
     * @param hoursEnd
     */
    public HourRange(int day, int hoursBegin, int hoursEnd) {
        super();
        this.day = day;
        this.hoursBegin = hoursBegin;
        this.hoursEnd = hoursEnd;
    }
}

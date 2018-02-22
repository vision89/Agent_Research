package com.gulley.dustin.data;

import java.util.Calendar;

public class Data {

    public static final String GOOGLE_LOG_TAG = "GooglePlacesAPITest";
    public static final String GOOGLE_PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String GOOGLE_DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions";
    public static final String GOOGLE_TYPE_DETAILS= "/details";
    public static final String GOOGLE_TYPE_SEARCH = "/nearbysearch";
    public static final String GOOGLE_OUT_JSON = "/json";
    public static final String GOOGLE_API_KEY = "";

    public static final String COM_TYPE_KEY = "communicationType";

    public static final String GOOGLE_KEYWORD_RESTAURANT = "restaurant";

    public enum AgentType {
        Handler,
        User,
        Restaurant,
        Decider,
        Transportation
    }

    public enum CommunicationType {
        Query,
        Response
    }

    public enum DataTypes {
        Area,
        AreaArray,
        HourRange,
        HourRangeArray,
        Location,
        LocationArray,
        Person,
        PersonArray,
        Place,
        PlaceArray,
        PublicLocation,
        PublicLocationArray,
        Work,
        WorkArray,
        RoutePair,
        RoutePairArray
    }

    /**
     * Get enum Day from String day
     * @param day
     * @return
     */
    public static int getDay(String day) {

        switch (day.toLowerCase()) {
            case "monday":
                return Calendar.MONDAY;
            case "tuesday":
                return Calendar.TUESDAY;
            case "wednesday":
                return Calendar.WEDNESDAY;
            case "thursday":
                return Calendar.THURSDAY;
            case "friday":
                return Calendar.FRIDAY;
            case "saturday":
                return Calendar.SATURDAY;
            case "sunday":
                return Calendar.SUNDAY;
            default:
                return -1;
        }

    }

    /**
     * Get current hour + minutes since midnight
     * @return
     */
    public static int getCurrentMinutes() {
        Calendar rightNow = Calendar.getInstance();
        return (rightNow.get(Calendar.HOUR_OF_DAY) * 60) + rightNow.get(Calendar.MINUTE);
    }

    public static int minutesToHours(int seconds) {
        return Math.floorDiv(seconds, 60);
    }

    public static int minutesPastHour(int seconds, int hour) {
        int remainder = seconds - (hour * 60);
        return remainder / 60;
    }

    /**
     * Returns an hour range from a list of hour ranges
     * @param hrs
     * @param day
     * @return
     */
    public static HourRange getSingleHourRange(HourRange[] hrs, int day) {

        HourRange tempRange = null;

        for(HourRange hr : hrs) {
            if(hr.getDay() == day) {
                return hr;
            }
            tempRange = hr;
        }

        return tempRange;

    }

}

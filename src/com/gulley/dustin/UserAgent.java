package com.gulley.dustin;

import com.gulley.dustin.data.*;
import com.gulley.dustin.utilities.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class UserAgent extends BaseAgent {

    private Person user;                //Holds user data
    private String datafile;            //Holds the datafile for the user
    private int max;                    //Max recommendations to return
    private Preferences preferences;    //User preferences

    /**
     * Get a generic hour range for work/lunch
     * @param jsonObj
     * @return
     */
    private HourRange getHourRange(JSONObject jsonObj) {
        HourRange hr = new HourRange(Data.getDay(jsonObj.getString("day")),
                jsonObj.getInt("hours-begin"),
                jsonObj.getInt("hours-end"));
        return hr;
    }

    /**
     * Parse the person data from a json obj to a person obj
     * @param jsonObj
     * @return
     */
    private Person parsePersonData(JSONObject jsonObj) {

        JSONObject jsonPerson = jsonObj.getJSONObject("person");                    //json person data
        JSONObject jsonArea = jsonPerson.getJSONObject("area");                     //json person address + radius
        JSONObject jsonLocation = jsonArea.getJSONObject("location");               //json person address
        JSONObject jsonWork = jsonPerson.getJSONObject("work");                     //json person work
        JSONObject jsonWorkArea = jsonWork.getJSONObject("area");                   //json person work address + radius
        JSONObject jsonWorkLocation = jsonWorkArea.getJSONObject("location");       //json person work address
        JSONArray jsonWorkHourRanges = jsonWork.getJSONArray("work-hours");    //json person work hours
        JSONArray jsonLunchHourRanges = jsonWork.getJSONArray("lunch-hours");  //json person lunch hours

        /**
         * Get the person's address
         */
        Area personArea = new Area(jsonLocation.getString("street"),
                jsonLocation.getString("city"),
                jsonLocation.getString("state"),
                jsonLocation.getString("zip"),
                jsonLocation.getFloat("lat"),
                jsonLocation.getFloat("lng"),
                jsonArea.getInt("radius"));

        /**
         * Get the person's work data
         */
        Work personWork = new Work(jsonWorkLocation.getString("street"),
                jsonWorkLocation.getString("city"),
                jsonWorkLocation.getString("state"),
                jsonWorkLocation.getString("zip"),
                jsonWorkLocation.getFloat("lat"),
                jsonWorkLocation.getFloat("lng"),
                jsonWorkArea.getInt("radius"));

        /**
         * Add the persons work hours
         */
        for(int i = 0;i < jsonWorkHourRanges.length(); ++i) {
            JSONObject tempObj = jsonWorkHourRanges.getJSONObject(i);
            HourRange tempRange = getHourRange(tempObj);
            personWork.addWorkHours(tempRange);
        }

        /**
         * Add the persons lunch hours
         */
        for(int i = 0;i < jsonLunchHourRanges.length(); ++i) {
            JSONObject tempObj = jsonLunchHourRanges.getJSONObject(i);
            HourRange tempRange = getHourRange(tempObj);
            personWork.addLunchHours(tempRange);
        }

        /**
         * Create the person
         */
        Person person = new Person(jsonPerson.getString("name"),
                personWork,
                personArea,
                jsonPerson.getString("email"));

        return person;

    };

    /**
     * Get the preference information from JSON
     * @param obj
     * @return
     */
    private Preferences parsePreferenceData(JSONObject obj) {

        Preferences preferences = null;

        if(obj.has("preferences")) {

            JSONObject jsonPreferences = obj.getJSONObject("preferences");
            preferences = new Preferences(jsonPreferences);

        }

        return preferences;

    }

    void work() {
        Work work = this.user.getWork();
        HourRange[] workHours = work.getWorkHours();
        HourRange[] lunchHours = work.getLunchHours();
        Calendar calendar = Calendar.getInstance();
        HourRange workDay = Data.getSingleHourRange(workHours, calendar.get(Calendar.DAY_OF_WEEK));
        HourRange lunchDay = Data.getSingleHourRange(lunchHours, calendar.get(Calendar.DAY_OF_WEEK));
        int lunchHourBegin = Data.minutesToHours(lunchDay.getHoursBegin());
        int lunchDuration = lunchDay.getHoursEnd() - lunchDay.getHoursBegin();
        int currentTime = Data.getCurrentMinutes();

        if((lunchHourBegin - currentTime) < 200) {
            //System.out.println("In the zone");
        }

        JSONObject obj = new JSONObject();
        obj.put("requesterId", this.getId());
        obj.put("requestee", Data.AgentType.Restaurant);
        obj.put("requesterType", (Data.AgentType) Data.AgentType.User);
        obj.put("latitude", work.getLatitude());
        obj.put("longitude", work.getLongitude());
        obj.put("radius", work.getRadius());
        obj.put(Data.COM_TYPE_KEY, Data.CommunicationType.Query);

        this.handler.message(obj);

    }

    /**
     * Get/Receive messages
     * @param obj
     */
    void message(JSONObject obj) {

        if(obj.has(Data.COM_TYPE_KEY)) {
            Data.CommunicationType type = (Data.CommunicationType) obj.get(Data.COM_TYPE_KEY);

            if(type.equals(Data.CommunicationType.Query)) {

                Data.DataTypes dt = (Data.DataTypes) obj.get("dataType");

                if(dt.equals(Data.DataTypes.RoutePairArray)) {

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("requesterId", this.getId());
                    jsonObj.put("requesterType", (Data.AgentType) Data.AgentType.User);
                    jsonObj.put("requestee", Data.AgentType.Decider);
                    jsonObj.put("data", obj.get("data"));
                    jsonObj.put("max", this.max);
                    jsonObj.put("dataType", Data.DataTypes.RoutePairArray);
                    jsonObj.put(Data.COM_TYPE_KEY, Data.CommunicationType.Query);
                    jsonObj.put("preferences", this.preferences.toJSON());

                    this.handler.message(jsonObj);

                }

            } else if(type.equals(Data.CommunicationType.Response)) {

                if(obj.get("requesterType").equals(Data.AgentType.Restaurant)) {

                    /**
                     * Query for the routes
                     */
                    JSONArray jsonArr = obj.getJSONArray("data");

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("requesterId", this.getId());
                    jsonObj.put("requestee", Data.AgentType.Transportation);
                    jsonObj.put("requesterType", (Data.AgentType) Data.AgentType.User);
                    jsonObj.put("from", this.user.getHome().toJson());
                    jsonObj.put("to", jsonArr);
                    jsonObj.put("dataType", Data.DataTypes.PublicLocationArray);
                    jsonObj.put(Data.COM_TYPE_KEY, Data.CommunicationType.Query);

                    this.handler.message(jsonObj);

                } else if(obj.get("requesterType").equals(Data.AgentType.Decider)) {

                    JSONArray tempArr = obj.getJSONArray("data");

                    //Get random restaurant
                    Random randIndex = new Random();
                    int index = randIndex.nextInt(tempArr.length());
                    JSONObject selectedRestaurantJson = tempArr.getJSONObject(index);
                    PublicLocation tempPublicLocation = new PublicLocation(selectedRestaurantJson.getJSONObject("first"));

                    HistoricItem chosenRestaurant = new HistoricItem();
                    chosenRestaurant.setId(tempPublicLocation.getPlaceId());
                    chosenRestaurant.setType(Data.DataTypes.PublicLocationArray);
                    chosenRestaurant.setTypeDescription("Restaurant");
                    chosenRestaurant.setIsoDate(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
                            .withZone(ZoneOffset.UTC)
                            .format(Instant.now()));

                    System.out.println(tempPublicLocation.toString());

                }

            }
        }

    }

    /**
     * User agent constructor
     * @param logfile
     */
    public UserAgent(String logfile, String datafile) {
        super(logfile, Data.AgentType.User);

        this.datafile = datafile;
        this.max = 1;

        //Read input file
        StringBuilder sb = this.getContentsOfFile(datafile);

        /**
         * Create the user
         */
        if(!sb.toString().isEmpty()) {
            JSONObject jsonObj = stringBuilderToJSON(sb);
            this.user = parsePersonData(jsonObj);
            this.preferences = parsePreferenceData(jsonObj);

            if(jsonObj.has("max")) {
                this.max = jsonObj.getInt("max");
            }
        }
    }
}

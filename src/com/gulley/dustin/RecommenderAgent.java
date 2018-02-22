package com.gulley.dustin;

import com.gulley.dustin.data.Data;
import com.gulley.dustin.utilities.Pair;
import com.gulley.dustin.data.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class RecommenderAgent extends BaseAgent {

    /*
    private Route getRouteInformation(PublicLocation location) {

    }
    */

    /**
     * Remove closed locations
     * @param pairs
     * @return
     */
    private  ArrayList<Pair<PublicLocation, Route>> removeClosedRestaurants(ArrayList<Pair<PublicLocation, Route>> pairs) {

        ArrayList<Pair<PublicLocation, Route>> workingPairs = new ArrayList<Pair<PublicLocation, Route>>();

        for(Pair<PublicLocation, Route> pair : pairs) {

            if(pair.getFirst().getOpenNow() == true) {

                workingPairs.add(pair);

            }

        }

        return workingPairs;

    }

    /**
     * Return repeatedness for a given restaurant
     * @param inpair
     * @return
     */
    private int getRepeatednessForRestaurant(Pair<PublicLocation, Route> inpair) {

        return 1;

    }

    /**
     * Curate restaurants by the users preferences
     * @param pairs
     * @param max
     * @param preferences
     * @return
     */
    private ArrayList<Pair<PublicLocation, Route>> curateRestaurants(ArrayList<Pair<PublicLocation, Route>> pairs, int max,
                                                            Preferences preferences) {

        ArrayList<Pair<PublicLocation, Route>> workingPairs = pairs;

        /**
         * Remove all restaurants that are closed
         */
        if(preferences.getOpen() == -1) {
            workingPairs = removeClosedRestaurants(workingPairs);
        }

        /**
         * Calculate weights
         */
        for(Pair<PublicLocation, Route> pair : workingPairs) {
            int open = 0;

            if(!pair.getFirst().getOpenNow()) {
                open = 1;
            }

            pair.getFirst().setWeight(preferences.calculate((int) (pair.getSecond().getDistance() + pair.getSecond().getSteps().length()),
                    pair.getFirst().getPriceLevel(), (int) (5 - pair.getFirst().getRating()),
                    1, getRepeatednessForRestaurant(pair), open));
        }

        ArrayList<Pair<PublicLocation, Route>> curatedPairs = new ArrayList<Pair<PublicLocation, Route>>();

        /**
         * If there are less pairs then the max we want to exit when the size is 0
         * otherwise we will exit when the curated pairs size surpasses the max
         */
        while((curatedPairs.size() < max) && (workingPairs.size() > 0)) {

            int index = -1;

            for(int i = 0;i < workingPairs.size();++i) {

                if((index == -1) ||
                        workingPairs.get(i).getFirst().getWeight() < workingPairs.get(index).getFirst().getWeight()) {
                    index = i;
                }

            }

            curatedPairs.add(workingPairs.get(index));
            workingPairs.remove(index);

        }

        return curatedPairs;

    }

    void work() {

    }

    void message(JSONObject obj) {

        if(obj.has(Data.COM_TYPE_KEY)) {

            Data.CommunicationType type = (Data.CommunicationType) obj.get(Data.COM_TYPE_KEY);

            if (type.equals(Data.CommunicationType.Query)) {

                if(obj.has("dataType")) {

                    Data.DataTypes dt = (Data.DataTypes) obj.get("dataType");

                    if(dt.equals(Data.DataTypes.RoutePairArray)) {

                        JSONArray tempData = obj.getJSONArray("data");
                        ArrayList<Pair<PublicLocation, Route>> pairs = new ArrayList<Pair<PublicLocation, Route>>();

                        for(int i=0;i<tempData.length();++i) {
                            JSONObject tempObj = tempData.getJSONObject(i);
                            PublicLocation tempPublicLocation = new PublicLocation(tempObj.getJSONObject("first"));
                            Route tempRoute = new Route(tempObj.getJSONObject("second"));
                            Pair<PublicLocation, Route> tempPair = new Pair<PublicLocation, Route>(tempPublicLocation, tempRoute);
                            pairs.add(tempPair);
                        }

                        int max = obj.getInt("max");
                        Preferences preferences = new Preferences(obj.getJSONObject("preferences"));

                        pairs = curateRestaurants(pairs, max, preferences);

                        /**
                         * Package up the curated lists to send to the user
                         */
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("requesterId", this.getId());
                        jsonObj.put("requesteeId", (UUID) obj.get("requesterId"));
                        jsonObj.put("data", pairs);
                        jsonObj.put("dataType", Data.DataTypes.PublicLocationArray);
                        jsonObj.put("requesterType", (Data.AgentType) Data.AgentType.Decider);
                        jsonObj.put(Data.COM_TYPE_KEY, Data.CommunicationType.Response);

                        this.handler.message(jsonObj);

                    }

                }

            } else if (type.equals(Data.CommunicationType.Response)) {

            }

        }

    }

    /**
     * Constructor
     * @param logfile
     */
    public RecommenderAgent(String logfile) { super(logfile, Data.AgentType.Decider); }

}

package com.gulley.dustin;

import com.gulley.dustin.data.*;
import com.gulley.dustin.utilities.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class TransportationAgent extends BaseAgent {

    public static Route routeSearch(String fromAddress, String toPlaceId) {

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        Route route = null;

        try {
            StringBuilder sb = new StringBuilder(Data.GOOGLE_DIRECTIONS_API_BASE);
            sb.append(Data.GOOGLE_OUT_JSON);
            sb.append("?origin=" + fromAddress.replaceAll(" ", "_"));
            sb.append("&destination=place_id:" + toPlaceId);
            sb.append("&key=" + Data.GOOGLE_API_KEY);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }

            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            if(jsonObj.has("routes")) {
                JSONArray routesArr = jsonObj.getJSONArray("routes");
                for(int i = 0;i < routesArr.length(); ++i) {
                    JSONObject obj = routesArr.getJSONObject(i);
                    if(obj.has("legs")) {
                        JSONArray jr = obj.getJSONArray("legs");

                        for(int j = 0;j < jr.length(); ++j) {
                            JSONObject jObj = jr.getJSONObject(j);
                            route = new Route();
                            if(jObj.has("duration")) {
                                route.setDuration(jObj.getJSONObject("duration").getFloat("value"));
                            }
                            if(jObj.has("distance")) {
                                route.setDistance(jObj.getJSONObject("distance").getFloat("value"));
                            }
                            if(jObj.has("start_address")) {
                                route.setStartAddress(jObj.getString("start_address"));
                            }
                            if(jObj.has("end_address")) {
                                route.setEndAddress(jObj.getString("end_address"));
                            }
                            if(jObj.has("steps")) {
                                route.setSteps(jObj.getJSONArray("steps"));
                            }
                        }
                    }
                }
            }

        } catch (MalformedURLException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", Error processing Places API URL, " + e.toString());
            return null;
        } catch (IOException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", Error connecting to Places API, " + e.toString());
        } catch (Exception e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", General error, " + e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return route;
    }

    /**
     * Receive messages
     * @param obj
     */
    void message(JSONObject obj) {

        if(obj.has(Data.COM_TYPE_KEY)) {
            Data.CommunicationType type = (Data.CommunicationType) obj.get(Data.COM_TYPE_KEY);

            if(type.equals(Data.CommunicationType.Query)) {

                JSONObject fromObj = obj.getJSONObject("from");
                Area home = new Area(fromObj);
                Data.DataTypes dt = (Data.DataTypes) obj.get("dataType");

                if(dt == Data.DataTypes.PublicLocationArray) {

                    JSONArray toObjs = obj.getJSONArray("to");
                    ArrayList<PublicLocation> locations = new ArrayList<PublicLocation>();
                    ArrayList<Pair<PublicLocation, Route>> routePairs = new ArrayList<Pair<PublicLocation, Route>>();

                    for(int i=0;i<toObjs.length();++i) {
                        locations.add(new PublicLocation(toObjs.getJSONObject(i)));
                    }

                    for(PublicLocation location : locations) {
                        Route tempRoute = routeSearch(home.getAddress(), location.getPlaceId());
                        Pair<PublicLocation, Route> tempPair = new Pair<PublicLocation, Route>();
                        tempPair.setFirst(location);
                        tempPair.setSecond(tempRoute);
                        routePairs.add(tempPair);
                    }

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("requesterId", this.getId());
                    jsonObj.put("requesteeId", (UUID) obj.get("requesterId"));
                    jsonObj.put("requesterType", (Data.AgentType) Data.AgentType.Transportation);
                    jsonObj.put("data", routePairs);
                    jsonObj.put("dataType", Data.DataTypes.RoutePairArray);
                    jsonObj.put(Data.COM_TYPE_KEY, Data.CommunicationType.Query);

                    this.handler.message(jsonObj);

                }

            } else if(type.equals(Data.CommunicationType.Response)) {

                System.out.println("Response received:");

            }
        }

    }

    /**
     * Work to do
     */
    void work() {

    }

    public TransportationAgent(String logfile) {
        super(logfile, Data.AgentType.Transportation);
    }

}

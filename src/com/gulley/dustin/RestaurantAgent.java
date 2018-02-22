package com.gulley.dustin;

import com.gulley.dustin.data.PublicLocation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import com.gulley.dustin.data.Data;
import com.gulley.dustin.data.Route;

public class RestaurantAgent extends BaseAgent {

    public static ArrayList<PublicLocation> search(String keyword, String type, double lat, double lng, int radius) {

        ArrayList<PublicLocation> resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {

            StringBuilder sb = new StringBuilder(Data.GOOGLE_PLACES_API_BASE);
            sb.append(Data.GOOGLE_TYPE_SEARCH);
            sb.append(Data.GOOGLE_OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + Data.GOOGLE_API_KEY);

            if(keyword.isEmpty() == false) {
                sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
            }

            if(type.isEmpty() == false) {
                sb.append("&type=" + URLEncoder.encode(type, "utf8"));
            }

            sb.append("&location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&radius=" + String.valueOf(radius));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while((read = in.read(buff)) > 0) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", " + "Error processing Places API URL, " + e.toString());
            return resultList;
        } catch (IOException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", Error connecting to Places API, " + e.toString());
        } catch (Exception e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", Unspecified error, " + e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            //Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = new JSONArray();

            if(jsonObj.has("results")) {
                predsJsonArray = jsonObj.getJSONArray("results");
            }

            //Extract the place descriptions from the results
            resultList = new ArrayList<PublicLocation>();

            for(int i=0; i < predsJsonArray.length(); i++) {
                PublicLocation place = new PublicLocation();
                JSONObject temp = predsJsonArray.getJSONObject(i);

                /**
                 * Add the name
                 */
                if(temp.has("name")) {
                    place.setName(predsJsonArray.getJSONObject(i).getString("name"));
                }

                /**
                 * Add types
                 */
                if(temp.has("types")) {
                    JSONArray types = temp.getJSONArray("types");
                    for(Object t : types) {
                        String s = t.toString();
                        place.addType(s);
                    }
                }

                /**
                 * Add rating
                 */
                if(temp.has("rating")) {
                    place.setRating(temp.getFloat("rating"));
                }

                /**
                 * Add Price Level
                 */
                if(temp.has("price_level")) {
                    place.setPriceLevel(temp.getInt("price_level"));
                }

                /**
                 * Add Opening Hours
                 */
                if(temp.has("opening_hours")) {
                    JSONObject jobj = temp.getJSONObject("opening_hours");
                    if(jobj.has("open_now")) {
                        place.setOpenNow(jobj.getBoolean("open_now"));
                    }
                }

                /**
                 * Add Vicinity
                 */
                if(temp.has("vicinity")) {
                    place.setVicinity(temp.getString("vicinity"));
                }

                /**
                 * Add Place Id
                 */
                if(temp.has("place_id")) {
                    place.setPlaceId(temp.getString("place_id"));
                }

                resultList.add(place);
            }

        } catch(JSONException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", " + "Error processing JSON results, " + e.toString());
        }

        return resultList;
    }

    public static PublicLocation details(String reference) {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(Data.GOOGLE_PLACES_API_BASE);
            sb.append(Data.GOOGLE_TYPE_DETAILS);
            sb.append(Data.GOOGLE_OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + Data.GOOGLE_API_KEY);
            sb.append("&reference=" + URLEncoder.encode(reference, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", Error processing Places API URL, " + e.toString());
            return null;
        } catch (IOException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", Error connecting to Places API, " + e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        PublicLocation place = null;

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString()).getJSONObject("result");

            place = new PublicLocation();

            if(jsonObj.has("formatted_address")) {
                //place.setFormattedAddress(jsonObj.getString("formatted_address"));
            }

            if(jsonObj.has("formatted_phone_number")) {
                place.setPhoneNumber(jsonObj.getString("formatted_phone_number"));
            }

            if(jsonObj.has("name")) {
                place.setName(jsonObj.getString("name"));
            }

            if(jsonObj.has("vicinity")) {
                place.setVicinity(jsonObj.getString("vicinity"));
            }

            if(jsonObj.has("place_id")) {
                place.setPlaceId(jsonObj.getString("place_id"));
            }

            if(jsonObj.has("website")) {
                place.setWebsite(jsonObj.getString("website"));
            }

        } catch(JSONException e) {
            System.out.println(Data.GOOGLE_LOG_TAG + ", Error processing JSON results, " + e.toString());
        }

        return place;
    }

    void work() {
    }

    /**
     * Send/Receive messages
     * @param obj
     */
    void message(JSONObject obj) {

        if(obj.has(Data.COM_TYPE_KEY)) {

            Data.CommunicationType type = (Data.CommunicationType) obj.get(Data.COM_TYPE_KEY);

            if(type.equals(Data.CommunicationType.Query)) {

                ArrayList<PublicLocation> places = search(Data.GOOGLE_KEYWORD_RESTAURANT,
                        "",
                        obj.getDouble("latitude"),
                        obj.getDouble("longitude"),
                        obj.getInt("radius"));

                JSONObject respondObj = new JSONObject();
                respondObj.put("requesterId", this.getId());
                respondObj.put("requesteeId", obj.get("requesterId"));
                respondObj.put("requesterType", (Data.AgentType) Data.AgentType.Restaurant);
                respondObj.put("data", places);
                respondObj.put(Data.COM_TYPE_KEY, Data.CommunicationType.Response);

                handler.message(respondObj);

            } else if(type.equals(Data.CommunicationType.Response)) {

            }

        }

    }

    /**
     * Arged constructor
     * @param logfile
     */
    public RestaurantAgent(String logfile) {
        super(logfile, Data.AgentType.Restaurant);
    }

}

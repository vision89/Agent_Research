package com.gulley.dustin.data;

import org.json.JSONObject;

import java.util.ArrayList;

public class PublicLocation extends Location {

    protected String name;              //name of location
    protected String placeId;           //Google id of location
    protected String website;           //Website of location if one exists
    protected String vicinity;          //Object near place
    protected String phoneNumber;       //Phone number for location
    protected ArrayList<String> types;  //Type of location
    protected Float rating;             //Location rating
    protected int priceLevel;           //Price level
    protected boolean openNow;          //Predicate if open now

    /**
     * Name Getter
     * @return
     */
    public String getName() { return this.name; }

    /**
     * PlaceId Getter
     * @return
     */
    public String getPlaceId() { return this.placeId; }

    /**
     * Website Getter
     * @return
     */
    public String getWebsite() { return this.website; }

    /**
     * Vicinity Getter
     * @return
     */
    public String getVicinity() { return this.vicinity; }

    /**
     * Phone Number Getter
     * @return
     */
    public String getPhoneNumber() { return this.phoneNumber; }

    /**
     * Rating Getter
     * @return
     */
    public Float getRating() { return this.rating; }

    /**
     * Price Level Getter
     * @return
     */
    public int getPriceLevel() { return this.priceLevel; }

    /**
     * Open Now Getter
     * @return
     */
    public boolean getOpenNow() { return this.openNow; }

    /**
     * Name Setter
     * @param name
     */
    public void setName(String name) { this.name = name; }

    /**
     * PlaceId Setter
     * @param placeId
     */
    public void setPlaceId(String placeId) { this.placeId = placeId; }

    /**
     * Website Setter
     * @param website
     */
    public void setWebsite(String website) { this.website = website; }

    /**
     * Vicinity Setter
     * @param vicinity
     */
    public void setVicinity(String vicinity) { this.vicinity = vicinity; }

    /**
     * Phone Number Setter
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * Rating Setter
     * @param rating
     */
    public void setRating(Float rating) { this.rating = rating; }

    /**
     * Price Level Setter
     * @param priceLevel
     */
    public void setPriceLevel(int priceLevel) { this.priceLevel = priceLevel; }

    /**
     * Open Now Setter
     * @param openNow
     */
    public void setOpenNow(boolean openNow) { this.openNow = openNow; }

    /**
     * Add a type
     * @param type
     */
    public void addType(String type) {
        if(!this.types.contains(type)) {
            this.types.add(type);
        }
    }

    /**
     * Remove a type
     * @param type
     */
    public void removeType(String type) {
        if(!this.types.contains(type)) {
            this.types.remove(type);
        }
    }

    /**
     * Predicate if location is of a type
     * @param type
     * @return
     */
    public boolean containsType(String type) {
        return this.types.contains(type);
    }

    /**
     * Clear the types
     */
    public void clearTypes() {
        this.types.clear();
    }

    /**
     * Public location as a string
     * @return
     */
    public String toString() {
        String tempString = super.toString();
        tempString += ", name: " + this.name;
        tempString += ", placeId: " + this.placeId.toString();
        tempString += ", website: " + this.website;
        tempString += ", vicinty: " + this.vicinity;
        tempString += ", phoneNumber: " + this.phoneNumber;
        tempString += ", types: " + this.types.toString();
        tempString += ", rating: " + Float.toString(this.rating);
        tempString += ", priceLevel: " + Integer.toString(this.priceLevel);
        tempString += ", openNow: " + Boolean.toString(this.openNow);

        return tempString;
    }

    /**
     * Public location to JSON
     * @return
     */
    public JSONObject toJSON() {

        JSONObject obj = super.toJSON();
        obj.put("name", this.name);
        obj.put("placeId", this.placeId);
        obj.put("website", this.website);
        obj.put("vicinty", this.vicinity);
        obj.put("phoneNumber", this.phoneNumber);
        obj.put("types", this.types);
        obj.put("rating", this.rating);
        obj.put("priceLevel", this.priceLevel);
        obj.put("openNow", this.openNow);

        return obj;

    }

    /**
     * No arg constructor
     */
    public PublicLocation() {
        super();
        this.types = new ArrayList<String>();
    }

    /**
     * JSON constructor
     * @param obj
     */
    public PublicLocation(JSONObject obj) {

        super();

        if(obj.has("latitude")) {
            this.latitude = obj.getFloat("latitude");
        }
        if(obj.has("name")) {
            this.name = obj.getString("name");
        }
        if(obj.has("rating")) {
            this.rating = obj.getFloat("rating");
        }
        if(obj.has("openNow")) {
            this.openNow = obj.getBoolean("openNow");
        }
        if(obj.has("vicinity")) {
            this.vicinity = obj.getString("vicinity");
        }
        if(obj.has("place_id")) {
            this.placeId = obj.getString("place_id");
        } else if(obj.has("placeId")) {
            this.placeId = obj.getString("placeId");
        }
        if(obj.has("priceLevel")) {
            this.priceLevel = obj.getInt("priceLevel");
        }
        if(obj.has("longitude")) {
            this.longitude = obj.getFloat("longitude");
        }

        this.types = new ArrayList<String>();
    }

    /**
     * Arged constructor
     * @param name
     * @param placeId
     * @param website
     * @param vicinity
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param latitude
     * @param longitude
     */
    public PublicLocation(String name, String placeId, String website, String vicinity, String phoneNumber,
                          ArrayList<String> types, Float rating, int priceLevel, boolean openNow,
                          String street, String city, String state, String zip, float latitude, float longitude) {

        super(street, city, state, zip, latitude, longitude);
        this.name = name;
        this.placeId = placeId;
        this.website = website;
        this.vicinity = vicinity;
        this.phoneNumber = phoneNumber;
        this.types = types;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.openNow = openNow;

    }
}

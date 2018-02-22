package com.gulley.dustin.data;

import org.json.JSONObject;

public class Person extends BaseData {

    protected String name;    //Persons name
    protected Work work;      //Persons job
    protected Area home;      //Persons home address
    protected String email;   //Persons email (can be used as unique id)

    /**
     * Get the person's name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the person's work
     * @return
     */
    public Work getWork() {
        return this.work;
    }

    /**
     * Get the person's home location information
     * @return
     */
    public Area getHome() {
        return this.home;
    }

    /**
     * Get the person's email
     * @return
     */
    public String getEmail() { return this.email; }

    /**
     * Set the persons name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the person's work information
     * @param work
     */
    public void setWork(Work work) {
        this.work = work;
    }

    /**
     * Set the person's home information
     * @param home
     */
    public void setHome(Area home) {
        this.home = home;
    }

    /**
     * Set the person's email address
     * @param email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Person data as JSON
     * @return
     */
    public JSONObject toJSON() {

        JSONObject obj = new JSONObject();
        JSONObject workObj = this.work.toJSON();
        JSONObject homeObj = this.home.toJSON();

        obj.put("name", this.name);
        obj.put("work", this.work);
        obj.put("home", this.home);
        obj.put("email", this.email);

        return obj;

    }

    /**
     * No arg constructor
     */
    public Person() {
        super();
    }

    /**
     * JSON Constructor
     * @param obj
     */
    public Person(JSONObject obj) {
        super();

        if(obj.has("name")) {
            this.name = obj.getString("name");
        }

        if(obj.has("work")) {
            Work work = new Work(obj.getJSONObject("work"));
            this.work = work;
        }

        if(obj.has("home")) {
            Area area = new Area(obj.getJSONObject("area"));
            this.home = area;
        }

        if(obj.has("email")) {
            this.email = obj.getString("email");
        }
    }

    /**
     * Args constructor
     * @param name
     * @param work
     * @param home
     */
    public Person(String name, Work work, Area home, String email) {
        super();
        this.name = name;
        this.work = work;
        this.home = home;
        this.email = email;
    }

}

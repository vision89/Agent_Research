package com.gulley.dustin.data;

import org.json.JSONObject;

public class Preferences extends BaseData {

    protected int travel;
    protected int cost;
    protected int reviews;
    protected int selection;
    protected int repeatedness;
    protected int open;

    /**
     * Travel setter
     * -1 means Do not allow
     * 0 means ignore
     * 1-10 is a weight
     * @param travel
     */
    public void setTravel(int travel) {
        if(travel >= -1 && travel <= 10) {
            this.travel = travel;
        }
    }

    /**
     * Cost setter
     * -1 means Do not allow
     * 0 means ignore
     * 1-10 is a weight
     * @param cost
     */
    public void setCost(int cost) {
        if(cost >= -1 && cost <= 10) {
            this.cost = cost;
        }
    }

    /**
     * Reviews setter
     * -1 means Do not allow
     * 0 means ignore
     * 1-10 is a weight
     * @param reviews
     */
    public void setReviews(int reviews) {
        if(reviews >= -1 && reviews <= 10) {
            this.reviews = reviews;
        }
    }

    /**
     * Selection setter
     * -1 means Do not allow
     * 0 means ignore
     * 1-10 is a weight
     * @param selection
     */
    public void setSelection(int selection) {
        if(selection >= -1 && selection <= 10) {
            this.selection = selection;
        }
    }

    /**
     * Repeatedness setter
     * -1 means Do not allow
     * 0 means ignore
     * 1-10 is a weight
     * @param repeatedness
     */
    public void setRepeatedness(int repeatedness) {
        if(repeatedness >= -1 && repeatedness <= 10) {
            this.repeatedness = repeatedness;
        }
    }

    /**
     * Open setter
     * -1 means Do not allow
     * 0 means ignore
     * 1-10 is a weight
     * @param open
     */
    public void setOpen(int open) {
        if(open >= -1 && open <= 10) {
            this.open = open;
        }
    }

    /**
     * Travel Getter
     * @return
     */
    public int getTravel() {
        return this.travel;
    }

    /**
     * Cost Getter
     * @return
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Reviews Getter
     * @return
     */
    public int getReviews() {
        return this.reviews;
    }

    /**
     * Selections Getter
     * @return
     */
    public int getSelection() {
        return this.selection;
    }

    /**
     * Repeatedness Getter
     * @return
     */
    public int getRepeatedness() {
        return this.repeatedness;
    }

    /**
     * Open getter
     * @return
     */
    public int getOpen() {
        return this.open;
    }

    /**
     * Calculate the score
     * @param travel
     * @param cost
     * @param reviews
     * @param selection
     * @param repeatedness
     * @param open
     * @return
     */
    public int calculate(int travel, int cost, int reviews, int selection, int repeatedness, int open) {

        int total = 0;

        if(this.travel > -1) {
            total += travel * this.travel;
        }

        if(this.cost > -1) {
            total += cost * this.cost;
        }

        if(this.reviews > -1) {
            total += reviews * this.reviews;
        }

        if(this.selection > -1) {
            total += selection * this.selection;
        }

        if(this.repeatedness > -1) {
            total += repeatedness * this.repeatedness;
        }

        if(this.open > -1) {
            total += open * this.open;
        }

        return total;

    }

    /**
     * Preferences as a JSON Object
     * @return
     */
    public JSONObject toJSON() {

        JSONObject obj = new JSONObject();
        obj.put("travel", this.travel);
        obj.put("cost", this.cost);
        obj.put("reviews", this.reviews);
        obj.put("selection", this.selection);
        obj.put("repeatedness", this.repeatedness);
        obj.put("open", this.open);

        return obj;
    }

    /**
     * No arg constructor
     */
    public Preferences() { super(); }

    /**
     * JSON Constructor
     * @param obj
     */
    public Preferences(JSONObject obj) {
        super();

        if(obj.has("travel")) {
            this.setTravel(obj.getInt("travel"));
        }

        if(obj.has("cost")) {
            this.setCost(obj.getInt("cost"));
        }

        if(obj.has("reviews")) {
            this.setReviews(obj.getInt("reviews"));
        }

        if(obj.has("selection")) {
            this.setSelection(obj.getInt("selection"));
        }

        if(obj.has("repeatedness")) {
            this.setRepeatedness(obj.getInt("repeatedness"));
        }

        if(obj.has("open")) {
            this.setOpen(obj.getInt("open"));
        }

    }

    /**
     * Arged constructor
     * @param travel
     * @param cost
     * @param reviews
     * @param selection
     * @param repeatedness
     * @param open
     */
    public Preferences(int travel, int cost, int reviews, int selection, int repeatedness, int open) {
        super();
        this.setTravel(travel);
        this.setCost(cost);
        this.setReviews(reviews);
        this.setSelection(selection);
        this.setRepeatedness(repeatedness);
        this.setOpen(open);
    }

}

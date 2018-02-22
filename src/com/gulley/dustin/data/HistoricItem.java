package com.gulley.dustin.data;

import org.json.JSONObject;

public class HistoricItem extends BaseData {
    protected String id;                // Id of specific item
    protected Data.DataTypes type;      // Datatype
    protected String typeDescription;   // Description of type ie Restaraunt for public location
    protected String isoDate;           // Date stored

    /**
     * Id getter
     * @return
     */
    public String getId() { return id; }

    /**
     * Datatype getter
     * @return
     */
    public Data.DataTypes getType() { return type; }

    /**
     * Iso Date getter
     * @return
     */
    public String getIsoDate() { return isoDate; }

    /**
     * Type description getter
     * @return
     */
    public String getTypeDescription() { return typeDescription; }

    /**
     * Id setter
     * @param id
     */
    public void setId(String id) { this.id = id; }

    /**
     * Type setter
     * @param type
     */
    public void setType(Data.DataTypes type) { this.type = type; }

    /**
     * ISO Date setter
     * @param isoDate
     */
    public void setIsoDate(String isoDate) { this.isoDate = isoDate; }

    /**
     * Type description setter
     * @param typeDescription
     */
    public void setTypeDescription(String typeDescription) { this.typeDescription = typeDescription; }

    /**
     * Historic item as a json object
     * @return
     */
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("type", this.getType());
        obj.put("typeDescription", this.getTypeDescription());
        obj.put("isoDate", this.getIsoDate());
        return obj;
    }

    /**
     * Historic item as a string
     * @return
     */
    public String toString() {
        String tempString = super.toString();
        tempString += ", id: " + this.getId();
        tempString += ", type: " + this.getType().toString();
        tempString += ", typeDescription: " + this.getTypeDescription();
        tempString += ", isoDate: " + this.getIsoDate();

        return tempString;
    }

    /**
     * No arg constructor
     */
    public HistoricItem() { super(); }

    /**
     * JSON constructor
     * @param obj
     */
    public HistoricItem(JSONObject obj) {
        super();
        this.isoDate = obj.getString("isoDate");
        this.type = (Data.DataTypes) obj.get("type");
        this.id = obj.getString("id");
        this.typeDescription = obj.getString("typeDescription");
    }

    /**
     * Arged constructor
     * @param id
     * @param type
     * @param typeDescription
     * @param isoDate
     */
    public HistoricItem(String id, Data.DataTypes type, String typeDescription, String isoDate) {
        super();
        this.id = id;
        this.type = type;
        this.typeDescription = typeDescription;
        this.isoDate = isoDate;
    }
}

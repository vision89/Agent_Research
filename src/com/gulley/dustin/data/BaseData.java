package com.gulley.dustin.data;

import com.sun.xml.internal.rngom.parse.host.Base;

import java.util.ArrayList;

public class BaseData {

    protected float weight; //Weight for recommendations

    /**
     * Weight getter
     * @param weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Weight setter
     * @return
     */
    public float getWeight() {
        return this.weight;
    }

    /**
     * Basedata to string
     * @return
     */
    public String toString() {
        String tempString = "";
        tempString += "weight: " + Float.toString(this.weight);
        return tempString;
    }

    /**
     * No arg constructor
     */
    public BaseData() {
        this.weight = 0.0f;
    }
}

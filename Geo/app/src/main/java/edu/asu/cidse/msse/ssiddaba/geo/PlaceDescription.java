package edu.asu.cidse.msse.ssiddaba.geo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Vector;

/**
 * Copyright (c) 2021 Somesh Siddabasappa,
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Purpose: SER 423 Course Assignment
 * <p>
 * Right to use: I provide the instructor and the University with the right to
 * build and evaluate the software package for the purpose of determining your
 * grade and program assessment.
 *
 * @author Somesh Siddabasappa ssiddaba@asu.edu
 * Software Engineering, CIDSE, MSSE, Arizona State University Polytechnic
 * @version March 25, 2021
 */

public class PlaceDescription implements Serializable {

    private String name;
    private String description;
    private String category;
    private String addressTitle;
    private String addressStreet;

    private double elevation;
    private double latitude;
    private double longitude;

    public static final String LOG_TAG =
            PlaceDescription.class.getSimpleName();

    public PlaceDescription() {
        this.name = "";
        this.description = "";
        this.category = "";
        this.addressTitle = "";
        this.addressStreet = "";
        this.elevation = 0;
        this.latitude = 0;
        this.longitude = 0;
    }

    public PlaceDescription(String jsonString) {

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            this.name = jsonObject.getString("name");
            this.description = jsonObject.getString("description");
            this.category = jsonObject.getString("category");
            this.addressTitle = jsonObject.getString("address-title");
            this.addressStreet = jsonObject.getString("address-street");
            this.elevation = jsonObject.getDouble("elevation");
            this.latitude = jsonObject.getDouble("latitude");
            this.longitude = jsonObject.getDouble("longitude");
        }catch (Exception ex){
            android.util.Log.w(LOG_TAG,
                    "error converting from json");
        }
    }

    public String toJsonString(){
        String ret = "";
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",this.name);
            jsonObject.put("description",this.description);
            jsonObject.put("category",this.category);
            jsonObject.put("address-title",this.addressTitle);
            jsonObject.put("address-street",this.addressStreet);
            jsonObject.put("elevation",this.elevation);
            jsonObject.put("latitude",this.latitude);
            jsonObject.put("longitude",this.longitude);
        }catch (Exception ex){
            android.util.Log.w(LOG_TAG,
                    "error converting to json");
        }
        return ret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PlaceDescription{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", addressTitle='" + addressTitle + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", elevation=" + elevation +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

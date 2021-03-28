package edu.asu.cidse.msse.ssiddaba.geo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

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
 * @version March 27, 2021
 */

public class PlaceLibrary {

    private static PlaceLibrary single_instance = null;

    private Context context;

    private PlaceLibrary() {}

    public static PlaceLibrary getInstance()
    {
        if (single_instance == null)
            single_instance = new PlaceLibrary();

        return single_instance;
    }

    public void initializeDB(Context context) {
        this.context = context;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> placeNames = new ArrayList<>();

        try {
            PlaceDB placeDB = new PlaceDB(context);
            SQLiteDatabase db = placeDB.openDB();
            Cursor cur = db.rawQuery("select name from place;", new String[]{});
            while(cur.moveToNext()){
                try{
                    placeNames.add(cur.getString(0));
                }catch(Exception ex){
                    android.util.Log.w(this.getClass().getSimpleName(),
                            "exception stepping through cursor"+ex.getMessage());
                }
            }
            cur.close();
            db.close();
            placeDB.close();
        } catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(),"unable to get place names");
        }
        Collections.sort(placeNames);
        return placeNames;
    }

    public PlaceDescription get(String name) {
        PlaceDescription place = new PlaceDescription();
        try {
            PlaceDB placeDB = new PlaceDB(context);
            SQLiteDatabase db = placeDB.openDB();
            Cursor cur = db.rawQuery("select name, description, category, address_title, " +
                    "address_street, elevation, latitude, longitude " +
                    "from place where name=?;", new String[]{name});
            while(cur.moveToNext()){
                place.setName(cur.getString(0));
                place.setDescription(cur.getString(1));
                place.setCategory(cur.getString(2));
                place.setAddressTitle(cur.getString(3));
                place.setAddressStreet(cur.getString(4));
                place.setElevation(Double.parseDouble(cur.getString(5)));
                place.setLatitude(Double.parseDouble(cur.getString(6)));
                place.setLongitude(Double.parseDouble(cur.getString(7)));
            }
            cur.close();
            db.close();
            placeDB.close();
        } catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(),"unable to get place details");
        }
        return  place;
    }

    public void add(PlaceDescription place) {
        try {
            PlaceDB placeDB = new PlaceDB(context);
            SQLiteDatabase db = placeDB.openDB();

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", place.getName());
            contentValues.put("description", place.getDescription());
            contentValues.put("category", place.getCategory());
            contentValues.put("address_title", place.getAddressTitle());
            contentValues.put("address_street", place.getAddressStreet());
            contentValues.put("elevation", place.getElevation());
            contentValues.put("latitude", place.getLatitude());
            contentValues.put("longitude", place.getLongitude());

            db.insert("place",null, contentValues);

            db.close();
            placeDB.close();
        } catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(),"unable to get add new place");
        }
    }

    public void update(PlaceDescription place) {
        try {
            PlaceDB placeDB = new PlaceDB(context);
            SQLiteDatabase db = placeDB.openDB();

            ContentValues contentValues = new ContentValues();
            contentValues.put("description", place.getDescription());
            contentValues.put("category", place.getCategory());
            contentValues.put("address_title", place.getAddressTitle());
            contentValues.put("address_street", place.getAddressStreet());
            contentValues.put("elevation", place.getElevation());
            contentValues.put("latitude", place.getLatitude());
            contentValues.put("longitude", place.getLongitude());

            db.update("place", contentValues, "name=?",
                    new String[]{place.getName()});

            db.close();
            placeDB.close();
        } catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(),"unable to save place");
        }
    }

    public void remove(String name) {
        String delete = "delete from place where student.name=?;";
        try {
            PlaceDB placeDB = new PlaceDB(context);
            SQLiteDatabase db = placeDB.openDB();

            db.execSQL(delete, new String[]{name});

            db.close();
            placeDB.close();
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(),
                    " error trying to delete place");
        }
    }

    public void loadJSONFromAsset(Context context) {
        try {
            InputStream is = context.getAssets().open("places.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);

            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    String place = ((JSONObject) jsonObject.get(key)).toString();
                    //places.add(new PlaceDescription(place));
                }
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
    }

}

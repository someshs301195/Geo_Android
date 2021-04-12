package edu.asu.msse.ssiddaba.geo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.asu.msse.ssiddaba.geo.PlaceLibrary;
import edu.asu.msse.ssiddaba.geo.R;
import edu.asu.msse.ssiddaba.geo.MainActivity;
import edu.asu.msse.ssiddaba.geo.PlaceDescription;

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
 * @version March 24, 2021
 */

public class PlaceDetailsActivity  extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private EditText category;
    private EditText addressTitle;
    private EditText addressStreet;
    private EditText elevation;
    private EditText latitude;
    private EditText longitude;

    private PlaceDescription place;
    private String placeName;
    private PlaceLibrary placeLibrary;

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_detail);

        placeLibrary = PlaceLibrary.getInstance();

        this.name = findViewById(R.id.name);
        this.description = findViewById(R.id.description);
        this.category = findViewById(R.id.category);
        this.addressTitle = findViewById(R.id.address_title);
        this.addressStreet = findViewById(R.id.address);
        this.elevation = findViewById(R.id.elevation);
        this.latitude = findViewById(R.id.latitude);
        this.longitude = findViewById(R.id.longitude);

        Intent intent = getIntent();

        this.placeName = intent.getStringExtra(MainActivity.PLACE_NAME);

        if(this.placeName != null)
            this.place = placeLibrary.get(placeName);

        if (this.place != null) {
            this.name.setText(place.getName());
            this.description.setText(place.getDescription());
            this.category.setText(place.getCategory());
            this.addressTitle.setText(place.getAddressTitle());
            this.addressStreet.setText(place.getAddressStreet());
            this.elevation.setText(String.valueOf(place.getElevation()));
            this.latitude.setText(String.valueOf(place.getLatitude()));
            this.longitude.setText(String.valueOf(place.getLongitude()));
        }
        else{
            this.place = new PlaceDescription();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_or_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            returnSuccess(item.getActionView());
        }
        else if (id == R.id.action_cancel) {
            returnCancel(item.getActionView());
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean savePlaceDescription() {

        if (this.name.getText().toString().trim().length() == 0) {
            displayToast("Name cannot be empty!!");
            return false;
        }

        place.setName(this.name.getText().toString());
        place.setDescription(this.description.getText().toString());
        place.setCategory(this.category.getText().toString());
        place.setAddressTitle(this.addressTitle.getText().toString());
        place.setAddressStreet(this.addressStreet.getText().toString());

        try {
            Double elevation = Double.parseDouble(this.elevation.getText().toString());
            place.setElevation(elevation);

            Double latitude = Double.parseDouble(this.latitude.getText().toString());
            place.setLatitude(latitude);

            Double longitude = Double.parseDouble(this.longitude.getText().toString());
            place.setLongitude(longitude);
        }
        catch (Exception e) {
            displayToast("Latitude, Longitude and Elevation should be in double format!");
            return false;
        }
        return true;
    }

    public void returnSuccess(View view) {
        boolean saved = savePlaceDescription();
        if (saved) {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.PLACE, this.place);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void returnCancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

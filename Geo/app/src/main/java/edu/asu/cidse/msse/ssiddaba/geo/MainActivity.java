package edu.asu.cidse.msse.ssiddaba.geo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

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

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> placeNames = new ArrayList<>();

    private RecyclerView recyclerView;
    private PlaceListAdapter placeListAdapter;
    private PlaceLibrary placeLibrary;

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    public static final String PLACE_NAME =
            "edu.asu.cidse.msse.ssiddaba.geo.placeName";
    public static final String PLACE_LIST =
            "edu.asu.cidse.msse.ssiddaba.geo.placeList";
    public static final String PLACE =
            "edu.asu.cidse.msse.ssiddaba.geo.place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placeLibrary = PlaceLibrary.getInstance();
        placeLibrary.initializeDB(getApplicationContext());

        if(savedInstanceState != null) {
            placeNames = (ArrayList<String>) savedInstanceState.get("placeNames");
        }
        else{
            placeNames = placeLibrary.getNames();
        }

        // Setup recycler view
        recyclerView = findViewById(R.id.recyclerview);
        placeListAdapter = new PlaceListAdapter(this, placeNames);
        recyclerView.setAdapter(placeListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDelete(placeListAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Setup floating button to add new place
        FloatingActionButton addPlace = findViewById(R.id.fab_add_place);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPlaceDetailsActivity(placeNames.size());
            }
        });

        // Setup floating button to calculate distance and bearing between
        // two places
        FloatingActionButton calculate = findViewById(R.id.fab_calculate_distance);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDistanceAndBearingActivity(placeNames.size());
            }
        });
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

    public void launchPlaceDetailsActivity(int position) {
        Context context = getApplicationContext();
        // create intent and start activity to add new place
        Intent intent =
                new Intent(context, PlaceDetailsActivity.class);

        startActivityForResult(intent, position);
    }

    public void launchDistanceAndBearingActivity(int position) {
        Context context = getApplicationContext();
        // create intent and start activity to add new place
        Intent intent =
                new Intent(context, DistanceAndBearingActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            PlaceDescription newPlace =
                    (PlaceDescription) data.getSerializableExtra(PLACE);

            if (requestCode == placeNames.size()) {
                placeLibrary.add(newPlace);
            } else {
                placeLibrary.update(newPlace);
            }

            refreshTableView();

            // Scroll to the changed view.
            recyclerView.smoothScrollToPosition(requestCode);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (this.placeNames.size() > 0) {
            outState.putSerializable("placeNames", this.placeNames);
        }
    }

    public void refreshTableView() {
        placeNames = placeLibrary.getNames();
        placeListAdapter.setPlaceNames(placeNames);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
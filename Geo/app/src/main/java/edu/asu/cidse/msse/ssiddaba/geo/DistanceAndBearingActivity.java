package edu.asu.cidse.msse.ssiddaba.geo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DistanceAndBearingActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    final double RADIUS = 6371e3;
    final double METER_TO_MILES = 0.00062137;

    private ArrayList<String> placeNames;

    private Spinner spinner_from;
    private Spinner spinner_to;

    private PlaceLibrary placeLibrary;

    private PlaceDescription from;
    private PlaceDescription to;

    LinearLayout distance_result;
    LinearLayout initial_bearing_result;

    TextView result_title;
    TextView distance_value;
    TextView bearing_value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_bearing);

        placeLibrary = PlaceLibrary.getInstance();

        distance_result = findViewById(R.id.distance_result);
        initial_bearing_result = findViewById(R.id.initial_bearing_result);
        result_title = findViewById(R.id.result_title_textView);
        distance_value = findViewById(R.id.distance_value_textView);
        bearing_value = findViewById(R.id.initial_bearing_value_textView);

        if(savedInstanceState != null) {
            placeNames = (ArrayList<String>) savedInstanceState.get("placeNames");
            if (savedInstanceState.getInt("distance_result") == View.VISIBLE) {
                distance_result.setVisibility(View.VISIBLE);
                distance_value.setText(savedInstanceState.getString("distance_value"));
            }
            if (savedInstanceState.getInt("initial_bearing_result") == View.VISIBLE) {
                initial_bearing_result.setVisibility(View.VISIBLE);
                distance_value.setText(savedInstanceState.getString("bearing_value"));
            }
        }
        else{
            placeNames = placeLibrary.getNames();
        }

        // Create the spinners
        spinner_from = findViewById(R.id.spinner_from);
        spinner_to = findViewById(R.id.spinner_to);

        if (spinner_from != null)
            spinner_from.setOnItemSelectedListener(this);

        if (spinner_to != null)
            spinner_to.setOnItemSelectedListener(this);

        // Create ArrayAdapter
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,
                R.layout.spinner_list, placeNames);

        // set adapters to spinners
        spinner_from.setAdapter(adapter);
        spinner_to.setAdapter(adapter);

        spinner_from.setOnItemSelectedListener(this);
        spinner_to.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_from) {
            from = placeLibrary.get(placeNames.get(position));
        }
        else if (parent.getId() == R.id.spinner_to) {
            to = placeLibrary.get(placeNames.get(position));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    public void calulateDistanceAndBearing(View view) {

        distance_result.setVisibility(View.VISIBLE);
        initial_bearing_result.setVisibility(View.VISIBLE);

        double lat1 = from.getLatitude();
        double lon1 = from.getLongitude();

        double lat2 = to.getLatitude();
        double lon2 = to.getLongitude();

        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        double bearing = calculateInitialBearing(lat1, lon1, lat2, lon2);

        String title = from.getName() + " - " + to.getName();

        result_title.setText(title);
        distance_value.setText(String.format("%.2f", distance) + " miles");
        bearing_value.setText(String.format("%.2f", bearing) + " degrees");
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        double phi1 = lat1 * Math.PI/180;
        double phi2 = lat2 * Math.PI/180;

        double delta = (lat2-lat1) * Math.PI/180;
        double lambda = (lon2-lon1) * Math.PI/180;

        double a = Math.sin(delta/2) * Math.sin(delta/2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(lambda/2) * Math.sin(lambda/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        // distance in meters
        double distance = RADIUS * c;

        // Convert to miles
        distance =  distance * METER_TO_MILES;

        return distance;
    }

    private double calculateInitialBearing(double lat1, double lon1, double lat2, double lon2) {

        // Convert degree to radians
        double lat1_r = degreeToRadians(lat1);
        double lon1_r = degreeToRadians(lon1);
        double lat2_r = degreeToRadians(lat2);
        double lon2_r = degreeToRadians(lon2);

        double y = Math.sin(lon2_r-lon1_r) * Math.cos(lat2_r);
        double x = Math.cos(lat1_r)*Math.sin(lat2_r) -
                Math.sin(lat1_r)*Math.cos(lat2_r)*Math.cos(lon2_r-lon1_r);

        double theta = Math.atan2(y, x);

        double bearing = (theta*180/Math.PI + 360) % 360; // in degrees

        return bearing;
    }

    private double degreeToRadians(double degree) {
        return degree * Math.PI / 180;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (this.placeNames.size() > 0) {
            outState.putSerializable("placeNames", this.placeNames);
        }

        TextView result_title = findViewById(R.id.result_title_textView);
        outState.putString("result_title", result_title.getText().toString());
        outState.putInt("distance_result", distance_result.getVisibility());
        outState.putString("distance_value", distance_value.getText().toString());
        outState.putInt("initial_bearing_result", initial_bearing_result.getVisibility());
        outState.putString("bearing_value", bearing_value.getText().toString());
    }
}

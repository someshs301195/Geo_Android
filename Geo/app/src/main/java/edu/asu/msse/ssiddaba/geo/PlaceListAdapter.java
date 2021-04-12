package edu.asu.msse.ssiddaba.geo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.asu.msse.ssiddaba.geo.R;

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
 * build and evaluate the software package for the purpose of determining my
 * grade and program assessment.
 *
 * @author Somesh Siddabasappa ssiddaba@asu.edu
 * Software Engineering, CIDSE, MSSE, Arizona State University Polytechnic
 * @version March 24, 2021
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    private final LayoutInflater inflater;

    private ArrayList<String> placeNames;
    private PlaceDescription recentlyDeletedPlace;
    private int recentlyDeletedPlacePosition;
    private View snackView;
    private Context context;
    private PlaceLibrary placeLibrary;

    public PlaceListAdapter(Context context, ArrayList<String> placeNames) {
        this.placeNames = placeNames;
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        placeLibrary = PlaceLibrary.getInstance();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View placeItemView = inflater.inflate(R.layout.place_list_item, parent, false);
        this.snackView = parent;
        return new PlaceViewHolder(placeItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        String placeName = placeNames.get(position);
        holder.wordItemView.setText(placeName);
    }

    @Override
    public int getItemCount() {
        return placeNames.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView wordItemView;
        final PlaceListAdapter placeListAdapter;

        public PlaceViewHolder(@NonNull View itemView, PlaceListAdapter placeListAdapter) {
            super(itemView);
            this.wordItemView = itemView.findViewById(R.id.place);
            this.placeListAdapter = placeListAdapter;
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();

            // Create a new intent
            Intent intent = new Intent(context, PlaceDetailsActivity.class);
            int position = getLayoutPosition();
            String name = placeNames.get(position);
            intent.putExtra(MainActivity.PLACE_NAME, name);

            ((Activity) context).startActivityForResult(intent,position);
        }
    }

    public void deleteItem(int position) {
        String name = placeNames.get(position);
        recentlyDeletedPlace = placeLibrary.get(name);
        recentlyDeletedPlacePosition = position;

        placeLibrary.remove(name);
        placeNames = placeLibrary.getNames();
        notifyDataSetChanged();
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(snackView, R.string.snack_bar_text,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        placeLibrary.add(recentlyDeletedPlace);
        placeNames = placeLibrary.getNames();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setPlaceNames(ArrayList<String> placeNames) {
        this.placeNames = placeNames;
    }
}

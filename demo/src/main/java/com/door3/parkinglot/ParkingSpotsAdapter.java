package com.door3.parkinglot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ParkingSpotsAdapter extends ArrayAdapter<ParkingSpot> {

    public ParkingSpotsAdapter(Context context, ParkingSpot[] spots) {
        super(context, 0, spots);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.parking_spot, parent, false);
        ParkingSpot spot = getItem(position);

        if (ParkingSpot.Type.HANDICAPPED.equals(spot.getType())) {
            convertView.findViewById(R.id.handicapped).setVisibility(View.VISIBLE);
        }

        if (!spot.isEmpty()) {
            convertView.findViewById(R.id.car).setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}

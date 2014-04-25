package com.door3.parkinglot;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private ParkingSpot.Type selectedDriverType = ParkingSpot.Type.STANDART;
    private ParkingLot lot;
    private ArrayAdapter<ParkingSpot> adapter;
    private RadioGroup driverTypeSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lot = ParkingLot.newRandom(30, 20);

        GridView listView = (GridView) findViewById(android.R.id.list);
        adapter = new ParkingSpotsAdapter(this.getApplicationContext(), lot.getSpots());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(spotClickListener);
        showEmptyCount();

        driverTypeSelection = (RadioGroup) findViewById(R.id.driver_type_selection);
        driverTypeSelection.setOnCheckedChangeListener(driverTypeSelectionListener);
        selectDefaultDriver();

    }

    private void selectDefaultDriver() {
        RadioButton radioButton = (RadioButton) driverTypeSelection.findViewById(R.id.standart_driver);
        radioButton.setChecked(true);
    }

    RadioGroup.OnCheckedChangeListener driverTypeSelectionListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            selectedDriverType = checkedId == R.id.standart_driver ? ParkingSpot.Type.STANDART : ParkingSpot.Type.HANDICAPPED;
        }
    };

    AdapterView.OnItemClickListener spotClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ParkingSpot spot = lot.getSpots()[position];
            if (spot.isEmpty()) {
                park(position);
            } else {
                remove(position);
            }
            adapter.notifyDataSetChanged();
            showEmptyCount();
        }
    };

    private void showEmptyCount() {
        String title = "Free: " + lot.emptySpotsCount(ParkingSpot.Type.STANDART) + " (Std.)" +
                ",  " + lot.emptySpotsCount(ParkingSpot.Type.HANDICAPPED) + " (Hanycapped)";
        getSupportActionBar().setTitle(title);
    }

    private void park(int position) {
        if (lot.park(position, selectedDriverType)) {
            Toast.makeText(this, "Welcome to our parking lot!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sorry, this place is only for handicapped drivers. Choose another, please.", Toast.LENGTH_LONG).show();
        }
    }

    private void remove(int position) {
        if (lot.remove(position)) {
            Toast.makeText(this, "Bye! Have a good day!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Hmm, can't remove your car, have no idea why.", Toast.LENGTH_SHORT).show();
        }
    }


}

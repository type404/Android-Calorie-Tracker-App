package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;

import java.util.ArrayList;
import java.util.Iterator;


public class MapActivity extends AppCompatActivity {

    private MapboxMap mMapboxMap;
    private MapView mMapView;
    Icon icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.activity_map);
        IconFactory iconFactory = IconFactory.getInstance(MapActivity.this);
        icon = iconFactory.fromResource(R.drawable.blue_marker);
        SharedPreferences spUserData = this.getSharedPreferences("User_File", Context.MODE_PRIVATE);
        String address = spUserData.getString("user_address", "No address");
        String postcode = spUserData.getString("user_postcode", "No address");
        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        MapAsyncTask mapAsyncTask = new MapAsyncTask();
        mapAsyncTask.execute(address, postcode);
        ParkMapAsyncTask parkMapAsyncTask = new ParkMapAsyncTask();
        parkMapAsyncTask.execute(postcode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void addMarker(MapboxMap mapboxMap, LatLng userLatLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(userLatLng);
        mapboxMap.addMarker(markerOptions);
    }
    private void addMarkerPark(MapboxMap mapboxMap, LatLng userLatLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(userLatLng);
        markerOptions.icon(icon);
        mapboxMap.addMarker(markerOptions);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
/*searches the location as per address and puts marker*/
    private class MapAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return MapSearchAPI.searchMap(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] latLong;
            latLong = MapSearchAPI.getLatLong(result);
            LatLng userLatLng = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    mMapboxMap = mapboxMap;
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 11));
                    addMarker(mapboxMap, userLatLng);
                }
            });
        }
    }
    /*searches park as per postcode within 5Kms and puts marker*/
    private class ParkMapAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return MapSearchAPI.searchMapRadius(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            for (int i = 0; i < 7; i++) {
                String[] latLong;
                latLong = MapSearchAPI.getLatLongParks(result,i);
                LatLng userLatLng = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(MapboxMap mapboxMap) {
                        mMapboxMap = mapboxMap;
                        mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 11));
                        addMarkerPark(mapboxMap, userLatLng);
                    }
                });
            }
        }
    }
}
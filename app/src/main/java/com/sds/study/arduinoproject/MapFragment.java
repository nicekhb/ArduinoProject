package com.sds.study.arduinoproject;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    protected GoogleMap mMap;
    String TAG;
    static PolylineOptions polylineOptions;

    private static MapFragment mapFragment;
    public static MapFragment newInstance() {
        if (mapFragment == null) {
            mapFragment = new MapFragment();
        }
        return mapFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TAG = this.getClass().getName();
        View view = layoutInflater.inflate(R.layout.fragment_map, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //FragmentManager fragment = getActivity().getSupportFragmentManager();
        // Fragment fragment=(Fragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        final SupportMapFragment myMAPF = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Log.d(TAG, "mapASync start....");
        polylineOptions = new PolylineOptions();
        Log.d(TAG, "last position :   " + RunFragment.lastPosition);
        polylineOptions.add(RunFragment.lastPosition);
        polylineOptions.color(Color.BLUE);

        myMAPF.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "MapReady Callback Method called..///");
        mMap = googleMap;
        mMap.addPolyline(polylineOptions);
        mMap.setMinZoomPreference(9);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);
        GoogleMapOptions options = new GoogleMapOptions();
        options.tiltGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}

package com.sda.balys.robert.projektsda.fragments;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sda.balys.robert.projektsda.R;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends Fragment implements OnMapReadyCallback {
    @BindView(R.id.textView)
    TextView positionText;
    @BindView(R.id.textView1)
    TextView postionTextLong;
    @BindView(R.id.textView2)
    TextView positionTextAltitude;
    @BindView(R.id.textView3)
    TextView AdressText;

    @BindView(R.id.fragment_embedded_map_view_mapview)
    MapView mapView;

    private Context context;
    private LocationManager locationManager;
    private Location mCurrentLocation;
    private GoogleMap mGoogleMap;
    private GpsStatus gpsStatus;

//    double longitude = -74.044502;
//    double latitude = 40.689247;
    double longitude ;
    double latitude ;


    public LocalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();




        ActivityCompat.requestPermissions(this.getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                1);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //positionText.setText(String.valueOf(mCurrentLocation.getLatitude()));


        return view;
    }
//
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }


    }






    LocationListener locationlistener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mCurrentLocation=location;
            longitude = mCurrentLocation.getLongitude();
            latitude = mCurrentLocation.getLatitude();
            positionText.setText(String.valueOf(mCurrentLocation.getLatitude()));
            postionTextLong.setText(String.valueOf(mCurrentLocation.getLongitude()));
            positionTextAltitude.setText(String.valueOf(mCurrentLocation.getAltitude()));
            drawMarker(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @OnClick(R.id.button)
    void clinOnGetPosition() {
        String locationProvider = locationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationlistener);
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        positionText.setText(String.valueOf(location.getLatitude()));
        postionTextLong.setText(String.valueOf(location.getLongitude()));
        positionTextAltitude.setText(String.valueOf(location.getAltitude()));
        getCompleteAddressString(location.getLatitude(),location.getLongitude());
        drawMarker(location);


    }

    @OnClick(R.id.button3)
    void clickOnStopPosition() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.removeUpdates(locationlistener);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("Statua").snippet("Ihome"));
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(latitude,longitude)).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

//        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("Statua").snippet("Ihome"));
//        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(latitude,longitude)).zoom(16).bearing(0).tilt(45).build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }


    private void drawMarker(Location location){
        mGoogleMap.clear();

//  convert the location object to a LatLng object that can be used by the map API
          LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());


// zoom to the current location
          mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,16));

// add a marker to the map indicating our current position
         mGoogleMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude())
                .draggable(true));

    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                AdressText.setText(strAdd);

          Log.w("MyCurrentloctionaddress", "" + strReturnedAddress.toString());
            } else {
              Log.w("MyCurrentloctionaddress", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("MyCurrentloctionaddress", "Canont get Address!");
        }
        return strAdd;
    }
}

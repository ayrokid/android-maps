package com.silvanix.maps.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private Marker marker;
    private Circle mCircle;
    private List<Marker> markerList = new ArrayList<>();
    float[] distance = new float[2];

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        double radiusInMeters = 2000; //2 kilometer
        //blue outline
        int strokeColor = 0xff0000ff;
        //opaque blue fill
        int shadeColor = 0x550000FF;
        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(-34, 151);
        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(0f);
        mCircle = gmap.addCircle(circleOptions);

        marker = gmap.addMarker(new MarkerOptions().position(position).title("Marker in Sydney"));
        //markerList.add(marker);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(position));
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            gmap.setMyLocationEnabled(true);
            return;
        }

        gmap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LatLng sydney = new LatLng(latLng.latitude, latLng.longitude);
                marker = gmap.addMarker(new MarkerOptions().position(sydney).title("My Maker"));
                markerList.add(marker);
                gmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });

    }


    public void firstButtonClick(View v) {
        Toast.makeText(this, "Remove Radius 2 KM", Toast.LENGTH_LONG).show();
        //System.out.println(markerList.size());
        mCircle.setRadius(2000);
        for (Marker row : markerList){
            Location.distanceBetween( row.getPosition().latitude, row.getPosition().longitude,
                    mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);
            if( distance[0] > mCircle.getRadius()  ){
                row.remove();
            }
        }
    }

    public void secondButtonClick(View v) {
        Toast.makeText(this, "Remove Radius 3 KM", Toast.LENGTH_LONG).show();
        mCircle.setRadius(3000);
        for (Marker row : markerList){
            Location.distanceBetween( row.getPosition().latitude, row.getPosition().longitude,
                    mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);
            if( distance[0] > 3000  ){
                row.remove();
            }
        }
    }

    public void thirdButtonClick(View v) {
        Toast.makeText(this, "Remove Radius 4 KM", Toast.LENGTH_LONG).show();
        mCircle.setRadius(4000);
        for (Marker row : markerList){
            Location.distanceBetween( row.getPosition().latitude, row.getPosition().longitude,
                    mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);
            if( distance[0] > 4000  ){
                row.remove();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.silvanix.maps.maps/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.silvanix.maps.maps/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

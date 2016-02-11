package com.silvanix.maps.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private Marker maker;
    private Circle mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        gmap.addCircle(circleOptions);

        gmap.addMarker(new MarkerOptions().position(position).title("Marker in Sydney"));
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
                gmap.addMarker(new MarkerOptions().position(sydney).title("My Maker"));
                gmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });

    }

    public void firstButtonClick(View v) {
        Toast.makeText(this, "Clicked on Button 1", Toast.LENGTH_LONG).show();
    }

    public void secondButtonClick(View v) {
        Toast.makeText(this, "Clicked on Button 2", Toast.LENGTH_LONG).show();
    }

    public void thirdButtonClick(View v) {
        Toast.makeText(this, "Clicked on Button 3", Toast.LENGTH_LONG).show();
    }
}

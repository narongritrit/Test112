package com.example.student.test1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Criteria criteria;
    private double latADouble = 13.718593, lngADouble = 100.453191;
    private LatLng  userLatLng;
    private int[] mkInts = new int[]{R.mipmap.mk_user, R.mipmap.mk_friend};
    private String[] userStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        //setup
        //ขออนุญาตใช้ service of google
        setUp();

        createFrag();

    }// main method

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        //for Network
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);//เรียกตำแหน่งจาก network
        if(networkLocation != null){
            latADouble = networkLocation.getLatitude();
            lngADouble = networkLocation.getLongitude();
        }
        //for GPS
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);//เรียกตำแหน่งจาก GPS
        if(networkLocation != null){
            latADouble = gpsLocation.getLatitude();
            lngADouble = gpsLocation.getLongitude();
        }

        Log.d("SiamV2","Lat ."+latADouble);
        Log.d("SiamV2","Lng >"+lngADouble);

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    //ทำงานเสร็จจะโยนพิกัด
    public Location myFindLocation(String strProvider) {
        Location location = null;
//
        if (locationManager.isProviderEnabled(strProvider)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);


        }
        return location;
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        //มือถือขยับตำแหน่ง ทำงาน
        public void onLocationChanged(Location location) {
            latADouble = location.getLatitude();
            lngADouble = location.getLongitude();

        }

        @Override
        //มือถือปิดเปิดตำแหน่ง
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        //network หายทำอะไร
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private void setUp() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);//ไม่สนใจแกน Z
        criteria.setBearingRequired(false);//ไม่สนใจแกน Z
        userStrings = getIntent().getStringArrayExtra("Login");
    }

    private void createFrag() {
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
        mMap = googleMap;

        //setup center map
        userLatLng =new LatLng(latADouble,lngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,15));

        myCreateMarker(userStrings[1],userLatLng,mkInts[0]);


        // Add a marker in Sydney and move the camera
      /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }//onmapready

    private void myCreateMarker(String strName,LatLng latLng,int intImage){
        mMap.addMarker(new MarkerOptions().position(latLng).title(strName)
        .icon(BitmapDescriptorFactory.fromResource(intImage)));

    }
}//main class

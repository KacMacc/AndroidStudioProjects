package testing.gps_service;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class Mapy extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
private boolean run;

    private ArrayList<LatLng> points; //added
//private Cos cos;

    private Button btn_start, btn_stop;
    // private TextView textView;
    private BroadcastReceiver broadcastReceiver;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        points = new ArrayList<LatLng>();
        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_mapy);
            initMap();
        } else {
            // No Coś tam coś tam
        }

        btn_start = (Button) findViewById(R.id.button11);
       btn_stop = (Button) findViewById(R.id.button22);


        if(!runtime_permissions())
            enable_buttons();
    }

    private void enable_buttons() {

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    run = true;
    //drawLine()
    }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
run = false;
            }
        });
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.Mapfragment);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }



    LocationRequest mLocationRequest;

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT); //added
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }
    private static final float SMALLEST_DISPLACEMENT = 0.25F; //quarter of a meter
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
Marker  marker;
    Polyline line;

    @Override
    public void onLocationChanged(Location location) {

        if(marker != null){
            marker.remove();
        }
        if (location == null) {
            Toast.makeText(this, "Nie mogę określić aktualnej lokalizacji", Toast.LENGTH_LONG).show();
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 18);
            MarkerOptions options = new MarkerOptions()
                    .title("Jestem tutaj")
                    .snippet("Szerokość: " + location.getLatitude() + "; Długość: " + location.getLongitude()+";")
                    .position(ll);
              marker = mGoogleMap.addMarker(options);
            mGoogleMap.animateCamera(update);
            points.add(ll);
            drawLine();
        }
        }
    private void drawLine(){

        if (run) {
            PolylineOptions options = new PolylineOptions().width(3).color(Color.BLUE).geodesic(true);
            for (int i = 0; i < points.size(); i++) {
                LatLng point = points.get(i);
                options.add(point);
            }
            line = mGoogleMap.addPolyline(options); //add Polyline

            //Toast.makeText(this, "Rysowanie trasy", Toast.LENGTH_LONG).show();
            //PolylineOptions options = new PolylineOptions()
             //       .add(marker.getPosition())
                //    .color(Color.BLUE)
               //     .width(3);
            //line = mGoogleMap.addPolyline(options);
        }//else{
            //Toast.makeText(this, "Zakończenie", Toast.LENGTH_LONG).show();
            //marker.remove();
        //}

    }
         }




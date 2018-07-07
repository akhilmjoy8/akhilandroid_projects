package com.example.acer.currentlocation;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.currentlocation.Location.LocationHeadActivity;
import com.example.acer.currentlocation.Location.TonyIntro;
import com.example.acer.currentlocation.Login.SplashActivity;
import com.example.acer.currentlocation.Models.PlaceInfo;
import com.example.acer.currentlocation.Navigation.MapsNavigationActivity;
import com.example.acer.currentlocation.Profile.ProfileActivity;

import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.example.acer.currentlocation.contacts.ContactViewActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {

    FirebaseDatabase database;
    DatabaseReference myRef,locref,locref1;
    PlaceInfo mPlacee = new PlaceInfo();
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            boolean isSuccess = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this,R.raw.nsample_style_map)
            );
            if(!isSuccess){
                Log.e("Error","Map style load failed.!!");
            }
        }
        catch (Resources.NotFoundException ex){
            ex.printStackTrace();
        }
        //firebase connection



        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            database = FirebaseDatabase.getInstance();
            myRef =database.getReference(uid).child("Profile");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    country = dataSnapshot.child("country").getValue(String.class);
                    email = dataSnapshot.child("email").getValue(String.class);
                    name = dataSnapshot.child("name").getValue(String.class);
                    phone = dataSnapshot.child("phone").getValue(String.class);
                    profilepic = dataSnapshot.child("profilepic").getValue(String.class);
                    state = dataSnapshot.child("state").getValue(String.class);

                    mPlacee.setName(name);
                    mPlacee.setPhoneNumber(phone);
                    mPlacee.setCountry(country);
                    mPlacee.setEmail(email);
                    mPlacee.setProfilepic(profilepic);
                    mPlacee.setState(state);

                    getDeviceLocation();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                int position = (int)(marker.getTag());
                //Using position get Value from arraylist
                try {
                    openBottomSheet();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }


    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    //widgets
    private ImageView mGps, search, dashboard, contacts, addLocation;
    private Toolbar toolbar;

    //firebase vars
    String country,email,name,phone,state,profilepic;
    SessionManager session;
    String uid;
    Context context;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker;
    private double lat;
    private double lng;
    String lt,lg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = new Intent(MapsActivity.this, MyService.class);
        startService(intent);



        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //session
        session = new SessionManager(getApplicationContext());
        uid=session.getValue(MapsActivity.this,"uid");
       // uid = "2ILOmMdogYg5Tn0RLSmLmXI4Dnv1";
        //firebase

        database = FirebaseDatabase.getInstance();
        final DatabaseReference onlineRef = database.getReference().child(".info/connected");
        final DatabaseReference currentUserRef = database.getReference().child(uid).child("Profile").child("status");
        onlineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Log.d(TAG, "DataSnapshot:" + dataSnapshot);
                if (dataSnapshot.getValue(Boolean.class)){

                    currentUserRef.setValue("online");
                    currentUserRef.onDisconnect().setValue("offline");
                    currentUserRef.goOffline();
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                //   Log.d(TAG, "DatabaseError:" + databaseError);
            }
        });

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contacts = findViewById(R.id.imgContacts);
        dashboard = findViewById(R.id.imgDashBoard);
        search = findViewById(R.id.imgSearch);
        addLocation = findViewById(R.id.imgAddLocation);
        mGps = (ImageView) findViewById(R.id.ic_gps);

        getLocationPermission();



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MapsNavigationActivity.class);
                intent.putExtra("lat","0.0");
                intent.putExtra("lng","0.0");
                intent.putExtra("name"," ");
                intent.putExtra("phone"," ");

                startActivity(intent);

            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ContactViewActivity.class);
                startActivity(intent);

            }
        });

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef2 =database.getReference(uid).child("Locations");
                myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren())
                        {
                            Intent intent = new Intent(MapsActivity.this, LocationHeadActivity.class);
                            intent.putExtra("lat",lt);
                            intent.putExtra("lng",lg);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(MapsActivity.this, TonyIntro.class);
                            intent.putExtra("lat",lt);
                            intent.putExtra("lng",lg);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }


    private void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");

                getDeviceLocation();
            }
        });

    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");

                            try {
                                Location currentLocation = (Location) task.getResult();
                                String gt = mPlacee.getName();


                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        18,
                                        "Hi," + gt);;

                                lat = currentLocation.getLatitude();
                                lng = currentLocation.getLongitude();
                                lt = Double.toString(lat);
                                lg = Double.toString(lng);
                                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);

                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(lat, lng))
                                        .icon(bitmapDescriptor)
                                        .title("Hii,"+gt)).showInfoWindow();
                            }
                            catch (Exception e){

                            }

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
       // mMarker.showInfoWindow();
    }


    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(options);

        }


    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }




    /*
        --------------------------- google places API autocomplete suggestions -----------------
     */


    //dialog box with success message and redirect to next activity
    public void openBottomSheet() throws IOException {

        View view = getLayoutInflater().inflate(R.layout.user_info_dialog, null);
        final ImageView imguser=(ImageView) view.findViewById(R.id.imgUser);
        final TextView tvloc=(TextView) view.findViewById(R.id.tvloc1);
        final FloatingActionButton addloc= view.findViewById(R.id.addloc);
        addloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef2 =database.getReference(uid).child("Locations");
                myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren())
                        {
                            Intent intent = new Intent(MapsActivity.this, LocationHeadActivity.class);
                            intent.putExtra("lat",lt);
                            intent.putExtra("lng",lg);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(MapsActivity.this, TonyIntro.class);
                            intent.putExtra("lat",lt);
                            intent.putExtra("lng",lg);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        Picasso.get().load(mPlacee.getProfilepic()).fit().centerCrop()
                .placeholder(R.drawable.ic_user_demo)
                .error(R.drawable.ic_user_demo)
                .into(imguser);
        //get palce address
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        tvloc.setText(addresses.get(0).getAddressLine(0));


        final Dialog mBottomSheetDialog = new Dialog(MapsActivity.this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

    }


}


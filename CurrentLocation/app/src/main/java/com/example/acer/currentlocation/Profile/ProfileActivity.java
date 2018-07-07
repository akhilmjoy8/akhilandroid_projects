package com.example.acer.currentlocation.Profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.acer.currentlocation.GPSTracker;
import com.example.acer.currentlocation.MapsActivity;
import com.example.acer.currentlocation.MyLocation;
import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.example.acer.currentlocation.contacts.ContactModel;
import com.example.acer.currentlocation.contacts.CustomAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference storageReference;
    String path;
    SessionManager session;
    String uid, etEmail1, etName1, etCountry1, etState1,phno,path2;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseDatabase database;
    DatabaseReference myRef,myRef1,locref;
    ImageView imgProfile, imgBack;
    TextView tvUserName,tvUserEmail1,tvUserMobile1,tvUserCountry1,tvUserState1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        database = FirebaseDatabase.getInstance();

try {
    ProfileFragment fragment = ProfileFragment.newInstance();
    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit();
}
catch (Exception e)
{

}

        imgProfile = findViewById(R.id.imgProfile);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail1 = findViewById(R.id.tvUserEmail1);
        tvUserMobile1 = findViewById(R.id.tvUserMobile11);
        tvUserCountry1 = findViewById(R.id.tvUserCountry1);
        tvUserState1 = findViewById(R.id.tvUserState1);
        imgBack = findViewById(R.id.imgBack);


        //get uid from session
        session = new SessionManager(getApplicationContext());
        uid = session.getValue(ProfileActivity.this, "uid");
//        uid = "2ILOmMdogYg5Tn0RLSmLmXI4Dnv1";
        phno = session.getValue(ProfileActivity.this, "phno");

        //firebase database ref
        database = FirebaseDatabase.getInstance();
        myRef =database.getReference(uid).child("Profile");

        //fetch values from firebase
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                etEmail1 = dataSnapshot.child("email").getValue(String.class);
                etName1 =  dataSnapshot.child("name").getValue(String.class);
                etCountry1 = dataSnapshot.child("country").getValue(String.class);
                etState1 = dataSnapshot.child("state").getValue(String.class);
                phno = dataSnapshot.child("phone").getValue(String.class);
                path2 = dataSnapshot.child("profilepic").getValue(String.class);
                tvUserName.setText(etName1);
                tvUserEmail1.setText(etEmail1);
                tvUserMobile1.setText(phno);
                tvUserCountry1.setText(etCountry1);
                tvUserState1.setText(etState1);
                Picasso.get().load(path2).fit().centerCrop()
                        .placeholder(R.drawable.ic_user_demo)
                        .error(R.drawable.ic_user_demo)
                        .into(imgProfile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        //check live location
//        final Handler handler = new Handler();
//        final int delay = 1000; //milliseconds
//
//        handler.postDelayed(new Runnable(){
//            public void run(){
//
//                displayData();
//                //do something
//                handler.postDelayed(this, delay);
//            }
//        }, delay);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

//    public void displayData() {
//        database = FirebaseDatabase.getInstance();
//        locref =database.getReference(uid).child("MyLocation");
//
//        GPSTracker gpsTracker = new GPSTracker(this);
//       // Toast.makeText(getApplicationContext(), "lat" + gpsTracker.getLocation().getLatitude()+"lng"+gpsTracker.getLocation().getLongitude(), Toast.LENGTH_SHORT).show();
//        MyLocation loc = new MyLocation(gpsTracker.getLocation().getLatitude(),gpsTracker.getLocation().getLongitude());
//        locref.setValue(loc);
//    }

}

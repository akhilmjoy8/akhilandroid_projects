package com.example.acer.currentlocation.contacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.acer.currentlocation.Navigation.MapsNavigationActivity;
import com.example.acer.currentlocation.Profile.ProfileAdapter;
import com.example.acer.currentlocation.Profile.locationView;
import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactLocationActivity extends AppCompatActivity {

    ListView  lv;
    Button cl;
    ArrayList<locationView> userArray = new ArrayList<locationView>();
    private LinearLayout mBottomSheet;
    private ImageView mLeftArrow;
    SessionManager session;
    ListView lst;
    FirebaseDatabase database;
    DatabaseReference myRef,myRef1;
    String  uname,upic,uid,phone,name;
    static String currentUid;
    String key;
    String ulat, ulng;

    static String LoggedIn_User_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_location);
        setTitle("Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get uid from session
        session = new SessionManager(getApplicationContext());
        currentUid = session.getValue(ContactLocationActivity.this, "uid");        // OneSignal Initialization

       //call service provider to send notifications
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.sendTag("User_ID",currentUid);

        //get intent values
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        uid = intent.getStringExtra("uid");
        cl = findViewById(R.id.btnCurrentLocation);

        if(uid.equals(currentUid))
        {
            cl.setVisibility(View.GONE);
            cl.setBackgroundColor(Color.TRANSPARENT);
        }
        database = FirebaseDatabase.getInstance();
        myRef1 =database.getReference().child(uid).child("MyLocation");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double lat = dataSnapshot.child("lat").getValue(Double.class);
                double lng = dataSnapshot.child("lng").getValue(Double.class);
                ulat = String.valueOf(lat);
                ulng = String.valueOf(lng);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNotification();
                Intent intent = new Intent(ContactLocationActivity.this, MapsNavigationActivity.class);
                intent.putExtra("lat",ulat);
                intent.putExtra("lng",ulng);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                startActivity(intent);

            }
        });


        lv = findViewById(R.id.lv_contactlocation);
        database = FirebaseDatabase.getInstance();
        myRef =database.getReference().child(uid);
        getData();
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
    public void getData()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                lv.setAdapter(null);
                userArray.clear();


                    myRef1 =database.getReference();
                    myRef1.child(uid).child("Locations").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            for (DataSnapshot ds1: dataSnapshot1.getChildren()) {
                                uname = dataSnapshot.child("Profile").child("name").getValue(String.class);
                                upic = dataSnapshot.child("Profile").child("profilepic").getValue(String.class);
                                String phone =dataSnapshot.child("Profile").child("phone").getValue(String.class);
                                String name = ds1.child("name").getValue(String.class);
                                String desc = ds1.child("description").getValue(String.class);
                                String img = ds1.child("photo").getValue(String.class);
                                String rate = ds1.child("rate").getValue().toString();
                                String lat = ds1.child("lat").getValue().toString();
                                String lng = ds1.child("lng").getValue().toString();

                                locationView user = new locationView(uname,upic,name,desc,img,rate,lat,lng,phone);
                                userArray.add(user);
                                ProfileAdapter adapter = new ProfileAdapter(ContactLocationActivity.this,R.layout.lst_view_feed, userArray);
                                lv.setAdapter(adapter);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendNotification()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
//                    if (ContactLocationActivity.currentUid.equals(currentUid)) {
//                        send_email = uid;
//                    } else {
                        send_email = uid;
//                    }

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NzAwNTM2ZjYtYzU1MC00NGY1LWI5ZmYtZjM4ODE5OWNhNTQz");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"c5646dab-1a7d-44ab-8040-e9ceb1794896\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"NSample requesting to access your current location\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }
}

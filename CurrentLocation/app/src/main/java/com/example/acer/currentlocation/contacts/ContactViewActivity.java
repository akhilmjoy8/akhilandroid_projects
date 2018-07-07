package com.example.acer.currentlocation.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.currentlocation.GPSTracker;
import com.example.acer.currentlocation.MyLocation;
import com.example.acer.currentlocation.Profile.ProfileEditActivity;
import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
public class ContactViewActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter customAdapter;
    ArrayList<ContactModel> userArray = new ArrayList<ContactModel>();
    Activity context = this;
    List<String> aList = new ArrayList<>();
    List<String> bList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    SessionManager session;
    String uid,phno,phcode;

    DatabaseReference locref,locref1;
    public static final int REQUEST_READ_CONTACTS = 79;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);
        setTitle("Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listViewContact);

        //get uid from session
        session = new SessionManager(getApplicationContext());
        uid = session.getValue(ContactViewActivity.this, "uid");
        phno = session.getValue(ContactViewActivity.this, "phno");
        phcode = session.getValue(ContactViewActivity.this, "phnocode");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            getContacts();

        } else {
            requestLocationPermission();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }



    protected void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!

        } else {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getContacts();

                } else {

                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public void getContacts() {


        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String ct = phoneNumber.replace(" ","");

            int len = ct.length();
            phoneNumber=ct;
            if(len >10)
            {
                phoneNumber = ct.substring(3);

            }
            bList.add(phoneNumber);
            Log.d("name>>",name+"  "+ct);
        }
        phones.close();

        database = FirebaseDatabase.getInstance();
        myRef =database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userArray.clear();
                listView.setAdapter(null);
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    String uid1= ds.getKey();
                    String phone=ds.child("Profile").child("phone").getValue(String.class);
                    String  name =ds.child("Profile").child("name").getValue(String.class);
                    String  pic =ds.child("Profile").child("profilepic").getValue(String.class);
                    String  status =ds.child("Profile").child("status").getValue(String.class);
                    int len = phone.length();
                    if(len >10)
                    {
                        phone = phone.substring(3);

                    }
                    if (bList.contains(phone)) {
                        ContactModel contactModel = new ContactModel();
                        contactModel.setName(name);
                        contactModel.setNumber(phone);
                        contactModel.setPic(pic);
                        contactModel.setStatus(status);
                        contactModel.setUid(uid1);
                        userArray.add(contactModel);
                    }
                }
                CustomAdapter adapter = new CustomAdapter(context,R.layout.lv_contact_view, userArray);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


}
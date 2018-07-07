package com.example.acer.nsampledemo.contacts;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.nsampledemo.Login.OtpVerificationActivity;
import com.example.acer.nsampledemo.Profile.ProfileEditActivity;
import com.example.acer.nsampledemo.R;
import com.example.acer.nsampledemo.ServerConnection.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listViewContact);

        //get uid from session
        session = new SessionManager(getApplicationContext());
        uid = session.getValue(ContactViewActivity.this, "uid");
        phno = session.getValue(ContactViewActivity.this, "phno");
        phcode = session.getValue(ContactViewActivity.this, "phnocode");
        //fetch value from firebase

        //fetch data from contacts
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


//        //search
//        try {
//
//            MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
//            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    //Do some magic
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    //Do some magic
//                    return false;
//                }
//            });
//
//            searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//                @Override
//                public void onSearchViewShown() {
//                    //Do some magic
//                }
//
//                @Override
//                public void onSearchViewClosed() {
//                    //Do some magic
//                }
//            });
//        }
//        catch (Exception e)
//        {
//
//        }
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.action_search, menu);
//        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//
//        return true;
//    }


}
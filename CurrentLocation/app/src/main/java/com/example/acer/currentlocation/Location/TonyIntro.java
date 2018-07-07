package com.example.acer.currentlocation.Location;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.currentlocation.Profile.ProfileActivity;
import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TonyIntro extends AppCompatActivity {
    SessionManager session;
    String uid, etName1,lat,lng;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tony_intro);
        getSupportActionBar().hide();


        Intent intent=getIntent();
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");
        final TextView tvTonyCall = findViewById(R.id.tvTonyCall);

        session = new SessionManager(getApplicationContext());
        uid = session.getValue(TonyIntro.this, "uid");

        //firebase database ref
        database = FirebaseDatabase.getInstance();
        myRef =database.getReference(uid).child("Profile");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                etName1 =  dataSnapshot.child("name").getValue(String.class);
                tvTonyCall.setText("Hi, "+etName1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ImageView im = findViewById(R.id.imgnxtTony);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TonyIntro.this, LocationHeadActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);

            }
        });
    }
}

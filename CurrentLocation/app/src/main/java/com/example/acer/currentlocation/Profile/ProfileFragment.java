package com.example.acer.currentlocation.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.example.acer.currentlocation.contacts.CustomAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileAdapter adapter;
    ArrayList<locationView> userArray = new ArrayList<locationView>();
    private CoordinatorLayout mBottomSheet;
    private ImageView mLeftArrow;
    SessionManager session;
    String uid;
    ListView lst;
    FirebaseDatabase database;
    DatabaseReference myRef,myRef1;
    String  uname,upic;
    String key;

    BottomSheetBehavior behavior;

//
//    private ImageView mRightArrow;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {



        //get uid from session
        session = new SessionManager(container.getContext());
        uid = session.getValue(container.getContext(), "uid");
       // uid = "2ILOmMdogYg5Tn0RLSmLmXI4Dnv1";

        database = FirebaseDatabase.getInstance();
        myRef =database.getReference();


        View view = inflater.inflate(R.layout.activity_profile__fragment, container, false);

        // find container view
        mBottomSheet = view.findViewById(R.id.bottom_sheet);

        // find arrows
        mLeftArrow = view.findViewById(R.id.bottom_sheet_left_arrow);

        mLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
     ///   mRightArrow = view.findViewById(R.id.bottom_sheet_right_arrow);
        initializeBottomSheet();
        lst = view.findViewById(R.id.lstFeeds);
        lst.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

       getData();
        return view;
    }

    private void initializeBottomSheet() {

        // init the bottom sheet behavior
//        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
//
//        // change the state of the bottom sheet
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//        // change the state of the bottom sheet
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//        // set callback for changes
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                if (isAdded()) {
//                    transitionBottomSheetBackgroundColor(slideOffset);
//                    animateBottomSheetArrows(slideOffset);
//                }
//            }
//        });

        behavior = BottomSheetBehavior.from(mBottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                transitionBottomSheetBackgroundColor(slideOffset);
                    animateBottomSheetArrows(slideOffset);
            }
        });
    }

    private void transitionBottomSheetBackgroundColor(float slideOffset) {
        int colorFrom = getResources().getColor(R.color.white);
        int colorTo = getResources().getColor(R.color.white);
        mBottomSheet.setBackgroundColor(interpolateColor(slideOffset,
                colorFrom, colorTo));
    }

    private void animateBottomSheetArrows(float slideOffset) {
        mLeftArrow.setRotation(slideOffset * -180);
//        mRightArrow.setRotation(slideOffset * 180);
    }

    // Helper method to interpolate colors
    private int interpolateColor(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;
        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;
        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }
    public void getData()
    {
//        locationView user = new locationView("ff","jj","hh","hh","ug","2");
//        userArray.add(user);
//        ProfileAdapter adapter = new ProfileAdapter(getContext(),R.layout.lst_view_feed, userArray);
//        lst.setAdapter(adapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lst.setAdapter(null);
                userArray.clear();
                for (final DataSnapshot ds: dataSnapshot.getChildren()) {

                    myRef1 =database.getReference();
                    myRef1.child(ds.getKey()).child("Locations").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            for (DataSnapshot ds1: dataSnapshot1.getChildren()) {
                                uname = ds.child("Profile").child("name").getValue(String.class);
                                upic = ds.child("Profile").child("profilepic").getValue(String.class);
                                String phone =ds.child("Profile").child("phone").getValue(String.class);
                                String name = ds1.child("name").getValue(String.class);
                                String desc = ds1.child("description").getValue(String.class);
                                String img = ds1.child("photo").getValue(String.class);
                                String rate = ds1.child("rate").getValue().toString();
                                String lat = ds1.child("lat").getValue().toString();
                                String lng = ds1.child("lng").getValue().toString();

                                locationView user = new locationView(uname,upic,name,desc,img,rate,lat,lng,phone);
                                userArray.add(user);
                                ProfileAdapter adapter = new ProfileAdapter(getContext(),R.layout.lst_view_feed, userArray);
                                lst.setAdapter(adapter);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
package com.example.acer.currentlocation.Location;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.acer.currentlocation.MapsActivity;
import com.example.acer.currentlocation.Profile.ProfileEditActivity;
import com.example.acer.currentlocation.Profile.addProfile;
import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class LocationEntryActivity extends AppCompatActivity {

    EditText etName, etDescription;
    private ImageView imgNext, imgPrevious, imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, imgTony;
    ProgressBar progressBar;
    private TextView tvTitle;
    private LinearLayout li;
    Bitmap bitmap;
    private ViewFlipper viewFlipper;
    String cat;
    int StringIndex = 0, imageIndex = 0;
    String[] Row = {
            "Can you tell me a caption for this place please.?",
            "So, I would to know more about this place",
            "If Icould know how much would you rate this place"
    };
    int[] imagearray = new int[]{
            R.drawable.tony_caption,
            R.drawable.tony_description,
            R.drawable.tony_rating
    };
    Animation animFadeIn, animFadeOut;
    FirebaseStorage storage;
    StorageReference storageReference;
    String path;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SessionManager session;
    String uid,path2,etCategory1,etName1,etDescription1,tvTitle1;
    Uri uri,duri;
    int img1_f=0;
    int rat=0;
    String lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_entry);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();

        Intent intent=getIntent();
       // bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        uri = intent.getParcelableExtra("imageUri");
        cat=intent.getStringExtra("category");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");

        //set profpicture default
        bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_user_demo)).getBitmap();

        //get uid from session
        session = new SessionManager(getApplicationContext());
        uid = session.getValue(LocationEntryActivity.this, "uid");
       // uid="yiitmonyPCeGZjn76fmXpMXkeiA3";
        //firebase storage ref
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        database = FirebaseDatabase.getInstance();
        myRef =database.getReference(uid).child("Locations");

        viewFlipper = findViewById(R.id.view_flipper);
        imgPrevious = findViewById(R.id.imgPrevious);
        imgNext = findViewById(R.id.imgNext);
        li = findViewById(R.id.layoutProfile);
        etName = findViewById(R.id.EtName);
        etDescription = findViewById(R.id.EtDescription);
        tvTitle = findViewById(R.id.tvTitle);
        imgStar1 = findViewById(R.id.imgStar1);
        imgStar2 = findViewById(R.id.imgStar2);
        imgStar3 = findViewById(R.id.imgStar3);
        imgStar4 = findViewById(R.id.imgStar4);
        imgStar5 = findViewById(R.id.imgStar5);
        imgTony = findViewById(R.id.imgTony);
        final TextView tv = findViewById(R.id.txtintro);
        ImageView img = findViewById(R.id.imgTonyBack);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationEntryActivity.this, LocationHeadActivity.class);
                startActivity(intent);
            }
        });

        imgStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rat = 1;

                imgStar1.setImageResource(R.drawable.ic_star_yellow);
                imgStar2.setImageResource(R.drawable.ic_white_star);
                imgStar3.setImageResource(R.drawable.ic_white_star);
                imgStar4.setImageResource(R.drawable.ic_white_star);
                imgStar5.setImageResource(R.drawable.ic_white_star);

            }

        });
        imgStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rat = 2;

                imgStar1.setImageResource(R.drawable.ic_star_yellow);
                imgStar2.setImageResource(R.drawable.ic_star_yellow);
                imgStar3.setImageResource(R.drawable.ic_white_star);
                imgStar4.setImageResource(R.drawable.ic_white_star);
                imgStar5.setImageResource(R.drawable.ic_white_star);

            }

        });
        imgStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rat = 3;

                imgStar1.setImageResource(R.drawable.ic_star_yellow);
                imgStar2.setImageResource(R.drawable.ic_star_yellow);
                imgStar3.setImageResource(R.drawable.ic_star_yellow);
                imgStar4.setImageResource(R.drawable.ic_white_star);
                imgStar5.setImageResource(R.drawable.ic_white_star);

            }

        });
        imgStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rat = 4;

                imgStar1.setImageResource(R.drawable.ic_star_yellow);
                imgStar2.setImageResource(R.drawable.ic_star_yellow);
                imgStar3.setImageResource(R.drawable.ic_star_yellow);
                imgStar4.setImageResource(R.drawable.ic_star_yellow);
                imgStar5.setImageResource(R.drawable.ic_white_star);

            }

        });
        imgStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rat = 5;

                imgStar1.setImageResource(R.drawable.ic_star_yellow);
                imgStar2.setImageResource(R.drawable.ic_star_yellow);
                imgStar3.setImageResource(R.drawable.ic_star_yellow);
                imgStar4.setImageResource(R.drawable.ic_star_yellow);
                imgStar5.setImageResource(R.drawable.ic_star_yellow);

            }

        });
        progressBar = findViewById(R.id.progressBar);

        li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });

        tvTitle.setText(Row[0]);
        if (tvTitle.getText().toString().equals("Can you tell me a caption for this place please.?")) {
            imgPrevious.setVisibility(View.INVISIBLE);
        }

        imgTony.setImageResource(imagearray[0]);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringIndex == Row.length - 1) {

                        uploadImage();
                        hideKeyboard(v);
                    progressBar.setVisibility(View.VISIBLE);

                } else {
                    //name validations
                    if (tvTitle.getText().equals("Can you tell me a caption for this place please.?")) {
                        imgPrevious.setVisibility(View.INVISIBLE);
                        tv.setVisibility(View.VISIBLE);

                        if (etName.getText().toString().isEmpty()) {
                            etName.setError("Caption of the place is required");
                            etName.requestFocus();
                            return;
                        }
                       else {
                            nextView(v);
                            imgPrevious.setVisibility(View.VISIBLE);
                            tv.setVisibility(View.INVISIBLE);

                            tvTitle.setVisibility(View.VISIBLE);
                            tvTitle.startAnimation(animFadeOut);
                            imgTony.setImageResource(imagearray[++imageIndex]);
                            tvTitle.setText(Row[++StringIndex]);
                            tvTitle.startAnimation(animFadeIn);
                        }
                    }
                    //description validations
                    else if (tvTitle.getText().toString().equals("So, I would to know more about this place")) {
                        if (etDescription.getText().toString().isEmpty()) {
                            etDescription.setError("description is required");
                            etDescription.requestFocus();
                            return;
                        }
                        if (etDescription.getText().toString().length() < 3) {
                            etDescription.setError("Please enter a valid description");
                            etDescription.requestFocus();
                            return;
                        } else {
                            nextView(v);
                            tvTitle.setVisibility(View.VISIBLE);
                            tvTitle.startAnimation(animFadeOut);
                            tvTitle.setText(Row[++StringIndex]);
                            imgTony.setImageResource(imagearray[++imageIndex]);
                            tvTitle.startAnimation(animFadeIn);
                        }
                    }
                   else {
                        nextView(v);
                        tvTitle.setVisibility(View.VISIBLE);
                        tvTitle.startAnimation(animFadeOut);


                         //final String i = String.valueOf(imgStar1.getTag());

                        tvTitle.setText(Row[++StringIndex]);
                        imgTony.setImageResource(imagearray[++imageIndex]);
                        if (tvTitle.getText().toString().equals("Can you tell me a caption for this place please.?")) {
                            imgPrevious.setVisibility(View.INVISIBLE);
                            tv.setVisibility(View.VISIBLE);

                        }
                        if (!tvTitle.getText().toString().equals("Can you tell me a caption for this place please.?")) {
                            imgPrevious.setVisibility(View.VISIBLE);
                            tv.setVisibility(View.INVISIBLE);

                        }

                        tvTitle.startAnimation(animFadeIn);
                    }
                }
            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousView(v);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.startAnimation(animFadeOut);

                if (StringIndex == Row.length + 1) {

                    StringIndex = 0;
                    imageIndex = 0;

                    tvTitle.setText(Row[StringIndex]);
                    if (tvTitle.getText().toString().equals("Can you tell me a caption for this place please.?")) {
                        imgPrevious.setVisibility(View.INVISIBLE);
                        tv.setVisibility(View.VISIBLE);

                    }
                    if (!tvTitle.getText().toString().equals("Can you tell me a caption for this place please.?")) {
                        imgPrevious.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.INVISIBLE);
                    }
                    tvTitle.startAnimation(animFadeIn);
                } else {

                    tvTitle.setText(Row[--StringIndex]);
                    imgTony.setImageResource(imagearray[--imageIndex]);

                    if (tvTitle.getText().toString().equals("Can you tell me a caption for this place please.?")) {
                        imgPrevious.setVisibility(View.INVISIBLE);
                        tv.setVisibility(View.VISIBLE);

                    }
                    if (!tvTitle.getText().toString().equals("Can you tell me a caption for this place please.?")) {
                        imgPrevious.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.INVISIBLE);

                    }
                    tvTitle.startAnimation(animFadeIn);
                }
            }
        });

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


    public void previousView(View v) {
        viewFlipper.setInAnimation(this, R.anim.slide_in_right_tv);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left_tv);
        viewFlipper.showPrevious();
    }

    public void nextView(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showNext();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void uploadImage() {
        if (uri != null) {
//                            final ProgressDialog progressDialog = new ProgressDialog(this);
//                            progressDialog.setTitle("Uploading...");
//                            progressDialog.show();
            path = "images/" + UUID.randomUUID().toString();

            final StorageReference ref = storageReference.child(path);

            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            duri = taskSnapshot.getDownloadUrl();
                            path2 = duri.toString();

                            etCategory1 = cat;
                            etName1 = etName.getText().toString();
                            etDescription1 = etDescription.getText().toString();
                            tvTitle1 = tvTitle.getText().toString();
                            String key = myRef.push().getKey();
                            addLocation loc = new addLocation(etName1, etCategory1, etDescription1,rat, path2,lat,lng);
                            myRef.child(key).setValue(loc);

                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LocationEntryActivity.this, MapsActivity.class));
                            LocationEntryActivity.this.finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // progressDialog.dismiss();
                            // Toast.makeText(ProfileEditActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }

                    });
        }
    }
}

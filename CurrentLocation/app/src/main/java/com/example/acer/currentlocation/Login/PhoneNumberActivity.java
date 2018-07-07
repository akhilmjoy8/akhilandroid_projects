package com.example.acer.currentlocation.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.acer.currentlocation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class PhoneNumberActivity extends AppCompatActivity {

    String contryId = null;
    String contryDialCode = null;

    EditText contrycode, mobileNo;
    LinearLayout ll;

    private static final String TAG = "PhoneAuth";
    private FirebaseAuth fbAuth;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        getSupportActionBar().hide();

        //firebase connection
        fbAuth = FirebaseAuth.getInstance();

        contrycode = findViewById(R.id.etContryCode);
        ll = findViewById(R.id.mainLayout);
        mobileNo = findViewById(R.id.etMobileNo);
        mobileNo.setFocusableInTouchMode(false);
        mobileNo.setFocusable(false);
        contrycode.setFocusableInTouchMode(false);
        contrycode.setFocusable(false);



        //get country code
        TelephonyManager telephonyMngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        contryId = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrContryCode=this.getResources().getStringArray(R.array.DialingCountryCode);
        for(int i=0; i<arrContryCode.length; i++){
            String[] arrDial = arrContryCode[i].split(",");
            if(arrDial[1].trim().equals(contryId.trim())){
                contryDialCode = arrDial[0];
                break;
            }
        }
        contrycode.setText("+"+contryDialCode);

        contrycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contrycode.setFocusableInTouchMode(true);
                contrycode.setFocusable(true);
            }
        });

        mobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNo.setFocusableInTouchMode(true);
                mobileNo.setFocusable(true);
            }
        });
        mobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mobileNo.length() == 10){
                    // Check if no view has focus:

                    new CountDownTimer(500, 100) { // adjust the milli seconds here

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            String mob = mobileNo.getText().toString();
                            String mobcode = contrycode.getText().toString();
                            Intent intent = new Intent(PhoneNumberActivity.this, OtpVerificationActivity.class);
                            intent.putExtra("mobcode",mobcode);
                            intent.putExtra("phNumber",mob);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            new CountDownTimer(500, 200) { // adjust the milli seconds here

                                public void onTick(long millisUntilFinished) {

                                }

                                public void onFinish() {
                                    mobileNo.setText("");
                                }
                            }.start();

                        }
                    }.start();

                }
            }

        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });

        //transition animation
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
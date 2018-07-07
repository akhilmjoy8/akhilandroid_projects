package com.example.acer.currentlocation.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.currentlocation.MapsActivity;
import com.example.acer.currentlocation.Profile.ProfileEditActivity;
import com.example.acer.currentlocation.R;
import com.example.acer.currentlocation.ServerConnection.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {
    private static final String TAG = "PhoneAuth";

    private String phoneText;
    private String phoneCode;
    private String codeText;
    private Button resendButton;
    ImageView backbt, imgIcon;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    LinearLayout li;

    private FirebaseAuth fbAuth;
    EditText num1,num2,num3,num4,num5,num6;
    TextView text1, error;
    private static final String FORMAT = "%02d:%02d";
    int seconds, minutes;
    Boolean f=false;
    CountDownTimer countdowntimer;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        getSupportActionBar().hide();


        session = new SessionManager(getApplicationContext());

        //transition animation
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
        fbAuth = FirebaseAuth.getInstance();


        text1 = findViewById(R.id.etTimer);
        error = findViewById(R.id.etError);

        li = findViewById(R.id.linearOtp);
        resendButton = findViewById(R.id.btResend);
        imgIcon = findViewById(R.id.imgBtnIcon);

        backbt = findViewById(R.id.imgBtnBackOtp);
        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(OtpVerificationActivity.this, PhoneNumberActivity.class);
                startActivity(in);
            }
        });

        //Timer
        timer();
        //OTP values
        num1 = findViewById(R.id.etVerif1);
        num2 = findViewById(R.id.etVerif2);
        num3 = findViewById(R.id.etVerif3);
        num4 = findViewById(R.id.etVerif4);
        num5 = findViewById(R.id.etVerif5);
        num6 = findViewById(R.id.etVerif6);


        num1.setFocusableInTouchMode(false);
        num1.setFocusable(false);
        num2.setFocusableInTouchMode(false);
        num2.setFocusable(false);
        num3.setFocusableInTouchMode(false);
        num3.setFocusable(false);
        num4.setFocusableInTouchMode(false);
        num4.setFocusable(false);
        num5.setFocusableInTouchMode(false);
        num5.setFocusable(false);
        num6.setFocusableInTouchMode(false);
        num6.setFocusable(false);

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setFocusableInTouchMode(true);
                num1.setFocusable(true);
            }
        });
        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                num2.setFocusableInTouchMode(true);
                num2.setFocusable(true);
                num2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {
                num2.setFocusable(true);
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num1.getText().length() != 0) {
                    num2.setFocusableInTouchMode(true);
                    num2.setFocusable(true);
                }
            }
        });
        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                num3.setFocusableInTouchMode(true);
                num3.setFocusable(true);
                num3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num1.getText().length() != 0 && num2.getText().length() != 0) {
                    num3.setFocusableInTouchMode(true);
                    num3.setFocusable(true);
                }
            }
        });
        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                num4.setFocusableInTouchMode(true);
                num4.setFocusable(true);
                num4.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num1.getText().length() != 0 && num2.getText().length() != 0 && num3.getText().length() != 0) {
                    num4.setFocusableInTouchMode(true);
                    num4.setFocusable(true);
                }
            }
        });
        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                num5.setFocusableInTouchMode(true);
                num5.setFocusable(true);
                num5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num1.getText().length() != 0 && num2.getText().length() != 0 && num3.getText().length() != 0 && num4.getText().length() != 0) {
                    num5.setFocusableInTouchMode(true);
                    num5.setFocusable(true);
                }
            }
        });
        num5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                num6.setFocusableInTouchMode(true);
                num6.setFocusable(true);
                num6.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num1.getText().length() != 0 && num2.getText().length() != 0 && num3.getText().length() != 0 && num4.getText().length() != 0 && num5.getText().length() != 0) {
                    num6.setFocusableInTouchMode(true);
                    num6.setFocusable(true);
                }
            }
        });
        num6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                codeText = String.format("%s%s%s%s%s%s", num1.getText().toString(),num2.getText().toString(), num3.getText().toString()
                        , num4.getText().toString(), num5.getText().toString(), num6.getText().toString());
                //Toast.makeText(OtpVerificationActivity.this,codeText,Toast.LENGTH_LONG).show();
                if(num6.getText().toString() != "") {
                    if (f == false) {
                        verifyCode();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);

            }
        });

        Intent in=getIntent();
        phoneText = in.getStringExtra("phNumber");
        phoneCode = in.getStringExtra("mobcode");
        phoneText =phoneCode+phoneText;
        //send otp to mobile
        sendCode();

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=false;

                countdowntimer.cancel();
                resendCode();
                timer();
                String clr="";
                num1.setText(clr);
                num2.setText(clr);
                num3.setText(clr);
                num4.setText(clr);
                num5.setText(clr);
                num6.setText(clr);
                num1.requestFocus();

            }
        });

    }



    private void timer() {
        error.setText("");
        countdowntimer =  new CountDownTimer(60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                text1.setText("" + String.format(FORMAT,

                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                text1.setText("00:00");
                error.setText("Timed Out");
                f=true;
            }
        }.start();

    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Toast.makeText(OtpVerificationActivity.this, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            // Toast.makeText(OtpVerificationActivity.this,"SMS Quota exceeded",Toast.LENGTH_LONG).show();

                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        //  Toast.makeText(OtpVerificationActivity.this,phoneVerificationId,Toast.LENGTH_LONG).show();
//
                    }
                };
    }
    public void sendCode() {

        String phoneNumber = phoneText;

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }
    public void resendCode() {

        String phoneNumber = phoneText;

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);

    }
    public void verifyCode() {
        try
        {
            String code = codeText;

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        }
        catch (Exception e)
        {
            // Toast.makeText(OtpVerificationActivity.this,""+e,Toast.LENGTH_LONG).show();

        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            showDiag();
                            // Intent in=new Intent(OtpVerificationActivity.this, MapsActivity.class);
                            // startActivity(in);

                            // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            OtpVerificationActivity.this.finish();
                            FirebaseUser user = task.getResult().getUser();
                            String uid = user.getUid();
                            // Save the text in SharedPreference
                            String ph=phoneText;
                            session.save(OtpVerificationActivity.this,"uid", uid);
                            session.save(OtpVerificationActivity.this,"phno", ph);

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                error.setText("Invalid OTP");
                                try {

                                    String clr = "";
                                    num1.setText(clr);
                                    num2.setText(clr);
                                    num3.setText(clr);
                                    num4.setText(clr);
                                    num5.setText(clr);
                                    num6.setText(clr);
                                    num1.requestFocus();
                                }
                                catch (Exception e)
                                {
                                    //Toast.makeText(OtpVerificationActivity.this,""+e,Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //dialog box with success message and redirect to next activity
    private void showDiag() {

        final View dialogView = View.inflate(this,R.layout.dialog,null);

        final Dialog dialog = new Dialog(this,R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);


        //Timer
        new CountDownTimer(1500, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                text1.setText("" + String.format(FORMAT,

                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {

                Intent in=new Intent(OtpVerificationActivity.this, ProfileEditActivity.class);
                startActivity(in);
                OtpVerificationActivity.this.finish();
            }
        }.start();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK){

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });



        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (imgIcon.getX() + (imgIcon.getWidth()/2));
        int cy = (int) (imgIcon.getY())+ imgIcon.getHeight() + 56;


        if(b){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }
}

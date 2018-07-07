package com.example.acer.nodetest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    Activity context = this;
    private static final String TAG = "AdminHome";
    TextView err;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText uname1 = (EditText) findViewById(R.id.nameInput);
        err = (TextView) findViewById(R.id.textView2);
        final EditText password1 = (EditText) findViewById(R.id.passwordInput);
        Button btnLogin = (Button) findViewById(R.id.button);
        Button btnReg = findViewById(R.id.reg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(MainActivity.this,RegActvity.class);
                startActivity(in);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                err.setText("");
                String uname = uname1.getText().toString().trim();
                String password = password1.getText().toString().trim();
                String url = "http://192.168.48.176:8000/user/signin";
                try{


                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("email" , uname);
                    jsonObject.put("password" , password);
                    final String mRequestBody = jsonObject.toString();

                    JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,
                            jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String s =response.getString("status");
                                if(s.equals("success")){
                                    Intent in =new Intent(MainActivity.this,adminHome.class);
                                    startActivity(in);
                                }
                                else{
                                    Toast.makeText(MainActivity.this,response.getString("msg"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            System.out.println(response.toString());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // As of f605da3 the following should work
                            NetworkResponse response = error.networkResponse;
                            if ((error instanceof ServerError && response != null) ||(error instanceof AuthFailureError && response != null) ){
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    String s =obj.getString("msg");
                                    err.setText(s);
                                    Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }){
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() {
                            try{
                                return mRequestBody == null ? null :
                                        mRequestBody.getBytes("utf-8");
                            }catch (UnsupportedEncodingException e){
                                return null;
                            }
                        }
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }
//                        @Override
//                        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                            int s = response.statusCode;
//                            int d=s;
////                            if(response.statusCode == 200){
////
////                                Intent in =new Intent(MainActivity.this,adminHome.class);
////                                startActivity(in);
////                            }
////                             if(response.statusCode == 204)
////                             {
////                                // Toast.makeText(MainActivity.this,"no user found",Toast.LENGTH_SHORT);
////                                 err.setText("Invalid login");
////                             }
//                            if(response.statusCode == 404)
//                            {
//                                 Toast.makeText(MainActivity.this,"no user found",Toast.LENGTH_SHORT).show();
//                            err.setText("Enter login details");
//                        }
//                            return super.parseNetworkResponse(response);
//                        }

                    };

                    Volley.newRequestQueue(MainActivity.this).add(jor);
                }
                catch (JSONException e){

                }
            }

        });
    }

}

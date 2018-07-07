package com.example.acer.nodetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegActvity extends AppCompatActivity {
   EditText uname1,email1,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_actvity);
        Button btnReg = findViewById(R.id.reg1);
        uname1 = findViewById(R.id.uname);
        email1 = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btnReg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String uname = uname1.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String email = email1.getText().toString().trim();
                String url = "http://192.168.48.176:8000/user/signup";
                try {


                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", uname);
                    jsonObject.put("email", email);
                    jsonObject.put("password", password);
                    final String mRequestBody = jsonObject.toString();

                    JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url,
                            jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                String s =response.getString("status");
                                if(s.equals("success")){
                                    Toast.makeText(RegActvity.this,response.getString("msg"),Toast.LENGTH_SHORT).show();
                                    Intent in =new Intent(RegActvity.this,MainActivity.class);
                                    startActivity(in);
                                }
                                else{
                                    Toast.makeText(RegActvity.this,response.getString("msg"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            NetworkResponse response = error.networkResponse;
                            if ((error instanceof ServerError && response != null) ||(error instanceof AuthFailureError && response != null) ){
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    String s =obj.getString("msg");
                                    Toast.makeText(RegActvity.this,s,Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace();
                                }
                            }

                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() {
                            try {
                                return mRequestBody == null ? null :
                                        mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                return null;
                            }
                        }

                        @Override
                        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


                            return super.parseNetworkResponse(response);
                        }

                    };

                    Volley.newRequestQueue(RegActvity.this).add(jor);
                } catch (JSONException e) {

                }
            }

        });
    }
}

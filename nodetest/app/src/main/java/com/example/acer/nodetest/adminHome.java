package com.example.acer.nodetest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class adminHome extends AppCompatActivity {
           String TAG ="admin";
    JSONObject FinalJSonObject ;
    TextView tv;
    ListView listView ;
    ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        listView = (ListView) findViewById(R.id.lv1);

        initJSON();

    }
    private void initJSON(){
        String url = "http://192.168.48.176:8000/user/findAll";


        JSONObject jsonObject=new JSONObject();

        final String mRequestBody = jsonObject.toString();

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
                new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray response) {
                try {

                   // JSONArray jsonarray = new JSONArray(response);
                    for(int i=0; i < response.length(); i++) {
                        JSONObject jsonobject = response.getJSONObject(i);
                        String uname       = jsonobject.getString("username");
                        String email    = jsonobject.getString("email");
                        String password    = jsonobject.getString("password");
                        list.add(uname+"\n"+email);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> itemsAdapter;
                itemsAdapter = new ArrayAdapter<String>(adminHome.this, android.R.layout.simple_list_item_1,list);
                listView.setAdapter(itemsAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {


                return super.parseNetworkResponse(response);
            }

        };
        Volley.newRequestQueue(adminHome.this).add(arrayreq);
    }

}

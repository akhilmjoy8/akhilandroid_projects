package com.example.acer.notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.acer.currentlocation.R;

import org.json.JSONException;

public class MainActivity extends Activity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent( MainActivity.this,BackgroundService.class));

        FireMessage f = null;
        try {
            f = new FireMessage("MY TITLE", "TEST MESSAGE");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TO SINGLE DEVICE
    /*  String fireBaseToken="c2N_8u1leLY:APA91bFBNFYDARLWC74QmCwziX-YQ68dKLNRyVjE6_sg3zs-dPQRdl1QU9X6p8SkYNN4Zl7y-yxBX5uU0KEKJlam7t7MiKkPErH39iyiHcgBvazffnm6BsKjRCsKf70DE5tS9rIp_HCk";
       f.sendToToken(fireBaseToken); */

        // TO MULTIPLE DEVICE
    /*  JSONArray tokens = new JSONArray();
      tokens.put("c2N_8u1leLY:APA91bFBNFYDARLWC74QmCwziX-YQ68dKLNRyVjE6_sg3zs-dPQRdl1QU9X6p8SkYNN4Zl7y-yxBX5uU0KEKJlam7t7MiKkPErH39iyiHcgBvazffnm6BsKjRCsKf70DE5tS9rIp_HCk");
      tokens.put("c2R_8u1leLY:APA91bFBNFYDARLWC74QmCwziX-YQ68dKLNRyVjE6_sg3zs-dPQRdl1QU9X6p8SkYNN4Zl7y-yxBX5uU0KEKJlam7t7MiKkPErH39iyiHcgBvazffnm6BsKjRCsKf70DE5tS9rIp_HCk");
       f.sendToGroup(tokens);  */

//        //TO TOPIC
//        String topic="yourTopicName";
//        f.sendToTopic(topic);
    }
}

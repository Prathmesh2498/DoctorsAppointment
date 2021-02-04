package com.example.prathmesh.projectsdl;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;

import javax.sql.StatementEvent;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //Get Data from menu
        Intent intent = getIntent();
        Bundle getFinalData = intent.getExtras();
        final String Summary_Data = getFinalData.getString("Data_For_Summary");
        final String get_Contact = getFinalData.getString("Data_For_Contact");
        final String get_Address = getFinalData.getString("Data_For_Address");

        /*String Summary_Data = intent.getStringExtra("Data_For_Summary");
        final String get_Contact = intent.getStringExtra("Data_For_Contact");
        final String get_Address = intent.getStringExtra("Data_For_Address");*/

        final String Data_for_Display = Summary_Data+"\n4.Contact: "+get_Contact+"\n5.Address: "+get_Address;


        //Set up Components
        TextView Summary, msg;
        Button call, map;

        Summary = (TextView)findViewById(R.id.Summary);

        msg = (TextView)findViewById(R.id.msg);
        msg.setText(Data_for_Display);

        call = (Button)findViewById(R.id.bCall);
        map = (Button)findViewById(R.id.bMap);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+get_Contact));
                    startActivity(call);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q= "+get_Address));
                startActivity(map);
            }
        });


    }
}

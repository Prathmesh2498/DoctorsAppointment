package com.example.prathmesh.projectsdl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Current_Appointments extends AppCompatActivity {
    TextView Information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__appointments);

        Intent from_menu = getIntent();

        Bundle b =  from_menu.getExtras();
        String Details = b.getString("Detail");
        String count = b.getString("Count");
        String Date = b.getString("Date");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String date = dateFormat.format(cal.getTime());

        int current_date = Integer.parseInt(date);
        int Appointment_date = Integer.parseInt(Date);
        int Count = Integer.parseInt(count);

        if(current_date>=Appointment_date)
        Count = 7- (current_date - Appointment_date);
        else
            Count = 7 - ((30+current_date)-Appointment_date);


        DatabaseSQLite db = new DatabaseSQLite(this);
        db.open();
        db.update(Count);
        db.close();



        Information = (TextView)findViewById(R.id.Info);
        Information.setText(Details+"\nDays to appointment: "+Count);

    }
}

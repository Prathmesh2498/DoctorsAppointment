package com.example.prathmesh.projectsdl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Locale;

public class Menu extends AppCompatActivity {

    //Text-to-Speech
    TextToSpeech speak;
    String toSpeak="Appointment Booked Successfully";

    //UI Variables
    private TextView mWelcomeMsg,mtext , mSummary;
    private Button mrbgynaec,mrbortho,mrbopd, mSum, mAppointments;

    //Result Codes for Doctors
    private final int Ortho = 1;
    private final int OPD = 2;
    private final int Gynaec = 3;
    private int number_of_appointments;


    //FILE Name For Shared Preferences
    String SHARED_PREF_FILENAME = "com.example.prathmesh.projectsdl.file";

    String data_From_DOCTOR;

    //Trying to Override onBackPressed to prevent Accidental logout
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count=0;
        if(count==0)
        Toast.makeText(Menu.this , "Signed Out Successfully" , Toast.LENGTH_SHORT ).show();
        count++;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&resultCode==RESULT_OK){



            mSummary.setText("Your Appointment was booked Sucessfully!\nCheck Summary for more details.");

            Bundle getData = data.getExtras();
            final String Appointment_Data = getData.getString("Data_For_Summary");
            final String Contact_Data = getData.getString("Data_For_Contact");
            final String Address_Data = getData.getString("Data_For_Address");


          /*final String Appointment_Data = data.getStringExtra("Data_For_Summary");
            final String Contact_Data = data.getStringExtra("Data_For_Contact");
            final String Address_Data = data.getStringExtra("Data_For_Address");*/

            mSum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent_from_menu = new Intent(Menu.this, com.example.prathmesh.projectsdl.Summary.class);
                    Bundle sendData = new Bundle();
                    sendData.putString("Data_For_Summary", Appointment_Data);
                    sendData.putString("Data_For_Contact", Contact_Data);
                    sendData.putString("Data_For_Address", Address_Data);
                    intent_from_menu.putExtras(sendData);


                    /*intent_from_menu.putExtra("Data_For_Summary",Appointment_Data);
                    intent_from_menu.putExtra("Data_For_Contact", Contact_Data);
                    intent_from_menu.putExtra("Data_For_Address", Address_Data);*/

                    startActivity(intent_from_menu);
                }
            });
        }

        if(requestCode==2&&resultCode==RESULT_OK){



            mSummary.setText("Your Appointment was booked Sucessfully!\nCheck Summary for more details.");

            Bundle getData = data.getExtras();
            final String Appointment_Data = getData.getString("Data_For_Summary");
            final String Contact_Data = getData.getString("Data_For_Contact");
            final String Address_Data = getData.getString("Data_For_Address");

            /*final String Appointment_Data = data.getStringExtra("Data_For_Summary");
            final String Contact_Data = data.getStringExtra("Data_For_Contact");
            final String Address_Data = data.getStringExtra("Data_For_Address");*/

            mSum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_from_menu = new Intent(Menu.this, com.example.prathmesh.projectsdl.Summary.class);

                    Bundle sendData = new Bundle();
                    sendData.putString("Data_For_Summary", Appointment_Data);
                    sendData.putString("Data_For_Contact", Contact_Data);
                    sendData.putString("Data_For_Address", Address_Data);
                    intent_from_menu.putExtras(sendData);



                    /*intent_from_menu.putExtra("Data_For_Summary",Appointment_Data);
                    intent_from_menu.putExtra("Data_For_Contact", Contact_Data);
                    intent_from_menu.putExtra("Data_For_Address", Address_Data);*/


                    startActivity(intent_from_menu);
                }
            });
        }

        if(requestCode==1&&resultCode==RESULT_OK){



            mSummary.setText("Your Appointment was booked Sucessfully!\nCheck Summary for more details.");

            Bundle getData = data.getExtras();
            final String Appointment_Data = getData.getString("Data_For_Summary");
            final String Contact_Data = getData.getString("Data_For_Contact");
            final String Address_Data = getData.getString("Data_For_Address");

            /*final String Appointment_Data = data.getStringExtra("Data_For_Summary");
            final String Contact_Data = data.getStringExtra("Data_For_Contact");
            final String Address_Data = data.getStringExtra("Data_For_Address");*/

            mSum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent_from_menu = new Intent(Menu.this, com.example.prathmesh.projectsdl.Summary.class);
                    Bundle sendData = new Bundle();
                    sendData.putString("Data_For_Summary", Appointment_Data);
                    sendData.putString("Data_For_Contact", Contact_Data);
                    sendData.putString("Data_For_Address", Address_Data);
                    intent_from_menu.putExtras(sendData);

                    /*intent_from_menu.putExtra("Data_For_Summary",Appointment_Data);
                    intent_from_menu.putExtra("Data_For_Contact", Contact_Data);
                    intent_from_menu.putExtra("Data_For_Address", Address_Data);*/

                    startActivity(intent_from_menu);
                }
            });
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Intent from_LogIn = getIntent();

        final String User = from_LogIn.getStringExtra("ID");

        //Connection of Attributes
        mWelcomeMsg=(TextView)findViewById(R.id.welcomeMsg);
        mtext=(TextView)findViewById(R.id.text);
        mSummary=(TextView)findViewById(R.id.Summary);

        mrbgynaec=(Button)findViewById(R.id.rbgynaec);
        mrbopd=(Button)findViewById(R.id.rbopd);
        mrbortho=(Button)findViewById(R.id.rbortho);
        mSum=(Button)findViewById(R.id.mSummary);
        mAppointments=(Button)findViewById(R.id.Appointment);

        //Firebase
        FirebaseDatabase muFirebaseDatabase;
        DatabaseReference muDatabaseReference;

        muFirebaseDatabase = FirebaseDatabase.getInstance();
        muDatabaseReference = muFirebaseDatabase.getReference().child("Client");

        //Check total appointments
        final DatabaseSQLite db = new DatabaseSQLite(this);
        db.open();
        final int[] number_of_appointments = {db.getCount()};
        db.close();
        final int [] allowed = new int[1];
        allowed[0]=1;
        if(number_of_appointments[0] ==1){
            allowed[0]=0;
        }
       // Toast.makeText(this, "count"+number_of_appointments, Toast.LENGTH_SHORT ).show();

        ValueEventListener muValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.child(User).child("Name").getValue(String.class);
                mWelcomeMsg.setText("Welcome "+Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mWelcomeMsg.setText("Error");
            }
        };

        muDatabaseReference.addListenerForSingleValueEvent(muValueEventListener);



        mrbortho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check total appointments
                final DatabaseSQLite db = new DatabaseSQLite(Menu.this);
                db.open();
                number_of_appointments[0] = db.getCount();
                db.close();
                final int [] allowed = new int[1];
                allowed[0]=1;
                if(number_of_appointments[0] ==1){
                    allowed[0]=0;
                }

                if(allowed[0]==1) {
                    Intent intent_from_menu = new Intent(Menu.this, Orthopedic.class);
                    intent_from_menu.putExtra("User", User);
                    startActivityForResult(intent_from_menu, Ortho);
                }else {
                    Toast.makeText(Menu.this, "You can only have one appointment at a time!", Toast.LENGTH_LONG).show();
                }

            }
        });

        mrbopd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check total appointments
                final DatabaseSQLite db = new DatabaseSQLite(Menu.this);
                db.open();
                number_of_appointments[0] = db.getCount();
                db.close();
                final int [] allowed = new int[1];
                allowed[0]=1;
                if(number_of_appointments[0] ==1){
                    allowed[0]=0;
                }

                if(allowed[0]==1) {
                    Intent intent_from_menu = new Intent(Menu.this, com.example.prathmesh.projectsdl.OPD.class);
                    intent_from_menu.putExtra("User", User);
                    startActivityForResult(intent_from_menu, OPD);
                }else {
                    Toast.makeText(Menu.this, "You can only have one appointment at a time!", Toast.LENGTH_LONG).show();
                }

            }

        });

        mrbgynaec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check total appointments
                final DatabaseSQLite db = new DatabaseSQLite(Menu.this);
                db.open();
                number_of_appointments[0] = db.getCount();
                db.close();
                final int [] allowed = new int[1];
                allowed[0]=1;
                if(number_of_appointments[0] ==1){
                    allowed[0]=0;
                }

                if(allowed[0]==1) {
                    Intent intent_from_menu = new Intent(Menu.this, com.example.prathmesh.projectsdl.Gynaecologist.class);
                    intent_from_menu.putExtra("User", User);
                    startActivityForResult(intent_from_menu, Gynaec);
                }else {
                    Toast.makeText(Menu.this, "You can only have one appointment at a time!", Toast.LENGTH_LONG).show();
                }

            }
        });

        mSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check total appointments
                final DatabaseSQLite db = new DatabaseSQLite(Menu.this);
                db.open();
                number_of_appointments[0] = db.getCount();
                db.close();
                final int [] allowed = new int[1];
                allowed[0]=1;
                if(number_of_appointments[0] == 0){
                    allowed[0]=0;
                }

              if(allowed[0]==1){

                  //Read from Shared Preference file
                  SharedPreferences pref = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE);
                  String Contact = pref.getString("Contact" , "911");
                  String Address = pref.getString("Address", "XXxX");

                  String []getData = new String[3];
                  db.open();
                  getData = db.retrieveData();
                  db.close();
                  String Details = getData[0];

                  Intent intent_from_menu = new Intent(Menu.this, com.example.prathmesh.projectsdl.Summary.class);
                  Bundle sendData = new Bundle();
                  sendData.putString("Data_For_Summary", Details);
                  sendData.putString("Data_For_Contact", Contact);
                  sendData.putString("Data_For_Address", Address);
                  intent_from_menu.putExtras(sendData);
                  startActivity(intent_from_menu);

              }else {
                  Toast.makeText(Menu.this, "Please Book Appointment First or check current appointments!", Toast.LENGTH_SHORT).show();
              }


            }
        });

        mAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] getData = new String[3];

                //Check total appointments
                final DatabaseSQLite db = new DatabaseSQLite(Menu.this);
                db.open();
                number_of_appointments[0] = db.getCount();
                db.close();
                final int [] allowed = new int[1];
                allowed[0]=1;
                if(number_of_appointments[0] == 0){
                    allowed[0]=0;
                }


                if(allowed[0]==1) {
                    db.open();
                    getData = db.retrieveData();
                    db.close();

                    Bundle b = new Bundle();
                    b.putString("Detail", getData[0]);
                    b.putString("Date", getData[1]);
                    b.putString("Count", getData[2]);

                    Intent to_Appointment = new Intent(Menu.this, com.example.prathmesh.projectsdl.Current_Appointments.class);
                    to_Appointment.putExtras(b);
                    startActivity(to_Appointment);
                }else {
                    Toast.makeText(Menu.this,"You have no current Appointments!", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

}
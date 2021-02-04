package com.example.prathmesh.projectsdl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Orthopedic extends AppCompatActivity {

    //UI Variables
    private TextView oavailability, ocounter, oSummary, oDocAvail;
    private Button oBook;
    private ProgressBar oProgressBar;

    //Firebase
    private FirebaseDatabase oFirebaseDatabase;
    private DatabaseReference oDatabaseReference, setDataDatabaseReference;

    //Text-to-Speech
    TextToSpeech speak;
    String toSpeak="Appointment Booked Successfully";

    //FILE Name For Shared Preferences
    String SHARED_PREF_FILENAME = "com.example.prathmesh.projectsdl.file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orthopedic);

        /*Init Speech and confirm appointment*/
        speak =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speak.setLanguage(Locale.US);
                }
            }

        });

        Intent intent_from_orthopedic = getIntent();
        final String UserName = intent_from_orthopedic.getStringExtra("User");

        //Map DOC names to DB
        final String Doc1="Dr.Sourabh kulkarni";
        final String[] Doctor = new String[1];
        final String[] DocInfotoSendBack = new String[3];
        final String Doc2="Dr.Prathmesh Deshpande";
        final String Doc3="Dr.Dhruva Deshpande";

        final Spinner myspinner =(Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(Orthopedic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Orthopedic));

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myspinner.setAdapter(myadapter);

        //Link UI Elements
       // oavailability=(TextView) findViewById(R.id.availability);
        //ocounter=(TextView) findViewById(R.id.counter);
        oSummary=(TextView) findViewById(R.id.Summary);
        oDocAvail=(TextView) findViewById(R.id.docAvail);

        oBook=(Button)findViewById(R.id.book);

        //oProgressBar = (ProgressBar)findViewById(R.id.pgBar);


        //Link Firebase

        oFirebaseDatabase = FirebaseDatabase.getInstance();
        oDatabaseReference = oFirebaseDatabase.getReference().child("Doctor").child("Orthopedic");

        final int[] flag = new int[1];
        flag[0] = 1;


        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text=myspinner.getSelectedItem().toString();
                if(text.equals(Doc1)){
                    Doctor[0]="O_Doc1";
                    String DocInfo1 = "1. Name: "+Doc1+"\n2.Years Of Exp: 10\n3.Speciality: Limbs";
                    oSummary.setText(DocInfo1);
                    DocInfotoSendBack[0] = DocInfo1;
                    DocInfotoSendBack[1] = "919929163452";
                    DocInfotoSendBack[2] = "Near Sholay Hotel, Aundh Hinjewadi Road, Vishal Nagar-Pimple Nilakh, Pune - 411027, Next to Bajaj Service Centre, Vishal Nagar, Jagtap Dairy";

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                oDocAvail.setText("No slots available, Please choose another Doctor");
                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                oDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                oDocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            oDocAvail.setText("Cancelled");
                        }
                    };
                    oDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }
                else if(text.equals(Doc2)){
                    Doctor[0]="O_Doc2";
                    String DocInfo2 = "1. Name: "+Doc2+"\n2.Years Of Exp: 7\n3.Speciality: Torso";
                    oSummary.setText(DocInfo2);
                    DocInfotoSendBack[0] = DocInfo2;
                    DocInfotoSendBack[1] = "918989773410";
                    DocInfotoSendBack[2] = "House Of Johnsons Building, Chinchwad East, Pune - 411019, Near Sai Palace Hotel Thermax Chowk Sambhaji Nagar";

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                oDocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                flag[0] = 1;
                                count++;
                                oDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                oDocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            oDocAvail.setText("Cancelled");
                        }
                    };
                    oDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }
                else if(text.equals(Doc3)){
                    Doctor[0]="O_Doc3";
                    String DocInfo3 = "1. Name: "+Doc3+"\n2.Years Of Exp: 30\n3.Speciality: Vertebral Column";
                    oSummary.setText(DocInfo3);
                    DocInfotoSendBack[0] = DocInfo3;
                    DocInfotoSendBack[1] = "919008534165";
                    DocInfotoSendBack[2] = "Pimple Nilakh Road, Baner, Pune - 411045, Near Prathamesh Park,Pune";

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                oDocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                oDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                oDocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            oDocAvail.setText("Cancelled");
                        }
                    };
                    oDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });








        oBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = flag[0];

                if (check == 1) {

                    //DB insert

                    final int count = 7;

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                    String date = dateFormat.format(cal.getTime());
                    int Date = Integer.parseInt(date);

                    String userDetails = DocInfotoSendBack[0]+"Contact: "+DocInfotoSendBack[1]+"Address: "+DocInfotoSendBack[2];

                    DatabaseSQLite db = new DatabaseSQLite(Orthopedic.this);
                    db.open();
                    db.addValues(userDetails, Date, count);
                    db.close();

                    //Write to a shared preference file
                    SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE).edit();
                    editor.putString("Contact" , DocInfotoSendBack[1]);
                    editor.putString("Address" , DocInfotoSendBack[2]);
                    editor.commit();

                    speak.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,null);

                    /*endTexttoSpeech}*/
                    oSummary.setText("Appointment Booked!");
                    Toast.makeText(Orthopedic.this, "Booked", Toast.LENGTH_SHORT).show();
                    setDataDatabaseReference = oFirebaseDatabase.getReference().child("Client");
                    setDataDatabaseReference.child(UserName).child("Doctor_Name").setValue(Doctor[0]);

                    Intent to_Menu = new Intent();
                    Bundle data = new Bundle();
                    data.putString("Data_For_Summary", DocInfotoSendBack[0]);
                    data.putString("Data_For_Contact", DocInfotoSendBack[1]);
                    data.putString("Data_For_Address", DocInfotoSendBack[2]);
                    to_Menu.putExtras(data);


                    /*to_Menu.putExtra("Data_For_Summary", DocInfotoSendBack[0]);
                    to_Menu.putExtra("Data_For_Contact", DocInfotoSendBack[1]);
                    to_Menu.putExtra("Data_For_Address", DocInfotoSendBack[2]);*/

                    setResult(RESULT_OK, to_Menu);
                    Orthopedic.this.finish();

                }
                else{
                    Toast.makeText(Orthopedic.this , "Cannot book, try again!" , Toast.LENGTH_SHORT).show();
                    speak.speak("Cannot book, try again", TextToSpeech.QUEUE_FLUSH, null,null);
                }
            }
        });



    }
}
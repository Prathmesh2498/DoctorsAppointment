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

public class OPD extends AppCompatActivity {

    //UI Elements
    private TextView Oavailability,Ocounter, OPDSummary, OPDdocAvail;
    private Button OBook;
    private ProgressBar opdProgressBar;

    //Firebase
    private FirebaseDatabase opdFirebaseDatabase;
    private DatabaseReference opdDatabaseReference, setDataDatabaseReference;

    //Text-to-Speech
    TextToSpeech speak;
    String toSpeak="Appointment Booked Successfully";

    //FILENAME
    String SHARED_PREF_FILENAME = "com.example.prathmesh.projectsdl.file";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opd);

        /*Init Speech and confirm appointment*/
        speak =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speak.setLanguage(Locale.US);
                }
            }

        });

        Intent intent_from_opd =getIntent();
        final String UserName = intent_from_opd.getStringExtra("User");

        final Spinner myspinner =(Spinner) findViewById(R.id.spinner);

        //Map Doctor names to DB
        final String Doc1="Dr.Arun Phadnis";
        final String Doc2="Dr.Mukesh Batra";
        final String Doc3="Dr.Parvati Halbe";
        final String[] Doctor = new String[1];
        final String[] DocInfotoSendBack = new String[3];


        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(OPD.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.OPD));

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myspinner.setAdapter(myadapter);

       // Oavailability=(TextView) findViewById(R.id.availability);
       // Ocounter=(TextView) findViewById(R.id.counter);
        OPDSummary=(TextView) findViewById(R.id.Summary);
        OPDdocAvail=(TextView) findViewById(R.id.docAvail);

        OBook=(Button)findViewById(R.id.book);

//        opdProgressBar = (ProgressBar)findViewById(R.id.pgBar);

        //Link Firebase
        opdFirebaseDatabase = FirebaseDatabase.getInstance();
        opdDatabaseReference = opdFirebaseDatabase.getReference().child("Doctor").child("OPD");

        //Flag to check availability
        final int[] flag = new int[1];
        flag[0] =1;

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text=myspinner.getSelectedItem().toString();
                if(text.equals(Doc1)){
                    Doctor[0]="Doc1";
                    String DocInfo1 = "1. Name: "+Doc1+"\n2.Years Of Exp: 6\n3.Speciality: ENT";
                    DocInfotoSendBack[0] = DocInfo1;
                    DocInfotoSendBack[1] = "918876523126";
                    DocInfotoSendBack[2] = "Shivadatta Complex, Baner Road, Balewadi-Baner, Pune - 411045, Near Orchid School Next to Seva Vikas Bank";
                    OPDSummary.setText(DocInfo1);

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                OPDdocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                opdDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                OPDdocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            OPDdocAvail.setText("Cancelled");
                        }
                    };
                    opdDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }
                else if(text.equals(Doc2)){
                    Doctor[0]="Doc2";
                    String DocInfo2 = "1. Name: "+Doc2+"\n2.Years Of Exp: 29\n3.Speciality: Viral Infection";
                    DocInfotoSendBack[0] = DocInfo2;
                    DocInfotoSendBack[1] = "918900963416";
                    DocInfotoSendBack[2] = "Add. 302, Veer Arcade, Kokane Chowk, Pimple Saudagar, Pune - 411027, Oppo. Reliance Mall";
                    OPDSummary.setText(DocInfo2);

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                OPDdocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                opdDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                OPDdocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            OPDdocAvail.setText("Cancelled");
                        }
                    };
                    opdDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }
                else if(text.equals(Doc3)){
                    Doctor[0]="Doc3";
                    String DocInfo3 = "1. Name: "+Doc2+"\n2.Years Of Exp: 14\n3.Speciality: Pediatrician";
                    DocInfotoSendBack[0] = DocInfo3;
                    DocInfotoSendBack[1] = "919099873413";
                    DocInfotoSendBack[2] = "We Heal Polyclinic Clinic D6 Rahul Park, Warje Malwadi-Warje, Pune - 411058, Next to OM Mangal Mart";

                    OPDSummary.setText(DocInfo3);

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                OPDdocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                opdDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                OPDdocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            OPDdocAvail.setText("Cancelled");
                        }
                    };
                    opdDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        OBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check = flag[0];

            if(check== 1){

                //DB insert

                final int count = 7;

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                String date = dateFormat.format(cal.getTime());
                int Date = Integer.parseInt(date);



                String userDetails = DocInfotoSendBack[0]+"Contact: "+DocInfotoSendBack[1]+"Address: "+DocInfotoSendBack[2];

                DatabaseSQLite db = new DatabaseSQLite(OPD.this);
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
                setDataDatabaseReference = opdFirebaseDatabase.getReference().child("Client");
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
                OPD.this.finish();

                }else{
                Toast.makeText(OPD.this , "Cannot book, try again!" , Toast.LENGTH_SHORT).show();
                speak.speak("Cannot book, try again", TextToSpeech.QUEUE_FLUSH, null,null);
                }
            }

        });
    }
}
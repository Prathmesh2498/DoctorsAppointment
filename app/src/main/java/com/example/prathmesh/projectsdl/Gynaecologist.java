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

public class Gynaecologist extends AppCompatActivity {

    //UI Variables
    private TextView gavailability, gcounter, gSummary , gDocAvail;
    private ProgressBar gProgressBar;
    private Button gBook;

    //Firebase
    FirebaseDatabase gFirebaseDatabase;
    DatabaseReference gDatabaseReference , setDataDatabaseReference;

    //Text-to-Speech
    TextToSpeech speak;
    String toSpeak="Appointment Booked Successfully";

    //FILE Name For Shared Preferences
    String SHARED_PREF_FILENAME = "com.example.prathmesh.projectsdl.file";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gynaecologist);

        /*Init Speech and confirm appointment*/
        speak =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speak.setLanguage(Locale.US);
                }
            }

        });

        //Link UI Elements
        Intent intent_from_gynaec = getIntent();
        final String UserName = intent_from_gynaec.getStringExtra("User");


        final Spinner myspinner =(Spinner) findViewById(R.id.spinner);

       // gavailability=(TextView) findViewById(R.id.availability);
       // gcounter=(TextView) findViewById(R.id.counter);
        gSummary=(TextView) findViewById(R.id.Summary);
        gDocAvail=(TextView) findViewById(R.id.docAvail);

        gBook=(Button)findViewById(R.id.book);

       // gProgressBar = (ProgressBar)findViewById(R.id.pgBar);

        //Map DOC names to DB
        final String[] Doctor = new String[1];
        final String[] DocInfotoSendBack = new String[3];
        final String Doc1="Dr.Arun Phadnis";
        final String Doc2="Dr.Rama Kulkarni";
        final String Doc3="Dr.Praniti Chaphekar";

        //ArrayAdapter to hold values in Drop Down
        ArrayAdapter <String> myadapter=new ArrayAdapter<String>(Gynaecologist.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Gynaecologist));

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myspinner.setAdapter(myadapter);

        //Write Documentation For Above Code

        //Firebase Linking
        gFirebaseDatabase = FirebaseDatabase.getInstance();
        gDatabaseReference = gFirebaseDatabase.getReference().child("Doctor").child("Gynaecologist");

        final int flag[] = new int[1];
        flag[0] =1;

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text=myspinner.getSelectedItem().toString();
                if(text.equals(Doc1)){
                    Doctor[0]="G_Doc1";
                    String DocInfo1 = "1. Name: "+Doc1+"\n2.Years Of Exp: 12\n3.Speciality: In Child Birth and C-Section";
                    DocInfotoSendBack[0] = DocInfo1;
                    DocInfotoSendBack[1] = "919898764537";
                    DocInfotoSendBack[2] = "Plot No 30 C, Karve Road Main Road, Karve Road Deccan, Pune - 411004, Near Tilak Tank Oposite to Garware College Near Erandawane";

                    gSummary.setText(DocInfo1);

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                gDocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                gDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                gDocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            gDocAvail.setText("Cancelled");
                        }
                    };
                    gDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }
                else if(text.equals(Doc2)){
                    Doctor[0]="G_Doc2";
                    String DocInfo2 = "1. Name: "+Doc2+"\n2.Years Of Exp: 22\n3.Speciality: Menstruation Problems";
                    DocInfotoSendBack[0] = DocInfo2;
                    DocInfotoSendBack[1] = "918888823451";
                    DocInfotoSendBack[2] = " C1/101 Silver Oak Society, Bishop School Road, Kalyani Nagar, Pune - 411014, Near Bank Of Maharashtra";

                    gSummary.setText(DocInfo2);

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                gDocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                gDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                gDocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            gDocAvail.setText("Cancelled");
                        }
                    };
                    gDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }
                else if(text.equals(Doc3)){
                    Doctor[0]="G_Doc3";
                    String DocInfo3 = "1. Name: "+Doc3+"\n2.Years Of Exp: 14\n3.Speciality: Egg Fertility";
                    DocInfotoSendBack[0] = DocInfo3;
                    DocInfotoSendBack[1] = "919087558243";
                    DocInfotoSendBack[2] = "Dattanadan Residency, 1st Floor, Narhe Road, Narhe Gaon, Pune - 411041, Near Datta Mandir Opposite Manaji Nagar Ganpati Mandir";

                    gSummary.setText(DocInfo3);

                    ValueEventListener gValueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Long count = (Long) dataSnapshot.child(Doctor[0]).child("count").getValue();

                            if (count == 5) {
                                gDocAvail.setText("No slots available, Please choose another Doctor");

                                flag[0] = 0;

                            } else {
                                count++;
                                flag[0] = 1;
                                gDatabaseReference.child(Doctor[0]).child("count").setValue(count);
                                gDocAvail.setText("Doctor available. Please press book to book your appointment");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            gDocAvail.setText("Cancelled");
                        }
                    };
                    gDatabaseReference.addListenerForSingleValueEvent(gValueEventListener);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        gBook.setOnClickListener(new View.OnClickListener() {
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

                    String userDetails = DocInfotoSendBack[0]+"\n4.Contact: "+DocInfotoSendBack[1]+"\n5.Address: "+DocInfotoSendBack[2];

                    DatabaseSQLite db = new DatabaseSQLite(Gynaecologist.this);
                    db.open();
                    db.addValues(userDetails, Date, count);
                    db.close();

                    //Write to a shared preference file
                    SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE).edit();
                    editor.putString("Contact" , DocInfotoSendBack[1]);
                    editor.putString("Address" , DocInfotoSendBack[2]);
                    editor.commit();

                    setDataDatabaseReference = gFirebaseDatabase.getReference().child("Client");
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

                    speak.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,null);

                    /*endTexttoSpeech}*/

                    setResult(RESULT_OK, to_Menu);
                    Gynaecologist.this.finish();
                }
                else{
                    Toast.makeText(Gynaecologist.this , "Cannot book, try again!" , Toast.LENGTH_SHORT).show();
                    speak.speak("Cannot book, try again", TextToSpeech.QUEUE_FLUSH, null,null);
                }
            }

        });



    }
}
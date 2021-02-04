package com.example.prathmesh.projectsdl;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class signUpInfo {
    public String User , Pass;
    signUpInfo(){
    User = "";
    Pass = "";
    }
    signUpInfo(String User , String Pass){
        this.User = User;
        this.Pass = Pass;
    }
}

public class MainActivity extends AppCompatActivity  {

    //UI Components
    private EditText pass , uname;
    private TextView Appname;
    private Button sub , login;
    private ProgressBar progressBar;

    //FireBase
    private FirebaseDatabase cFirebaseDatabase;
    private DatabaseReference cDatabaseReference;

    //SignUp Request Code
    final int ActivityRequestCode = 3;


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 3&&resultCode==RESULT_OK) {

            String message_from_SignUp = data.getStringExtra("Message");

            Toast.makeText(MainActivity.this, message_from_SignUp, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Link UI Components
        uname = (EditText)findViewById(R.id.Uname);
        pass = (EditText)findViewById(R.id.pass);
        sub = (Button)findViewById(R.id.sub);
        Appname = (TextView)findViewById(R.id.AppName);
        login = (Button)findViewById(R.id.LogIn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ImageView iv=(ImageView)findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.doctor2);


        //Intent - Go to SignUp Page
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent_from_main = new Intent(MainActivity.this , com.example.prathmesh.projectsdl.SignUp.class);
                startActivityForResult(intent_from_main , ActivityRequestCode);

            }
        });


        //Check LogIn Details
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(uname.getText().toString().isEmpty()||pass.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this , "Enter All Fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                    final String user = uname.getText().toString().trim();
                    final String password = pass.getText().toString().trim();

                    //Reset UI
                    uname.setText("");
                    pass.setText("");

                    /*Initialize Firebase objects*/

                    //Get Root Node
                    cFirebaseDatabase = FirebaseDatabase.getInstance();

                    //Attach to specific User
                    cDatabaseReference = cFirebaseDatabase.getReference().child("Client_Authentication");


                    //To get Data on Change of Data
                    ValueEventListener cvalueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            signUpInfo su = new signUpInfo();

                            //Get Username and Password from Database
                            su.User = dataSnapshot.child(user).child("Username").getValue(String.class);
                            su.Pass = dataSnapshot.child(user).child("Password").getValue(String.class);

                            //IF USERNAME OR PASSWORD IS NULL
                            if (su.User==null || su.Pass==null) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Invalid Combination, Try Again!", Toast.LENGTH_LONG).show();
                            } else {


                                //IF USERNAME And PASSWORD MATCH
                                if (su.User.equals(user) && su.Pass.equals(password)) {
                                    Intent Home_Page = new Intent(MainActivity.this, com.example.prathmesh.projectsdl.Menu.class);
                                    pass.setText("");
                                    uname.setText("");
                                    Home_Page.putExtra("ID", user);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(Home_Page);

                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "Invalid Combination, Try Again!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }


                    };
                    cDatabaseReference.addListenerForSingleValueEvent(cvalueEventListener);
            }
        });

    }


}

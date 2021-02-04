package com.example.prathmesh.projectsdl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class newUserInfo{
    public String Age, Doctor_Name, Gender, Name, Password;

    newUserInfo(){

    }

    newUserInfo (String Age , String Doctor_Name , String Gender , String Name , String Password){
        this.Age = Age;
        this.Doctor_Name = Doctor_Name;
        this.Gender = Gender;
        this.Name = Name;
        this.Password = Password;
    }
}

public class SignUp extends AppCompatActivity {

    //UI Variables
    private EditText sugetuser,sugetname,sugetpass,sugetage;
    private TextView supass,suname,sugender,suuser;
    private Button surbm,surbf,suCreateProf;


     /*Firebase variables*/

    //FireBase Root
    FirebaseDatabase suFirebaseDatabase;

    //Get Reference Of 'Client'
    DatabaseReference suDatabaseReference;

    //Get Reference of 'Client_Authentication'
    DatabaseReference suDatabaseReference_Client_Authentication;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Intent From Main
        final Intent intent_from_SignUp = getIntent();

        //Link UI Components
        sugetuser = (EditText)findViewById(R.id.getuser);
        sugetname = (EditText)findViewById(R.id.getname);
        sugetpass= (EditText) findViewById(R.id.getpass);
        sugetage = (EditText)findViewById(R.id.getage);

        supass= (TextView)findViewById(R.id.supass);
        suname= (TextView)findViewById(R.id.suname);
        sugender= (TextView)findViewById(R.id.gender);
        suuser= (TextView)findViewById(R.id.suuser);

        surbm= (Button)findViewById(R.id.rbm);
        surbm.setSelected(false);
        surbf= (Button)findViewById(R.id.rbf);
        surbf.setSelected(false);
        suCreateProf= (Button)findViewById(R.id.createProf);


        final String[] gender = new String[1];

        surbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                surbm.setSelected(true);
                gender[0] = "male";
            }
        });

        surbf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                surbf.setSelected(true);
                gender[0] = "female";
            }
        });

        //Get Data from UI and Send to Database
        suCreateProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sugetname.getText().toString().isEmpty() || sugetage.getText().toString().isEmpty() ||
                        sugetuser.getText().toString().isEmpty() || sugetpass.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                    String Name = sugetname.getText().toString().trim();
                    String Age = sugetage.getText().toString().trim();
                    String user = sugetuser.getText().toString().trim();
                    String Password = sugetpass.getText().toString().trim();
                    String Gender = gender[0];
                    String Doctor_name = "XXX";

                    newUserInfo newUser = new newUserInfo(Age, Doctor_name, Gender, Name, Password);

                    suFirebaseDatabase = FirebaseDatabase.getInstance();

                    //Write Data of New Client
                    suDatabaseReference = suFirebaseDatabase.getReference().child("Client");

                    suDatabaseReference.child(user).setValue(newUser);

                    //Write Data For Client_Authentication of new Client

                    suDatabaseReference_Client_Authentication = suFirebaseDatabase.getReference().child("Client_Authentication");
                    suDatabaseReference_Client_Authentication.child(user).child("Username").setValue(user);
                    suDatabaseReference_Client_Authentication.child(user).child("Password").setValue(Password);

                    //Go Back to LogIn Page
                    Intent go_Back = new Intent();
                    go_Back.putExtra("Message", "Successfully created Account");
                    setResult(RESULT_OK, go_Back);
                    SignUp.this.finish();

            }
        });





    }
}

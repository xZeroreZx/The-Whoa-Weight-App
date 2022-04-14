package com.assignments.whoa_weight_app;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    //Create the variables we will use
    Button Register;
    EditText Name, Username, Password;
    FirebaseAuth firebaseAuth;

// Standard onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setupUIViews();
        //Get the instance of the Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to Database
                    String user_name = Username.getText().toString().trim();
                    String user_pass = Password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_name, user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendEmailVerification();
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }
                        }

                    });
                }
            }
        });
    }

    private void setupUIViews(){

        Register = (Button) findViewById(R.id.buttonRegister);

        Username = (EditText) findViewById(R.id.editUsername);
        Password = (EditText) findViewById(R.id.editPassword);
        Name = (EditText) findViewById(R.id.editName);

    }

    private Boolean validate(){
        Boolean result = false;

        String nameUser = Username.getText().toString();
        String passUser = Password.getText().toString();
        String nameAlpha = Name.getText().toString();

        if(nameUser.isEmpty() || passUser.isEmpty() || nameAlpha.isEmpty()){
            Toast.makeText(this,"Please make sure all fields are complete", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Successfully Registered, Verification Email Sent", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent (RegisterActivity.this, MainActivity.class));

                    }else{
                        Toast.makeText(RegisterActivity.this, "Verification Email did not send", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
package com.assignments.whoa_weight_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button login_btn, signup_btn;
    EditText username, password;
    TextView forgot_password, user_state;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);
        forgot_password = (TextView)findViewById(R.id.forgot_password_link);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
    //Checks if the user has signed in before from the device
        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, WeightActivity.class));
        }
    //Logs user in to the application
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString().trim();
                String userPass = password.getText().toString().trim();
                if(userName.isEmpty() || userPass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill in all Fields", Toast.LENGTH_SHORT).show();
                }else {
                    signIn(username.getText().toString(), password.getText().toString());
                }
            }
        });
        //Directs the application to the Register activity.
        signup_btn = findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    //This will send the user to the Password Reset activity
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });
        user_state = findViewById(R.id.user_state);


    }
    //Sign in method to validate and setting text in case of failure.
    private void signIn (String username, String password) {
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    checkEmailVerification();
                    startActivity(new Intent(MainActivity.this, WeightActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //Checks if the user has verified their email address for added security.
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this, WeightActivity.class));
        }else{
            Toast.makeText(MainActivity.this, "Please verify your Email", Toast.LENGTH_SHORT).show();
            //Sign out so that the application doesn't crash when email verification fails.
            firebaseAuth.signOut();
        }
    }

}

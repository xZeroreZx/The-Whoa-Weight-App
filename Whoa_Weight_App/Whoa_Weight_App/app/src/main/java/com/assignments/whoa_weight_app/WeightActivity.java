package com.assignments.whoa_weight_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WeightActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button logout;
    EditText iDate;
    EditText iWeight;
    EditText iGoal;
    String userDate;
    String userWeight;
    String userGoal;
    Integer currWeight;
    Integer toGoal;

    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().hide();
//Gets the current instance of the Firebase information determinate of the current user.
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        logout = (Button) findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(WeightActivity.this, MainActivity.class));
            }
        });

//assists in adding information to the database and the user screen.
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDate = (EditText) findViewById(R.id.etDate);
                iWeight = (EditText) findViewById(R.id.etWeight);
                iGoal = (EditText) findViewById(R.id.etGoal);
                userDate = iDate.getText().toString().trim();
                userWeight = iWeight.getText().toString().trim();
                userGoal = iGoal.getText().toString().trim();

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    if (userDate.isEmpty() || userWeight.isEmpty() || userGoal.isEmpty()) {
                        Toast.makeText(WeightActivity.this, "Please fill in all areas", Toast.LENGTH_SHORT).show();
                    } else {
                        sendUserData();
                        getUserData();
                        Toast.makeText(WeightActivity.this, "Upload Complete!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

    }
    private void sendUserData(){ //sends user data to the Firebase database.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile= new UserProfile(userDate, userWeight, userGoal);
        myRef.setValue(userProfile);

    }

    private void setupUIViews(){
        iDate = (EditText)findViewById(R.id.etDate);
        iWeight= (EditText) findViewById(R.id.etWeight);
        iGoal = (EditText) findViewById(R.id.etGoal);
    }
//getter to locate the current information for the user.
    private void getUserData(){
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                iDate.setText(userProfile.getTodayDate());
                iWeight.setText(userProfile.getTodayWeight());
                iGoal.setText(userProfile.getMyGoal());
                currWeight = Integer.parseInt(iWeight.getText().toString());
                toGoal = Integer.parseInt(iGoal.getText().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
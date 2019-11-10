package com.example.test2;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PostGraduation_Details extends AppCompatActivity {

    private EditText editTextYOJ, editTextYOP, editTextSemester, editTextSGPA;
    private EditText editTextCGPA, editTextBacklog, editTextAddress;
    private String YOJ, YOP, semester, SGPA, CGPA, backlog, address;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_graduation__details);

        Button btnSubmit;
        auth = FirebaseAuth.getInstance();

        editTextYOJ = findViewById(R.id.textPGYOJ);
        editTextYOP = findViewById(R.id.textPGYOP);
        editTextSemester = findViewById(R.id.textPGSem);
        editTextSGPA = findViewById(R.id.textPGSGPA);
        editTextCGPA = findViewById(R.id.textPGCGPA);
        editTextBacklog = findViewById(R.id.textPGBacklog);
        editTextAddress = findViewById(R.id.textPresentAddress);
        progressBar = findViewById(R.id.progressBarPG);
        btnSubmit = findViewById(R.id.buttonPG);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YOJ = editTextYOJ.getText().toString().trim();
                YOP = editTextYOP.getText().toString().trim();
                semester = editTextSemester.getText().toString().trim();
                SGPA = editTextSGPA.getText().toString().trim();
                CGPA = editTextCGPA.getText().toString().trim();
                backlog = editTextBacklog.getText().toString().trim();
                address = editTextAddress.getText().toString().trim();

                if (TextUtils.isEmpty(YOJ)) {
                    editTextYOJ.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(YOP)) {
                    editTextYOP.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(semester)) {
                    editTextSemester.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(SGPA)) {
                    editTextSGPA.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(CGPA)) {
                    editTextCGPA.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(backlog)) {
                    editTextBacklog.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    editTextAddress.setError("Field cannot be blank");
                    return;
                }

                intoDatabase();
            }
        });

    }

    private void intoDatabase()
    {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        DatabaseReference rootRef;
        String uid, collegeEmail;
        FirebaseUser user = auth.getCurrentUser();

        if(user != null)
        {
            uid = user.getUid();
            collegeEmail = user.getEmail();
            Model_PGDetail object = new Model_PGDetail(YOJ, YOP, semester, SGPA, CGPA, backlog, address,collegeEmail);

            rootRef = FirebaseDatabase.getInstance().getReference("user/"+uid);

            rootRef.child("PG").setValue(object, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                    if(databaseError == null)
                    {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(PostGraduation_Details.this,"Saved",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostGraduation_Details.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(PostGraduation_Details.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
        else
        {
            Toast.makeText(PostGraduation_Details.this,"error",Toast.LENGTH_SHORT).show();
        }



    }

}

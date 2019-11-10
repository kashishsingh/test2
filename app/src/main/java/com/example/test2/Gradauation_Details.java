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

public class Gradauation_Details extends AppCompatActivity {

    private EditText editTextQualification, editTextInstitute, editTextUniversity ;
    private EditText editTextScore, editTextYOPassing, editTextCourseStart, editTextCourseEnd;
    private String qualification, institute, university, score, YOP, CourseStart, CourseEnd;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradauation__details);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarGraduation);

        Button btnSubmit = findViewById(R.id.graduationSubmit);
        editTextInstitute = findViewById(R.id.textUGInstitute);
        editTextQualification = findViewById(R.id.textUGQualification);
        editTextScore = findViewById(R.id.textUGScore);
        editTextUniversity = findViewById(R.id.textUGUniversity);
        editTextYOPassing = findViewById(R.id.textUGYOP);
        editTextCourseStart = findViewById(R.id.textUGCoursestart);
        editTextCourseEnd =findViewById(R.id.textUGCourseend);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                institute = editTextInstitute.getText().toString().trim();
                qualification = editTextQualification.getText().toString().trim();
                score = editTextScore.getText().toString().trim();
                university = editTextUniversity.getText().toString().trim();
                YOP = editTextYOPassing.getText().toString().trim();
                CourseStart = editTextCourseStart.getText().toString().trim();
                CourseEnd = editTextCourseEnd.getText().toString().trim();

                if (TextUtils.isEmpty(qualification)) {
                    editTextQualification.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(institute)) {
                    editTextInstitute.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(university)) {
                    editTextUniversity.setError("Field cannot be blank");
                    return;
                }

                if (TextUtils.isEmpty(YOP)) {
                    editTextYOPassing.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(score)) {
                    editTextScore.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(CourseStart)) {
                    editTextCourseStart.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(CourseEnd)) {
                    editTextCourseEnd.setError("Field cannot be blank");
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
        String uid;
        FirebaseUser user = mAuth.getCurrentUser();
        Model_GraduationDetails object = new Model_GraduationDetails(qualification, institute,
                university, score, YOP, CourseStart, CourseEnd);
        if(user != null)
        {
            uid = user.getUid();
            //Toast.makeText(Gradauation_Details.this,uid,Toast.LENGTH_SHORT).show();
            rootRef = FirebaseDatabase.getInstance().getReference("user/"+uid);
            rootRef.child("Graduation").setValue(object, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                    if(databaseError == null)
                    {
                        Intent intent = new Intent(Gradauation_Details.this,PostGraduation_Details.class);
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(Gradauation_Details.this,"Saved",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }
                    else {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(Gradauation_Details.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
        else
        {
            Toast.makeText(Gradauation_Details.this,"Error",Toast.LENGTH_SHORT).show();
        }


    }

}

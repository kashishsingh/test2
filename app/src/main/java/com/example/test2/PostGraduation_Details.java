package com.example.test2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PostGraduation_Details extends AppCompatActivity {

    private EditText editTextSGPA1, editTextSGPA2, editTextSGPA3, editTextSGPA4, editTextSGPA5, editTextSGPA6;
    private EditText editTextCGPA, editTextBacklog, editTextAddress, editTextYOJ, editTextYOP, editTextSemester;
    private String YOJ, YOP, SGPA1="0", SGPA2="0", SGPA3="0", SGPA4="0", SGPA5="0", SGPA6="0",
            CGPA, backlog, address, semester;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_graduation__details);

        Button btnSubmit;
        auth = FirebaseAuth.getInstance();

        editTextYOJ = findViewById(R.id.etPgYoj);
        editTextYOP = findViewById(R.id.etPgYoc);
        editTextSemester =  findViewById(R.id.etPgCurrentSem);

        editTextSGPA1 = findViewById(R.id.etSem1);
        editTextSGPA2 = findViewById(R.id.etSem2);
        editTextSGPA3 = findViewById(R.id.etSem3);
        editTextSGPA4 = findViewById(R.id.etSem4);
        editTextSGPA5 = findViewById(R.id.etSem5);
        editTextSGPA6 = findViewById(R.id.etSem6);

        editTextCGPA = findViewById(R.id.etCgpa);
        editTextBacklog = findViewById(R.id.etPgBacklog);
        editTextAddress = findViewById(R.id.etCurrentAddress);
        progressBar = findViewById(R.id.progressBarPG);
        btnSubmit = findViewById(R.id.btn_submit);
        editTextCGPA.setKeyListener(null);

        progressBar.setVisibility(ProgressBar.GONE);

        editTextSGPA1.addTextChangedListener(CgpaCalculator);
        editTextSGPA2.addTextChangedListener(CgpaCalculator);
        editTextSGPA3.addTextChangedListener(CgpaCalculator);
        editTextSGPA4.addTextChangedListener(CgpaCalculator);
        editTextSGPA5.addTextChangedListener(CgpaCalculator);
        editTextSGPA6.addTextChangedListener(CgpaCalculator);

        editTextSGPA1.setOnFocusChangeListener(textListener);
        editTextSGPA2.setOnFocusChangeListener(textListener);
        editTextSGPA3.setOnFocusChangeListener(textListener);
        editTextSGPA4.setOnFocusChangeListener(textListener);
        editTextSGPA5.setOnFocusChangeListener(textListener);
        editTextSGPA6.setOnFocusChangeListener(textListener);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YOJ = editTextYOJ.getText().toString().trim();
                YOP = editTextYOP.getText().toString().trim();
                CGPA = editTextCGPA.getText().toString().trim();
                backlog = editTextBacklog.getText().toString().trim();
                address = editTextAddress.getText().toString().trim();
                semester = editTextSemester.getText().toString().trim();

                SGPA1 = editTextSGPA1.getText().toString().trim();
                SGPA2 = editTextSGPA2.getText().toString().trim();
                SGPA3 = editTextSGPA3.getText().toString().trim();
                SGPA4 = editTextSGPA4.getText().toString().trim();
                SGPA5 = editTextSGPA5.getText().toString().trim();
                SGPA6 = editTextSGPA6.getText().toString().trim();


                if (TextUtils.isEmpty(YOJ)) {
                    editTextYOJ.setError("Field cannot be blank");
                    return ;
                }
                if (TextUtils.isEmpty(YOP)) {
                    editTextYOP.setError("Field cannot be blank");
                    return ;
                }

                if(TextUtils.isEmpty(semester))
                {
                    editTextSemester.setError("Field cannot be blank");
                    return;
                }

                if (TextUtils.isEmpty(backlog)) {
                    editTextBacklog.setError("Field cannot be blank");
                    return ;
                }

                /*if (TextUtils.isEmpty(CGPA)) {
                    editTextCGPA.setError("Field cannot be blank");
                    return ;
                }*/

                if (TextUtils.isEmpty(address)) {
                    editTextAddress.setError("Field cannot be blank");
                    return ;
                }


                intoDatabase();
            }
        });

    }


    private TextWatcher CgpaCalculator = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int j, int i1, int i2) {

            double value;
            String answer;
            double s1=0, s2=0, s3=0, s4=0 ,s5=0, s6=0;
            int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0;

            SGPA1 = editTextSGPA1.getText().toString().trim();
            SGPA2 = editTextSGPA2.getText().toString().trim();
            SGPA3 = editTextSGPA3.getText().toString().trim();
            SGPA4 = editTextSGPA4.getText().toString().trim();
            SGPA5 = editTextSGPA5.getText().toString().trim();
            SGPA6 = editTextSGPA6.getText().toString().trim();
            try
            {
                if(!SGPA1.equals(""))
                    s1 = Double.parseDouble(SGPA1);
                if(!SGPA2.equals(""))
                    s2 = Double.parseDouble(SGPA2);
                if(!SGPA3.equals(""))
                    s3 = Double.parseDouble(SGPA3);
                if(!SGPA4.equals(""))
                    s4 = Double.parseDouble(SGPA4);
                if(!SGPA5.equals(""))
                    s5 = Double.parseDouble(SGPA5);
                if(!SGPA6.equals(""))
                    s6 = Double.parseDouble(SGPA6);

                if(s1>0){ a1=1;}
                if(s2>0){a2=1;}
                if(s3>0){a3=1;}
                if(s4>0){a4=1;}
                if(s5>0){a5=1;}
                if(s6>0){a6=1;}


                value = (s1+s2+s3+s4+s5+s6)/(a1+a2+a3+a4+a5+a6);

                if(Double.isNaN(value)) //division by 0 returns NaN
                    value = 0.0;

                answer = String.valueOf(value);
                editTextCGPA.setText(answer);
            }
            catch (Exception e)
            {


            }

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    };


    // pop up message to inform : press 0 for backlog
private View.OnFocusChangeListener textListener = new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View view, boolean b) {

        if(b)
        {
            Snackbar.make(findViewById(R.id.top_coordinator), "Enter 0 if you have a backlog",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
};



    private void intoDatabase()
    {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        DatabaseReference rootRef;
        String uid;
        FirebaseUser user = auth.getCurrentUser();

        if(user != null)
        {
            uid = user.getUid();
           //collegeEmail = user.getEmail();
            Model_PGDetail object = new Model_PGDetail(YOJ, YOP, CGPA, semester, backlog, address,SGPA1,SGPA2,SGPA3,SGPA4, SGPA5,SGPA6);

            rootRef = FirebaseDatabase.getInstance().getReference("user/"+uid);

            rootRef.child("PG").setValue(object, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                    if(databaseError == null)
                    {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(PostGraduation_Details.this,"Saved",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostGraduation_Details.this,placement.class);
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
            Toast.makeText(PostGraduation_Details.this,"User not Loggged in",Toast.LENGTH_SHORT).show();
        }



    }

}

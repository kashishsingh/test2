package com.example.test2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tenth_Details extends AppCompatActivity {

    private EditText editTextInstitute10, editTextUniversity10, editTextYOP10, editTextPercentage10;
    private EditText editTextInstitute12, editTextUniversity12, editTextYOP12, editTextPercentage12;
    private RadioGroup radioGroup;
    private FirebaseAuth mAuth;
    Intent intent;
    private ProgressBar progressBar;

    String institution10, board10, yop10, percentage10;
    String institution12, board12,yop12, percentage12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenth__details);

        Button buttonSubmit;
        progressBar = findViewById(R.id.progressBar10);

        editTextInstitute10 = findViewById(R.id.textInstitution10);
        editTextUniversity10 = findViewById(R.id.textBoard10);
        editTextYOP10 = findViewById(R.id.textYOP10 );
        editTextPercentage10 = findViewById(R.id.textPercentage10 );

        editTextInstitute12 = findViewById(R.id.textInstitution12 );
        editTextUniversity12 = findViewById(R.id.textBoard12);
        editTextYOP12 = findViewById(R.id.textYOP12 );
        editTextPercentage12 = findViewById(R.id.textPercentage12);

        radioGroup = findViewById(R.id.radioGroup10);
        buttonSubmit = findViewById(R.id.button10);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                institution10 = editTextInstitute10.getText().toString().trim();
                board10 = editTextUniversity10.getText().toString().trim();
                yop10 = editTextYOP10.getText().toString().trim();
                percentage10 = editTextPercentage10.getText().toString().trim();
                institution12 = editTextInstitute12.getText().toString().trim();
                board12 = editTextUniversity12.getText().toString().trim();
                yop12 = editTextYOP12.getText().toString().trim();
                percentage12 = editTextPercentage12.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();

                if (TextUtils.isEmpty(institution10)) {
                    editTextInstitute10.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(board10)) {
                    editTextUniversity10.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(yop10)) {
                    editTextYOP10.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(percentage10)) {
                    editTextPercentage10.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(institution12)) {
                    editTextInstitute12.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(board12)) {
                    editTextUniversity12.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(yop12)) {
                    editTextYOP12.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(percentage12)) {
                    editTextPercentage12.setError("Field cannot be blank");
                    return;
                }

                intoDatabase();
            }
        });
    }

    private void intoDatabase()
    {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        intent = new Intent(Tenth_Details.this,Gradauation_Details.class);
        DatabaseReference rootRef;
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioID);
        String stream = radioButton.getText().toString();

        String uid="";
        FirebaseUser user = mAuth.getCurrentUser();
        Model_TenthTwelveDetails object = new Model_TenthTwelveDetails(institution10, board10,
                yop10, percentage10, stream, institution12, board12,yop12, percentage12);

        if(user != null)
        {
            uid = user.getUid();
            rootRef = FirebaseDatabase.getInstance().getReference(uid);
            rootRef.child("tenth").setValue(object, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if(databaseError ==  null)
                    {
                        //success
                        progressBar.setVisibility(ProgressBar.GONE);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Tenth_Details.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


    }

}

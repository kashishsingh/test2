package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText emailId;
    private EditText paswd;
    private EditText conpaswd;
    private Button btnSignUp;
    private TextView tvSignIn;
    private ProgressBar progressbar;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        paswd = findViewById(R.id.editText2);
        conpaswd = findViewById(R.id.editText3);
        btnSignUp = findViewById(R.id.button2);
        tvSignIn = findViewById(R.id.textView);
        progressbar = findViewById(R.id.progressBar);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailId.getText().toString().trim();
                String password = paswd.getText().toString().trim();
                String confirmPassword = conpaswd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailId.setError("Field cannot be blank");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    paswd.setError("Field cannot be blank");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    conpaswd.setError("Field cannot be blank");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(MainActivity.this, "Please Make sure Both password are same", Toast.LENGTH_LONG).show();
                    return;
                }

                signUp(email, password);

            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void signUp(String email, String password)
    {
        progressbar.setVisibility(ProgressBar.VISIBLE);
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    mFirebaseAuth.getCurrentUser().sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "User created successfully, Please verify your E-mail", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressbar.setVisibility(ProgressBar.GONE);
                if (e instanceof FirebaseAuthUserCollisionException )
                {
                    Toast.makeText(MainActivity.this,"Email address already in use",Toast.LENGTH_LONG).show();
                }
                else if (e instanceof FirebaseAuthWeakPasswordException )
                {
                    Toast.makeText(MainActivity.this,"Please enter a strong password",Toast.LENGTH_LONG).show();
                }
                else if (e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Toast.makeText(MainActivity.this,"Please Enter Email properly",Toast.LENGTH_LONG).show();
                    emailId.requestFocus();
                }
                else
                {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}


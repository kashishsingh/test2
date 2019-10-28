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
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private ProgressBar progressbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView tvSignIn;
        Button btnSignUp;
        mFirebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editText);
        editTextPassword = findViewById(R.id.editText2);
        editTextConfirmPassword = findViewById(R.id.editText3);
        btnSignUp = findViewById(R.id.button2);
        tvSignIn = findViewById(R.id.textView);
        progressbar = findViewById(R.id.progressBar);


        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Field cannot be blank");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Field cannot be blank");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    editTextConfirmPassword.setError("Field cannot be blank");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Please Make sure Both password are same", Toast.LENGTH_LONG).show();
                    return;
                }

                signUp(email, password);

            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(mFirebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private void signUp(String email, String password)
    {
        progressbar.setVisibility(ProgressBar.VISIBLE);
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if (task.isSuccessful())
                {
                    user = mFirebaseAuth.getCurrentUser();
                    if(user!= null)
                    {
                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "User created successfully, Please verify your E-mail", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        // user does not exist. not gonna happen
                        Toast.makeText(RegisterActivity.this,"Problem",Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressbar.setVisibility(ProgressBar.GONE);
                if (e instanceof FirebaseAuthUserCollisionException )
                {
                    Toast.makeText(RegisterActivity.this,"Email address already in use",Toast.LENGTH_LONG).show();
                }
                else if (e instanceof FirebaseAuthWeakPasswordException )
                {
                    Toast.makeText(RegisterActivity.this,"Please enter a strong password",Toast.LENGTH_LONG).show();
                }
                else if (e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Toast.makeText(RegisterActivity.this,"Please Enter Email properly",Toast.LENGTH_LONG).show();
                    editTextEmail.requestFocus();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}



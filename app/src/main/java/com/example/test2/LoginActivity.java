package com.example.test2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;


public class LoginActivity extends AppCompatActivity implements Reset_Password.ResetPasswordListener {
    private EditText emailId, pass;
    private Button btnSignIn;
    private TextView tvSignUp, fPassword;
    private ProgressBar progressbar;
    private FirebaseAuth auth;
    //private DatabaseReference refRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);
        btnSignIn = findViewById(R.id.button2);
        tvSignUp = findViewById(R.id.textView);
        fPassword = findViewById(R.id.textView_FPassword);
        progressbar = findViewById(R.id.progressBar2);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailId.getText().toString();
                final String password = pass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
                    emailId.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_LONG).show();
                    pass.requestFocus();
                    return;
                }
                SigningIn(email,password);

            }
        });


        fPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();

            }
        });
    }

    private void openDialog()
    {
        Reset_Password rs = new Reset_Password();
        rs.show(getSupportFragmentManager(),"kashish");
    }


    private void SigningIn (final String email, String password)
        {
            progressbar.setVisibility(ProgressBar.VISIBLE);

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressbar.setVisibility(ProgressBar.GONE);
                            if (task.isSuccessful())
                                {
                                    String id = IntoDataBase(email);
                                if (auth.getCurrentUser().isEmailVerified())
                                {
                                    //IntoDataBase(mail);//push details to Real time database
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("id",id);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                    {
                                    Toast.makeText(LoginActivity.this, "Please verify your E-mail id", Toast.LENGTH_LONG).show();
                                    }

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressbar.setVisibility(ProgressBar.GONE);
                    if (e instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        Toast.makeText(LoginActivity.this,"Either Email or Password don't match",Toast.LENGTH_LONG).show();
                    }

                    else
                    {
                        Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }

                }
            });
        }


    private String IntoDataBase(String mail)
    {
        String uid="";
        //String umail="";
        FirebaseUser user = auth.getCurrentUser();
        if(user!= null)
        {
            uid = user.getUid();
            //umail = user.getEmail();
        }
        //refRoot = FirebaseDatabase.getInstance().getReference();
        //refRoot.child(uid).child("Email").setValue(umail);
        return uid;
        //refRoot.child(uid).child("Name").setValue("ram");

    }

    @Override
    public void changePassword(String email) //email from reset_Password.java
    {

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Link Sent to your Email Id",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}



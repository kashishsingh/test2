package com.example.test2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private Button btnLogout;
    private Button insert;
    private EditText name, age, USN;
    private DatabaseReference rootref;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogout = findViewById(R.id.logout);
        insert = findViewById(R.id.insert);
        name = findViewById(R.id.editName);
        age = findViewById(R.id.editAge);
        USN= findViewById(R.id.editUSN);


        Intent intent = getIntent(); // getting uid from login activity
        final String id = intent.getStringExtra("id");
        //Toast.makeText(HomeActivity.this,id,Toast.LENGTH_LONG).show();

        insert.setOnClickListener(new View.OnClickListener() { //insert into database
            @Override
            public void onClick(View view) {

                String namee = name.getText().toString();
                String agee = age.getText().toString();
                String USNN = USN.getText().toString();

                rootref = FirebaseDatabase.getInstance().getReference();
                rootref.child(id).child("Name").setValue(namee);
                rootref.child(id).child("Age").setValue(agee);
                rootref.child(id).child("USN").setValue(USNN);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                intToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intToMain);
            }
        });


    }
}


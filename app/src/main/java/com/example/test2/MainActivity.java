package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{



Button view;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button personal, tenth, twelve, bachelor, pg, logout;

        personal = findViewById(R.id.buttonPersonal);
        tenth = findViewById(R.id.buttonSchool);
        twelve = findViewById(R.id.buttonTwelve);
        bachelor = findViewById(R.id.buttonBachelor);
        pg = findViewById(R.id.buttonPG);
        logout = findViewById(R.id.buttonLogout);
        view = findViewById(R.id.buttonView);


        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Basic_Detail.class);
                startActivity(intent);
            }
        });

        tenth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Tenth_Details.class);
                startActivity(intent);
            }
        });

        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Tenth_Details.class);
                startActivity(intent);
            }
        });

        bachelor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Gradauation_Details.class);
                startActivity(intent);
            }
        });

        pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,PostGraduation_Details.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if(user != null)
                {
                    auth.signOut();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    Toast.makeText(MainActivity.this,"Signed Out",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,viewActivity.class);
                startActivity(intent);
            }
        });

    }
}


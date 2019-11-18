package com.example.test2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class DetailedViewActivity extends AppCompatActivity {

    private DatabaseReference refBasic, refTenth, refGraduation,refPG;
    private String id;
    private TextView textViewName,textViewUsn, textViewPersonalMail, textViewCollegeMail, textViewFather,
                        textViewGender, textViewDate;
    private String name, usn, personalEMail, collegeEmail, profilePic, father,gender,date;
    private ImageView imageVIewProfilePic;

    private TextView textView10Institute, textView10University, textView10Yop, textView10Percent,
            textView12Institute, textView12University, textView12Yop,textView12Percent, textView12Stream;
    private String institute10, university10, yop10, percent10, institute12, university12, yop12, percent12, stream12;

    private TextView textViewGradQualification, textViewGradInstitute, textViewGradUniversity, textViewGradYop,
    textViewGradYoj, textViewGradScore;

    private String gradQualification, gradInstitute, gradUniversity, gradYop, gradYoj, gradScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting user id from viewActivity.java
        Intent intent = getIntent();
        id = intent.getStringExtra("id");



        refBasic = FirebaseDatabase.getInstance().getReference("user/"+id+"/Basic");//Database reference
        refTenth = FirebaseDatabase.getInstance().getReference("user/"+id+"/tenth");
        refGraduation = FirebaseDatabase.getInstance().getReference("user/"+id+"/Graduation");
        refPG = FirebaseDatabase.getInstance().getReference("user/"+id+"/PG");

        displayBasic();
        displayTenth();
        displayGraduation();
        displayPG();


    }


    private void displayBasic()
    {
        textViewName = findViewById(R.id.tvName);
        textViewUsn = findViewById(R.id.tvUsn);
        textViewCollegeMail = findViewById(R.id.tvCollegeMail);
        textViewPersonalMail = findViewById(R.id.tvPersonalMail);
        imageVIewProfilePic = findViewById(R.id.imageView);
        textViewFather = findViewById(R.id.tvFather);
        textViewGender= findViewById(R.id.tvGender);
        textViewDate = findViewById(R.id.tvDate);

        refBasic.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {

                    //Toast.makeText(DetailedViewActivity.this,"id is "+id,Toast.LENGTH_SHORT).show();
                    Model_basicDetails details = dataSnapshot.getValue(Model_basicDetails.class);
                    if(details!=null)
                    {
                        name = details.getName();
                        usn = details.getUsn();
                        personalEMail = details.getEmail();
                        collegeEmail = details.getCollegeEmail();
                        profilePic = details.getmImageUrl();
                        father = details.getFather();
                        gender = details.getSelectedRadio();
                        date = details.getDate();

                        textViewName.setText(name);
                        textViewUsn.setText(usn);
                        textViewPersonalMail.setText(personalEMail);
                        textViewCollegeMail.setText(collegeEmail);
                        Picasso.with(DetailedViewActivity.this).load(profilePic)
                                .transform(new CropCircleTransformation())
                                .into(imageVIewProfilePic);
                        textViewFather.setText(father);
                        textViewGender.setText(gender);
                        textViewDate.setText(date);
                    }
                    else
                    {
                        //test
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void displayTenth()
    {
        textView10Institute = findViewById(R.id.tv10Institute);
        textView10University = findViewById(R.id.tv10University);
        textView10Yop = findViewById(R.id.tvYop10);
        textView10Percent = findViewById(R.id.tv10percent);

        textView12Institute = findViewById(R.id.tv12Institute);
        textView12University = findViewById(R.id.tv12University);
        textView12Percent = findViewById(R.id.tv12Percentage);
        textView12Stream = findViewById(R.id.tv12Stream);
        textView12Yop = findViewById(R.id.tv12Yop);

        refTenth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {

                    //Toast.makeText(DetailedViewActivity.this,id,Toast.LENGTH_SHORT).show();
                    Model_TenthTwelveDetails obj = dataSnapshot.getValue(Model_TenthTwelveDetails.class);
                    if(obj!=null)
                    {
                        university10 = obj.getBoard10();
                        yop10 = obj.getYop10();
                        percent10 = obj.getPercentage10();
                        institute10 = obj.getInstitution10();

                        institute12 = obj.getInstitution12();
                        university12 = obj.getBoard12();
                        yop12 = obj.getYop12();
                        percent12 = obj.getPercentage12();
                        stream12 = obj.getStream();

                        textView10Institute.setText(institute10);
                        textView10University.setText(university10);
                        textView10Percent.setText(percent10);
                        textView10Yop.setText(yop10);

                        textView12Institute.setText(institute12);
                        textView12University.setText(university12);
                        textView12Stream.setText(stream12);
                        textView12Yop.setText(yop12);
                        textView12Percent.setText(percent12);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void displayGraduation()
    {

        textViewGradInstitute = findViewById(R.id.tvGradInstitute);
        textViewGradUniversity = findViewById(R.id.tvGradUniversity);
        textViewGradYoj = findViewById(R.id.tvGradYoj);
        textViewGradYop = findViewById(R.id.tvGradYoe);
        textViewGradScore = findViewById(R.id.tvGradScore);
        textViewGradQualification = findViewById(R.id.tvGradQualification);

        refGraduation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Model_GraduationDetails mgd = dataSnapshot.getValue(Model_GraduationDetails.class);
                    if(mgd!= null)
                    {

                        gradInstitute = mgd.getInstitute();
                        gradQualification = mgd.getQualification();
                        gradUniversity = mgd.getUniversity();
                        gradScore = mgd.getScore();
                        gradYop = mgd.getCourseStart();
                        gradYoj = mgd.getCourseEnd();

                        textViewGradInstitute.setText(gradInstitute);
                        textViewGradUniversity.setText(gradUniversity);
                        textViewGradQualification.setText(gradQualification);
                        textViewGradScore.setText(gradScore);
                        textViewGradYoj.setText(gradYoj);
                        textViewGradYop.setText(gradYop);
                        
                    }
                    else
                    {
                        Toast.makeText(DetailedViewActivity.this,"MGS empmty",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

                Toast.makeText(DetailedViewActivity.this,"problem fetching Graduation data",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private  void displayPG()
    {

    }
}

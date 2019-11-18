package com.example.test2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class DetailedViewActivity extends AppCompatActivity {

    private DatabaseReference refBasic, refTenth, refGraduation,refPG;

    private TextView textViewName,textViewUsn, textViewPersonalMail, textViewCollegeMail, textViewFather,
                        textViewGender, textViewDate;
    private String  profilePic;
    private ImageView imageVIewProfilePic;

    private TextView textView10Institute, textView10University, textView10Yop, textView10Percent,
            textView12Institute, textView12University, textView12Yop,textView12Percent, textView12Stream;


    private TextView textViewGradQualification, textViewGradInstitute, textViewGradUniversity, textViewGradYop,
    textViewGradYoj, textViewGradScore;

    private TextView textViewPGSem1, textViewPGSem2,textViewPGSem3,textViewPGSem4,textViewPGSem5,
            textViewPGSem6,textViewPGAddress, textViewPGBacklog, textViewPGCGPA,
            textViewPGYoj, textViewPGYoe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting user id from viewActivity.java
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");



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

                        profilePic = details.getmImageUrl();

                        textViewName.setText(details.getName());
                        textViewUsn.setText(details.getUsn());
                        textViewPersonalMail.setText( details.getEmail());
                        textViewCollegeMail.setText(details.getCollegeEmail());
                        Picasso.with(DetailedViewActivity.this).load(profilePic)
                                .transform(new CropCircleTransformation())
                                .into(imageVIewProfilePic);
                        textViewFather.setText(details.getFather());
                        textViewGender.setText(details.getSelectedRadio());
                        textViewDate.setText(details.getDate());
                    }
                    else
                    {
                        Toast.makeText(DetailedViewActivity.this,"Basic Details empty",Toast.LENGTH_SHORT).show();
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

                        textView10Institute.setText(obj.getInstitution10());
                        textView10University.setText(obj.getBoard10());
                        textView10Percent.setText(obj.getPercentage10());
                        textView10Yop.setText(obj.getYop10());

                        textView12Institute.setText(obj.getInstitution12());
                        textView12University.setText( obj.getBoard12());
                        textView12Stream.setText(obj.getStream());
                        textView12Yop.setText( obj.getYop12());
                        textView12Percent.setText(obj.getPercentage12());
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

                        textViewGradInstitute.setText(mgd.getInstitute());
                        textViewGradUniversity.setText(mgd.getUniversity());
                        textViewGradQualification.setText(mgd.getQualification());
                        textViewGradScore.setText(mgd.getScore());
                        textViewGradYoj.setText(mgd.getCourseStart());
                        textViewGradYop.setText(mgd.getCourseEnd());
                        
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

        textViewPGSem1 = findViewById(R.id.tvSgpaSem1);
        textViewPGSem2 = findViewById(R.id.tvSgpaSem2);
        textViewPGSem3 = findViewById(R.id.tvSgpaSem3);
        textViewPGSem4 = findViewById(R.id.tvSgpaSem4);
        textViewPGSem5 = findViewById(R.id.tvSgpaSem5);
        textViewPGSem6 = findViewById(R.id.tvSgpaSem6);

        textViewPGAddress = findViewById(R.id.tvAddress);
        textViewPGBacklog = findViewById(R.id.tvPGBacklog);
        textViewPGYoj = findViewById(R.id.tvPGJoining);
        textViewPGYoe = findViewById(R.id.tvPgEnd);
        textViewPGCGPA = findViewById(R.id.tvPGCGPA);

        refPG.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Model_PGDetail mpg = dataSnapshot.getValue(Model_PGDetail.class);
                    if(mpg!= null)
                    {

                        textViewPGSem1.setText(mpg.getSGPA1());
                        textViewPGSem2.setText(mpg.getSGPA2());
                        textViewPGSem3.setText(mpg.getSGPA3());
                        textViewPGSem4.setText(mpg.getSGPA4());
                        textViewPGSem5.setText(mpg.getSGPA5());

                        textViewPGSem6.setText(mpg.getSGPA6());
                        textViewPGAddress.setText(mpg.getAddress());
                        textViewPGBacklog.setText(mpg.getBacklog());
                        textViewPGCGPA.setText(mpg.getCGPA());
                        textViewPGYoj.setText(mpg.getYOJ());
                        textViewPGYoe.setText(mpg.getYOP());


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
}

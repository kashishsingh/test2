package com.example.test2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class placement extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private ProgressBar mProgressBar;
    private Button placeButton;
    private String placeCompany, placePackage, type;
    private Spinner spinner;
    private EditText editTextplaceCompany, editTextPlacePackage;
    private FirebaseAuth pAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        pAuth= FirebaseAuth.getInstance();

        final RadioGroup placeRadioGroup;
        //RadioButton radioClickedID;
        placeRadioGroup = findViewById(R.id.placeRadioGroup);
        mProgressBar = findViewById(R.id.placeProgressBar);
        mProgressBar.setVisibility(ProgressBar.GONE);
        int radioPlaceId = placeRadioGroup.getCheckedRadioButtonId();
        //radioClickedID = findViewById(radioPlaceId);
        spinner = findViewById(R.id.placeSpinner);
        placeButton = findViewById(R.id.placeSubmit);

        editTextplaceCompany = findViewById(R.id.placeCompany);
        editTextPlacePackage = findViewById(R.id.placePackage);

        initializeSpinner();
        spinner.setOnItemSelectedListener(this);

        editTextplaceCompany.setEnabled(false);
        editTextplaceCompany.setFocusableInTouchMode(false);
        editTextPlacePackage.setEnabled(false);
        editTextPlacePackage.setFocusableInTouchMode(false);
        placeButton.setEnabled(false);
        spinner.setEnabled(false);
        placeButton.setBackgroundResource(0);


        placeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedI) {
                if (checkedI == R.id.radioNo) {

                    editTextplaceCompany.setEnabled(false);
                    editTextplaceCompany.setFocusableInTouchMode(false);
                    editTextPlacePackage.setEnabled(false);
                    editTextPlacePackage.setFocusableInTouchMode(false);
                    placeButton.setEnabled(false);
                    spinner.setEnabled(false);
                    placeButton.setBackgroundResource(0);

                } else if (checkedI == R.id.radioYes) {
                    editTextplaceCompany.setEnabled(true);
                    editTextplaceCompany.setFocusableInTouchMode(true);
                    editTextPlacePackage.setEnabled(true);
                    editTextPlacePackage.setFocusableInTouchMode(true);
                    placeButton.setEnabled(true);
                    spinner.setEnabled(true);
                    placeButton.setBackgroundResource(R.drawable.rounded);
                }

            }
        });


        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                placeCompany = editTextplaceCompany.getText().toString().trim();
                placePackage = editTextPlacePackage.getText().toString().trim();


                if (TextUtils.isEmpty(placeCompany)) {
                    editTextplaceCompany.setError("Field cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(placePackage)) {
                    editTextPlacePackage.setError("Field cannot be blank");
                    return;
                }

                placeIntoDatabase();

            }
        });
    }


    private void initializeSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.placement_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
    {

        type = (String) adapterView.getItemAtPosition(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    private void placeIntoDatabase()
    {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        DatabaseReference rootRef;

        String uid;
        FirebaseUser user = pAuth.getCurrentUser();
        if(user != null)
        {
            uid = user.getUid();
            Model_PlacementDetails placeObj = new Model_PlacementDetails(placeCompany, type ,placePackage) ;
            rootRef = FirebaseDatabase.getInstance().getReference("user/"+uid);
            rootRef.child("Placement").setValue(placeObj, new DatabaseReference.CompletionListener()
            {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                {
                    if (databaseError == null)
                    {
                       mProgressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(placement.this,"Saved",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(placement.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(placement.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(placement.this,"User not Loggged in",Toast.LENGTH_SHORT).show();
        }

    }
}

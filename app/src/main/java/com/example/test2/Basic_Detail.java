package com.example.test2;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import static com.squareup.picasso.Picasso.with;

public class Basic_Detail extends AppCompatActivity {

    private ImageView userPhoto;
    private EditText editTextEmail, editTextName, editTextUSN, editTextFather, editTextDate, editTextPhone;
    private String date, id;
    private String name, USN, email, phone, father, gender;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private StorageReference mStorageRef, fileReference;
    private ProgressBar mProgressBar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference rootRef;
   // private StorageTask mUploadTask;//to stope multipe upload
    private UploadTask uploadTask;
    private FirebaseAuth mAuth;

    Uri imageUri;
    private static final int Pick_Image =100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic__detail_1);
        Button submit;

        mAuth = FirebaseAuth.getInstance();
        userPhoto = findViewById(R.id.imageID);
        editTextEmail = findViewById(R.id.textEmail);
        editTextName = findViewById(R.id.textName);
        editTextUSN = findViewById(R.id.textUSN);
        editTextPhone = findViewById(R.id.textPhone);
        editTextFather =findViewById(R.id.textFather);
        editTextDate = findViewById(R.id.textDate);
        submit = findViewById(R.id.buttonSubmit);
        radioGroup = findViewById(R.id.radioGroup);
        mProgressBar = findViewById(R.id.progressBar_BasicDetail);
        mStorageRef = FirebaseStorage.getInstance().getReference("userPic");


        editTextDate.setFocusable(false);

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(uploadTask !=null && uploadTask.isInProgress())
                {

                    Toast.makeText(Basic_Detail.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    name = editTextName.getText().toString().trim();
                    USN = editTextUSN .getText().toString().trim();
                    email = editTextEmail .getText().toString().trim();
                    phone = editTextPhone.getText().toString().trim();
                    father = editTextFather .getText().toString().trim();
                    String bDate = editTextDate.getText().toString().trim();
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    gender = radioButton.getText().toString();

                    if (TextUtils.isEmpty(USN)) {
                        editTextUSN.setError("Field cannot be blank");
                        return;
                    }
                    if (TextUtils.isEmpty(name)) {
                        editTextName.setError("Field cannot be blank");
                        return;
                    }

                    if (!TextUtils.isEmpty(email))
                    {
                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                        {
                            editTextEmail.setError("Pattern Missmatch");
                            return;
                        }
                    }
                    else
                    {
                        editTextEmail.setError("Field cannot be blank");
                        return;
                    }

                    if (!TextUtils.isEmpty(phone))
                    {
                        if(phone.length()!=10)
                        {
                            editTextPhone.setError("Please check Phone number");
                            return;
                        }
                    }
                    else
                    {
                        editTextPhone.setError("Field cannot be blank");
                        return;
                    }


                    if (TextUtils.isEmpty(father)) {
                        editTextFather.setError("Field cannot be blank");
                        return;
                    }


                    if(TextUtils.isEmpty(bDate)){
                        editTextDate.setError("Field cannot be blank");
                        return;
                    }

                    intoDatabase();
                }
            }
        });


        editTextDate.setOnClickListener(new View.OnClickListener() { //date picker
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Basic_Detail.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                date = month + "/" + day + "/" + year;
                editTextDate.setText(date);

            }
        };

    }//onCreate ending


        private void intoDatabase()
        {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);

            FirebaseUser user = mAuth.getCurrentUser();
            if(user != null)
            {
                id = user.getUid();
            }

            if(imageUri!=null)
            {
                fileReference = mStorageRef.child(id + "." + getPhotoExtension(imageUri));
                //converting image from uri to byte Array and compressing it.
                userPhoto.setDrawingCacheEnabled(true);
                userPhoto.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) userPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                uploadTask = fileReference.putBytes(data); //uploading

                Task<Uri> urlTask = uploadTask.
                        continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                                         {
                                             @Override
                                             public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                 if (!task.isSuccessful()) {
                                                     throw task.getException();
                                                 }
                                                 return fileReference.getDownloadUrl();

                                             }
                                         }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            Uri downloadUri = task.getResult();
                            String photoUrl = downloadUri.toString();
                            Model_basicDetails object = new Model_basicDetails(name, USN, email, phone, father, date, gender, photoUrl,id );
                            rootRef = FirebaseDatabase.getInstance().getReference("user/"+id);
                            rootRef.child("Basic").setValue(object);
                            mProgressBar.setVisibility(ProgressBar.GONE);
                            Intent tenth = new Intent(Basic_Detail.this, Tenth_Details.class);
                            Toast.makeText(Basic_Detail.this,"Saved",Toast.LENGTH_SHORT).show();
                            startActivity(tenth);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(Basic_Detail.this,"Error",Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    }
                });


                /*uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Basic_Detail.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        getPhotoDownloadUrl();


                        Model_basicDetails object = new Model_basicDetails(name, USN, email, phone, father, date, gender, photoUrl );
                        rootRef = FirebaseDatabase.getInstance().getReference(id);
                        rootRef.child("Basic").setValue(object);
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        Intent tenth = new Intent(Basic_Detail.this, Tenth_Details.class);
                        Toast.makeText(Basic_Detail.this,"Saved",Toast.LENGTH_SHORT).show();
                        startActivity(tenth);
                        finish();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                        mProgressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                });*/

            }
                /*StorageReference fileReference = mStorageRef.child(id + "." + getPhotoExtension(imageUri));
                mUploadTask = fileReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //upload is successfull
                        String photoUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        Model_basicDetails object = new Model_basicDetails(name, USN, email, phone, father, date, gender, photoUrl );
                        rootRef = FirebaseDatabase.getInstance().getReference(id);
                        rootRef.child("Basic").setValue(object);
                        //Toast.makeText(Basic_Detail.this,"Success",Toast.LENGTH_LONG).show();
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        Intent tenth = new Intent(Basic_Detail.this, Tenth_Details.class);
                        startActivity(tenth);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Basic_Detail.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                        mProgressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                });
            } */
            else
            {
                Toast.makeText(Basic_Detail.this,"No Photo Selected", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(ProgressBar.GONE);
            }


    }


    private void selectPhoto()//to select photo from gallery
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,Pick_Image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == Pick_Image && data!=null && data.getData()!=null )
        {
            imageUri = data.getData();
            //userPhoto.setImageURI(imageUri);
            with(this)
                    .load(imageUri)
                    .fit()
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(18,8))
                    .into(userPhoto);
        }
    }

    private String getPhotoExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}

package com.lottery.sambad.admin.ui.reports;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lottery.sambad.admin.R;
import com.lottery.sambad.admin.ui.new_version.NewVersionActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class UpdatePaidInfoActivity extends AppCompatActivity {

    private Button GetUpdate;
    private ImageView imgFirebase;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private EditText videoLinkText,PhoneOneText,PhoneTwoText,PhoneThreeText,whatsAppnumberText;
    //Firebase

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_paid_info);

        GetUpdate = (Button) findViewById(R.id.GetUpdate);
        imgFirebase = (ImageView) findViewById(R.id.UploadViewBtn);

        videoLinkText = findViewById(R.id.videoLinkText);
        whatsAppnumberText = findViewById(R.id.whatsAppnumberText);
        PhoneOneText = findViewById(R.id.PhoneOneText);
        PhoneTwoText = findViewById(R.id.PhoneTwoText);
        PhoneThreeText = findViewById(R.id.PhoneThreeText);

        //Firebase init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imgFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        GetUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PhoneOneText.getText().toString().isEmpty() && !PhoneTwoText.getText().toString().isEmpty() && !PhoneThreeText.getText().toString().isEmpty() && !whatsAppnumberText.getText().toString().isEmpty() && !videoLinkText.getText().toString().isEmpty()){
                    uploadImage();
                }else {
                    Toast.makeText(UpdatePaidInfoActivity.this, "please fill up form", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void uploadImage() {
        if(filePath != null){
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Uploading....");
            progress.show();

            StorageReference ref= storageReference.child("basic_image/"+ UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Test");
                    // ref.child("hello").setValue();




                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            //Toast.makeText(NewVersionActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            DatabaseReference refa = FirebaseDatabase.getInstance().getReference("PremiumRequInfo");
                            refa.child("whatsapp_number").setValue(whatsAppnumberText.getText().toString());
                            refa.child("phone_one").setValue(PhoneOneText.getText().toString());
                            refa.child("phone_two").setValue(PhoneTwoText.getText().toString());
                            refa.child("phone_three").setValue(PhoneThreeText.getText().toString());
                            refa.child("video_link").setValue(videoLinkText.getText().toString());
                            refa.child("video_thumbail").setValue(task.getResult().toString());
                            progress.dismiss();
                            Toast.makeText(UpdatePaidInfoActivity.this, "Version successfully updated", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress.dismiss();
                    Toast.makeText(UpdatePaidInfoActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progres_time = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progress.setMessage("Uploaded "+(int)progres_time+" %");
                }
            });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK &&
                data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgFirebase.setImageBitmap(bitmap);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

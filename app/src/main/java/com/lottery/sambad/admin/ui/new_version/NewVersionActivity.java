package com.lottery.sambad.admin.ui.new_version;

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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class NewVersionActivity extends AppCompatActivity {

    private Button GetUpdate;
    private ImageView imgFirebase;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    EditText versionCd,DescriptionT,videoLinkText;
    //Firebase

    FirebaseStorage storage;
    StorageReference storageReference;
    String UpdatePosition = "o";
    Switch updatepositionSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_version);

        GetUpdate = (Button) findViewById(R.id.GetUpdate);
        imgFirebase = (ImageView) findViewById(R.id.UploadViewBtn);
        versionCd = findViewById(R.id.versionCodeText);
        DescriptionT = findViewById(R.id.descriptionText);
        videoLinkText = findViewById(R.id.videoLinkText);
        updatepositionSwitch = findViewById(R.id.updatepositionSwitch);

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
               if (!versionCd.getText().toString().isEmpty() && !DescriptionT.getText().toString().isEmpty() && !videoLinkText.getText().toString().isEmpty()){
                   uploadImage();
               }else {
                   Toast.makeText(NewVersionActivity.this, "please fill up form", Toast.LENGTH_SHORT).show();
               }
            }
        });

        updatepositionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    UpdatePosition = "m";
                }else {
                    UpdatePosition = "o";
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
                            DatabaseReference refa = FirebaseDatabase.getInstance().getReference("AppInfo");
                            refa.child("update_position").setValue(UpdatePosition);
                            refa.child("video_thumbail").setValue(task.getResult().toString());
                            refa.child("update_version").setValue(versionCd.getText().toString());
                            refa.child("video_url").setValue(videoLinkText.getText().toString());
                            refa.child("update_description").setValue(DescriptionT.getText().toString());
                            progress.dismiss();
                            Toast.makeText(NewVersionActivity.this, "Version successfully updated", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress.dismiss();
                    Toast.makeText(NewVersionActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

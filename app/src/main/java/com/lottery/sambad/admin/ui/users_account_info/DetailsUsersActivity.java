package com.lottery.sambad.admin.ui.users_account_info;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.lottery.sambad.admin.R;
import com.lottery.sambad.admin.database.ApiInterface;
import com.lottery.sambad.admin.database.MyApi;
import com.lottery.sambad.admin.database.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsUsersActivity extends AppCompatActivity {

    private TextView mPhone,mRegidate,mUsertype,mLicenseExpire,mlicenseRegister,mLicenseNumber;
    private Switch mAcPosition,mAcLogged;
    String UserID;
    Button DLaddLicense,DLcancelLicense;
    LinearLayout DLlicenseLayout;
    private ApiInterface apiInterface;
    String LICENSE_STATUS = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_users);

        apiInterface= RetrofitClient.getApiClient().create(ApiInterface.class);

        mPhone = findViewById(R.id.DLpbone);
        mRegidate = findViewById(R.id.DLregiDate);
        mUsertype = findViewById(R.id.DLuserType);
        mAcPosition = findViewById(R.id.DLacPositionSwitch);
        mAcLogged = findViewById(R.id.DLacLoggedSwitch);
        DLaddLicense = findViewById(R.id.DLaddLicense);
        DLlicenseLayout = findViewById(R.id.DLlicenseLayout);

        mLicenseExpire = findViewById(R.id.DLlicenseExpireDate);
        mlicenseRegister = findViewById(R.id.DLlicenseRegisterDate);
        mLicenseNumber = findViewById(R.id.DLlicenseNumber);

        DLcancelLicense = findViewById(R.id.DLCancelLicense);

        Intent intent = getIntent();
        mPhone.setText(intent.getStringExtra("Phone"));
        UserID = intent.getStringExtra("Id");
        mRegidate.setText(intent.getStringExtra("registrationDate"));
        LICENSE_STATUS = intent.getStringExtra("paid_license");
        if (LICENSE_STATUS.equals("1")){
            mUsertype.setText("Premium");
            LoadLicense();
            DLlicenseLayout.setVisibility(View.VISIBLE);
            DLcancelLicense.setVisibility(View.VISIBLE);
        }else {
            mUsertype.setText("Standerd");
            DLlicenseLayout.setVisibility(View.GONE);
            DLaddLicense.setVisibility(View.VISIBLE);
        }
        DLcancelLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AClicenseManage("0");

            }
        });

        DLaddLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AClicenseManage("1");

            }
        });

        if (intent.getStringExtra("ac_position").equals("1")){
            mAcPosition.setText("Enabled");
            mAcPosition.setChecked(true);
        }else {
            mAcPosition.setText("Disabled");
            mAcPosition.setChecked(false);
        }
        if (intent.getStringExtra("ActiveStatus").equals("1")){
            mAcLogged.setText("Logged In");
            mAcLogged.setChecked(true);
        }else {
            mAcLogged.setText("Logged Out");
            mAcLogged.setChecked(false);
        }

        mAcLogged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    AClogged("1");
                    mAcLogged.setText("Logged In");
                }else{
                    AClogged("0");
                    mAcLogged.setText("Logged Out");
                }
            }
        });

        mAcPosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ACDisable("1");
                    mAcPosition.setText("Enabled");
                }else{
                    ACDisable("0");
                    mAcPosition.setText("Disabled");
                }
            }
        });


    }

    private void LoadLicense(){
        Call<JsonElement> call=apiInterface.getLicenseLoad(UserID);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful() && response.code()==200) {
                    try {
                        JSONArray rootArray=new JSONArray(response.body().toString());

                        for (int i=0; i<rootArray.length(); i++) {

                            String id=rootArray.getJSONObject(i).getString("id");
                            String licensenumber=rootArray.getJSONObject(i).getString("license_number");
                            String starteddate=rootArray.getJSONObject(i).getString("started_date");
                            String expiredate=rootArray.getJSONObject(i).getString("expire_date");
                            mLicenseNumber.setText(licensenumber);
                            mLicenseExpire.setText(expiredate);
                            mlicenseRegister.setText(starteddate);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DetailsUsersActivity.this, "error found:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsUsersActivity.this, "Unknown error occurred.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(DetailsUsersActivity.this, "error:- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPostListByPagination(String key) {
        Call<JsonElement> call=apiInterface.getLicenseLoad(key);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.code()==200) {
                    //list.clear();
                    try {
                        JSONArray rootArray=new JSONArray(response.body().toString());

                        for (int i=0; i<rootArray.length(); i++) {

                            String id=rootArray.getJSONObject(i).getString("Id");
                            String title=rootArray.getJSONObject(i).getString("Title");
                            String description=rootArray.getJSONObject(i).getString("Description");
                            String circularImageUrl=rootArray.getJSONObject(i).getString("CircularImageUrl");
                            String startingDate=rootArray.getJSONObject(i).getString("StartingDate");
                            String endDate=rootArray.getJSONObject(i).getString("EndDate");
                            String circularCategory=rootArray.getJSONObject(i).getString("CircularCategory");
                            String circularSourceName=rootArray.getJSONObject(i).getString("CircularSourceName");
                            String circularSourceUrl=rootArray.getJSONObject(i).getString("CircularSourceUrl");
                            String ageEndingDate=rootArray.getJSONObject(i).getString("AgeEndingDate");
                            String targetAge=rootArray.getJSONObject(i).getString("TargetAge");
                            String categoryName =rootArray.getJSONObject(i).getString("CategoryName");
                            //PostModel postModel=new PostModel(id,title,description,circularImageUrl,startingDate,endDate,circularCategory,circularSourceName,circularSourceUrl,ageEndingDate,targetAge,categoryName);
                           // list.add(postModel);
                        }
                        //adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DetailsUsersActivity.this, "error found:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsUsersActivity.this, "Unknown error occurred.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailsUsersActivity.this, "error:- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ACDisable(String ACposition){
        Call<JsonElement> call=apiInterface.getACposition(UserID,ACposition);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful() && response.code()==200) {
                    try {
                        JSONArray rootArray=new JSONArray(response.body().toString());

                        for (int i=0; i<rootArray.length(); i++) {

                            String status=rootArray.getJSONObject(i).getString("status");
                            if (status.equals("success")){
                                Toast.makeText(DetailsUsersActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DetailsUsersActivity.this, "error found:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsUsersActivity.this, "Unknown error occurred.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(DetailsUsersActivity.this, "error:- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AClogged(String aclogged){
        Call<JsonElement> call=apiInterface.getAClogged(UserID,aclogged);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful() && response.code()==200) {
                    try {
                        JSONArray rootArray=new JSONArray(response.body().toString());

                        for (int i=0; i<rootArray.length(); i++) {

                            String status=rootArray.getJSONObject(i).getString("status");
                            if (status.equals("success")){
                                Toast.makeText(DetailsUsersActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DetailsUsersActivity.this, "error found:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsUsersActivity.this, "Unknown error occurred.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(DetailsUsersActivity.this, "error:- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AClicenseManage(String aclicense){
        Call<JsonElement> call=apiInterface.getManageLicense(UserID,aclicense);
        call.enqueue(new Callback<JsonElement>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful() && response.code()==200) {
                    try {
                        JSONArray rootArray=new JSONArray(response.body().toString());

                        for (int i=0; i<rootArray.length(); i++) {

                            String status=rootArray.getJSONObject(i).getString("status");
                            if (status.equals("success")){
                                if (aclicense.equals("1")){
                                    /*mUsertype.setText("Premium");
                                    LoadLicense();*/
                                    mUsertype.setText("Premium");
                                    DLcancelLicense.setVisibility(View.VISIBLE);
                                    DLaddLicense.setVisibility(View.GONE);
                                    DLlicenseLayout.setVisibility(View.VISIBLE);

                                }else{
                                    mUsertype.setText("Standerd");
                                    DLcancelLicense.setVisibility(View.GONE);
                                    DLaddLicense.setVisibility(View.VISIBLE);
                                    DLlicenseLayout.setVisibility(View.GONE);
                                }
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DetailsUsersActivity.this, "error found:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsUsersActivity.this, "Unknown error occurred.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(DetailsUsersActivity.this, "error:- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
package com.lottery.sambad.admin.ui.quick_search;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.lottery.sambad.admin.R;
import com.lottery.sambad.admin.database.ApiInterface;
import com.lottery.sambad.admin.database.RetrofitClient;
import com.lottery.sambad.admin.model.ModelUsers;
import com.lottery.sambad.admin.ui.users_account_info.AdapterUsers;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickSearchActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdapterUsers adapter;
    private List<ModelUsers> list=new ArrayList<>();
    private String targetCategory;
    private String targetCategoryId;
    private int page_number = 1;
    private int item_count = 30;
    private int past_visible_item, visible_item_count, total_item_count, previous_total = 0;
    private boolean isLoading = true;
    private ProgressBar progressBar;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_search);

        Toolbar mToolbar = findViewById(R.id.mtoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        apiInterface= RetrofitClient.getApiClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.searchRecyceler);
        progressBar=findViewById(R.id.recySearchProgress);
        progressBar.setVisibility(View.GONE);

        layoutManager=new LinearLayoutManager(this);
        adapter= new AdapterUsers(this,list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);




        getPostListByPagination( "");
        editText = findViewById(R.id.searchBox);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getPostListByPagination( charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        showKeyboard();


    }

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    private void getPostListByPagination(String key) {
        Call<JsonElement> call=apiInterface.getPostSearch(key);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.code()==200) {
                    list.clear();
                    try {
                        JSONArray rootArray=new JSONArray(response.body().toString());

                        for (int i=0; i<rootArray.length(); i++) {

                            String Id=rootArray.getJSONObject(i).getString("Id");
                            String Token=rootArray.getJSONObject(i).getString("Token");
                            String Phone=rootArray.getJSONObject(i).getString("Phone");
                            String paid_license=rootArray.getJSONObject(i).getString("paid_license");
                            String ac_position=rootArray.getJSONObject(i).getString("ac_position");
                            String RegistrationDate=rootArray.getJSONObject(i).getString("RegistrationDate");
                            String ActiveStatus=rootArray.getJSONObject(i).getString("ActiveStatus");
                            ModelUsers postModel=new ModelUsers(Id,Token,Phone,paid_license,ac_position,RegistrationDate,ActiveStatus);
                            list.add(postModel);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(QuickSearchActivity.this, "error found:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(QuickSearchActivity.this, "Unknown error occurred.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(QuickSearchActivity.this, "error:- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
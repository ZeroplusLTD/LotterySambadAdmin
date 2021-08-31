package com.lottery.sambad.admin.database;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("get_load_license.php?")
    Call<JsonElement> getLicenseLoad(@Query("UserId") String UserId);

    @GET("get_disable_account_in_admin.php?")
    Call<JsonElement> getACposition(@Query("UserId") String UserId,@Query("ACposition") String ACPosition);

    @GET("get_logged_account_in_admin.php?")
    Call<JsonElement> getAClogged(@Query("UserId") String UserId,@Query("ACposition") String ACPosition);

    @GET("get_manage_license_in_admin.php?")
    Call<JsonElement> getManageLicense(@Query("UserId") String UserId,@Query("ACposition") String ACPosition);

    @GET("get_data_count_in_admin.php?")
    Call<JsonElement> getDeshCount();

    @GET("get_search_users_in_admin.php?")
    Call<JsonElement> getPostSearch(@Query("searchKey") String searchKey);

}

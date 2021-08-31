package com.lottery.sambad.admin.database;

import android.util.Base64;


import com.lottery.sambad.admin.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitForIp,retrofit;
    private static final String baseUrl="https://lotteryresultpro.lotterysambadpro.xyz/";
    private static final String AUTH= "Basic "+ Base64.encodeToString((BuildConfig.USER_NAME+":"+BuildConfig.USER_PASSWORD).getBytes(),Base64.NO_WRAP);

    public static synchronized Retrofit getApiClientForIp(){
        if (retrofitForIp==null){
            retrofitForIp= new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitForIp;
    }

    public static synchronized Retrofit getApiClient(){
        if (retrofit==null){
            OkHttpClient okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(
                            chain -> {
                                Request original =chain.request();
                                Request.Builder requestBuilder=original.newBuilder()
                                        .addHeader("Authorization",AUTH)
                                        .method(original.method(), original.body());
                                Request request=requestBuilder.build();
                                return chain.proceed(request);
                            }
                    )
                    .build();
            retrofit= new Retrofit
                    .Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }


}

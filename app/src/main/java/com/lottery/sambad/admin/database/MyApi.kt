package com.lottery.sambad.admin.database

import android.util.Base64
import com.lottery.sambad.admin.BuildConfig
import com.lottery.sambad.admin.model.LotteryNumberModel
import com.lottery.sambad.admin.model.ResultUploadResponse
import com.lottery.sambad.admin.model.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {


//    @POST("upload_lottery_number.php?")
//    suspend fun uploadLotteryNumber(
//        @Query("LotteryNumber") lotteryNumber: String,
//        @Query("LotterySerialNumber") lotterySerialNumber: String,
//        @Query("WinType") winType: String,
//        @Query("WinDate") winDate: String,
//        @Query("WinTime") winTime: String,
//        @Query("WinDayName") winDayName: String
//    ): Response<ResultUploadResponse>


    @POST("upload_all_lottery_number.php?")
    suspend fun uploadLotteryNumber(
        @Body rawJsonDataForLottery: MutableList<LotteryNumberModel>
    ): Response<ResultUploadResponse>
    @POST("extra_upload_upload_all_lottery_number.php?")
    suspend fun ExtrauploadLotteryNumber(
        @Body rawJsonDataForLottery: MutableList<LotteryNumberModel>
    ): Response<ResultUploadResponse>


    @GET("get_users_info_in_admin.php?")
    suspend fun getDuplicateLotteryNumberList(
        @Query("PageNumber") pageNumber: String,
        @Query("ItemCount") itemCount: String,
        @Query("listPosition") listPosition: String
    ): Response<UserInfoResponse>

    @Multipart
    @POST("upload_result_documents.php?")
    suspend fun uploadResultDocuments(@Part("DayName") dayName: RequestBody, @Part("ResultTime") resultTime: RequestBody, @Part("ResultDate") resultDate: RequestBody, @Part image: MultipartBody.Part): Response<ResultUploadResponse>

    @Multipart
    @POST("upload_ads_image_documents.php?")
    suspend fun uploadAdsDocument(@Part("TargetUrl") targetUrl: RequestBody, @Part("ActiveStatus") activeStatus: RequestBody, @Part image: MultipartBody.Part): Response<ResultUploadResponse>

    @Multipart
    @POST("upload_home_tutorial_document.php?")
    suspend fun uploadHomeTutorialDocument(@Part("TargetUrl") targetUrl: RequestBody, @Part image: MultipartBody.Part): Response<ResultUploadResponse>





    companion object {

        @Volatile
        private var myApiInstance: MyApi? = null
        private val LOCK = Any()

        operator fun invoke() = myApiInstance ?: synchronized(LOCK) {
            myApiInstance ?: createClient().also {
                myApiInstance = it
            }
        }

        private fun createClient(): MyApi {
            val AUTH: String = "Basic ${
                Base64.encodeToString(
                    ("${BuildConfig.USER_NAME}:${BuildConfig.USER_PASSWORD}").toByteArray(),
                    Base64.NO_WRAP
                )
            }"
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .addHeader("Authorization", AUTH)
                        .method(original.method(), original.body())
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(MyApi::class.java)
        }


    }
}
package com.lottery.sambad.admin.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.gson.JsonElement
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.database.ApiInterface
import com.lottery.sambad.admin.database.RetrofitClient
import com.lottery.sambad.admin.databinding.ActivityMainBinding
import com.lottery.sambad.admin.ui.NotificationActivity
import com.lottery.sambad.admin.ui.ads_image.AdsImageUploadActivity
import com.lottery.sambad.admin.ui.lottery_number_upload.LotteryNumberUploadActivity
import com.lottery.sambad.admin.ui.new_users.HeadlineActivity
import com.lottery.sambad.admin.ui.new_version.NewVersionActivity
import com.lottery.sambad.admin.ui.pdf_and_image_upload.PdfAndImageUploadActivity
import com.lottery.sambad.admin.ui.quick_search.QuickSearchActivity
import com.lottery.sambad.admin.ui.reports.UpdatePaidInfoActivity
import com.lottery.sambad.admin.ui.tutorial.HomeTutorialUploadActivity
import com.lottery.sambad.admin.ui.users_account_info.*
import com.lottery.sambad.admin.utils.MyExtensions.shortToast
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private var apiInterface: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface::class.java)

        initAll()

        checkRequiredPermission()

//        Coroutines.io {
//            val response=CommonMethods.sendNotification("Hi","Thanks for using our app.",null)
//            Log.d(Constants.TAG,"sending status:- $response")
//        }

       /* val versionRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("ActiveUsers")
        versionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val countTxt = findViewById<TextView>(R.id.active_usercount)
                if (snapshot.exists()){
                    val liveUser = snapshot.childrenCount
                    countTxt.text = liveUser.toString()
                }else{
                    countTxt.text = "0"
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
*/


    }





    private fun initAll() {
        binding.uploadPdfAndImageButton.setOnClickListener(this)
        binding.uploadLotteryNumberButton.setOnClickListener(this)
        binding.uploadAdsImageButton.setOnClickListener(this)
        binding.uploadHomeTutorialButton.setOnClickListener(this)
        binding.uploadNotificationButton.setOnClickListener(this)
        binding.UpdateUserAppButton.setOnClickListener(this)
        binding.LoggedInUserBtn.setOnClickListener(this)
        binding.LoggedOutUserBtn.setOnClickListener(this)
        binding.StanderdUserBtn.setOnClickListener(this)
        binding.PremiumUserBtn.setOnClickListener(this)
        binding.DisableUserBtn.setOnClickListener(this)
        binding.ExpireUserBtn.setOnClickListener(this)
        binding.uploadHeadlineBtn.setOnClickListener(this)
        binding.uploadpaidInfoBtn.setOnClickListener(this)
        getCoundData();
    }


    private fun getCoundData() {
        val call: Call<JsonElement> = apiInterface!!.getDeshCount()
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful && response.code() == 200) {
                    try {
                        val rootArray = JSONArray(response.body().toString())
                        for (i in 0 until rootArray.length()) {
                            val premiumusers = rootArray.getJSONObject(i).getString("premium_users")
                            val standerdusers = rootArray.getJSONObject(i).getString("standerd_users")
                            val loggedinusers = rootArray.getJSONObject(i).getString("logged_in_users")
                            val loggedoutusers = rootArray.getJSONObject(i).getString("logged_out_users")
                            val loggedexpireusers = rootArray.getJSONObject(i).getString("logged_expire_users")
                            val disabledusers = rootArray.getJSONObject(i).getString("disabled_users")

                            binding.premiumCount.text = premiumusers
                            binding.standerdCount.text = standerdusers
                            binding.loggedinCount.text = loggedinusers
                            binding.loggedoutCount.text = loggedoutusers
                            binding.LicenseExpireCount.text = loggedexpireusers
                            binding.disableCount.text = disabledusers


                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@MainActivity,
                            "error found:- " + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Unknown error occurred.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "error:- " + t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun checkRequiredPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    shortToast(resources.getString(R.string.need_to_grant_all_permission))
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            })
            .check()
    }

    override fun onClick(v: View?) {
        v?.let {
            var uploadIntent: Intent
            when (it.id) {
                R.id.uploadPdfAndImageButton -> {
                    uploadIntent= Intent(this,PdfAndImageUploadActivity::class.java)
                    startActivity(uploadIntent)
                }

                R.id.uploadLotteryNumberButton -> {
                    uploadIntent= Intent(this,LotteryNumberUploadActivity::class.java)
                    startActivity(uploadIntent)
                }

                R.id.uploadAdsImageButton -> {
                    uploadIntent= Intent(this,AdsImageUploadActivity::class.java)
                    startActivity(uploadIntent)
                }

                R.id.uploadHomeTutorialButton -> {
                    uploadIntent= Intent(this,HomeTutorialUploadActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.uploadNotificationButton -> {
                    uploadIntent= Intent(this,NotificationActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.UpdateUserAppButton -> {
                    uploadIntent= Intent(this,NewVersionActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.LoggedInUserBtn ->{
                    uploadIntent= Intent(this,LoggedInUsersActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.LoggedOutUserBtn ->{
                    uploadIntent= Intent(this,LoggedOutUsersActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.StanderdUserBtn ->{
                    uploadIntent= Intent(this,StanderdUsersActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.PremiumUserBtn ->{
                    uploadIntent= Intent(this,PremiumUsersActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.DisableUserBtn ->{
                    uploadIntent= Intent(this,DisableUsersActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.ExpireUserBtn ->{
                    uploadIntent= Intent(this,ExpireLicenseUsersActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.uploadHeadlineBtn ->{
                    uploadIntent= Intent(this,HeadlineActivity::class.java)
                    startActivity(uploadIntent)
                }
                R.id.uploadpaidInfoBtn ->{
                    uploadIntent= Intent(this,UpdatePaidInfoActivity::class.java)
                    startActivity(uploadIntent)
                }


            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)


        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.quickSearch -> {
                var uploadIntent: Intent = Intent(this,QuickSearchActivity::class.java)
                startActivity(uploadIntent)
            }
            R.id.quickRefresh -> {
                getCoundData()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
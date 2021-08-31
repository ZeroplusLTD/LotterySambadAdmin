package com.lottery.sambad.admin.ui.lottery_number_upload

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.databinding.ActivityLotteryNumberUploadBinding
import com.lottery.sambad.admin.model.LotteryNumberModel
import com.lottery.sambad.admin.ui.MyApplication
import com.lottery.sambad.admin.utils.CommonMethods
import com.lottery.sambad.admin.utils.Constants
import com.lottery.sambad.admin.utils.Coroutines
import com.lottery.sambad.admin.utils.MyExtensions.shortToast

class LotteryNumberUploadActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLotteryNumberUploadBinding
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var inputMethodManager: InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLotteryNumberUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initAll()

        setUpDropDownTextView()




    }


    private fun initAll() {
        clipboardManager=getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        inputMethodManager=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.uploadLotteryNumberButton.setOnClickListener(this)
        binding.firstLotteryNumberPasteImageView.setOnClickListener(this)
        binding.firstLotteryNumberClearImageView.setOnClickListener(this)
        binding.secondLotteryNumberPasteImageView.setOnClickListener(this)
        binding.secondLotteryNumberClearImageView.setOnClickListener(this)
        binding.thirdLotteryNumberPasteImageView.setOnClickListener(this)
        binding.thirdLotteryNumberClearImageView.setOnClickListener(this)
        binding.fourthLotteryNumberPasteImageView.setOnClickListener(this)
        binding.fourthLotteryNumberClearImageView.setOnClickListener(this)
        binding.fifthLotteryNumberPasteImageView.setOnClickListener(this)
        binding.fifthLotteryNumberClearImageView.setOnClickListener(this)
        binding.spinKit.visibility=View.GONE

        resetData()
    }

    private fun setUpDropDownTextView() {
        binding.resultPublishTimeDropDownTextView.text= Constants.noonTime
        val resultTime: ArrayList<String> = arrayListOf()
        resultTime.add(Constants.noonTime)
        resultTime.add(Constants.eveningTime)
        resultTime.add(Constants.nightTime)
        binding.resultPublishTimeDropDownTextView.setOptions(resultTime)
    }

    private fun resetData() {
        binding.spinKit.visibility=View.GONE
        binding.lotterySerialNumbersEditText.text.clear()
        binding.firstLotteryNumbersEditText.text.clear()
        binding.secondLotteryNumbersEditText.text.clear()
        binding.thirdLotteryNumbersEditText.text.clear()
        binding.fourthLotteryNumbersEditText.text.clear()
        binding.fifthLotteryNumbersEditText.text.clear()
        binding.resultPublishTimeDropDownTextView.text=Constants.noonTime

        try {
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        } catch (e: Exception) {}

    }

    private fun startLotteryNumberUploadOperation() {
        val serialNumber: String=binding.lotterySerialNumbersEditText.text.toString().trim()
        val firstLotteryNumber: String=binding.firstLotteryNumbersEditText.text.toString().trim()
        val secondLotteryNumber: String=binding.secondLotteryNumbersEditText.text.toString().trim()
        val thirdLotteryNumber: String=binding.thirdLotteryNumbersEditText.text.toString().trim()
        val fourthLotteryNumber: String=binding.fourthLotteryNumbersEditText.text.toString().trim()
        val fifthLotteryNumber: String=binding.fifthLotteryNumbersEditText.text.toString().trim()

        val secondLotteryNumberList: List<String> = secondLotteryNumber.split("\\s".toRegex())
        val thirdLotteryNumberList: List<String> = thirdLotteryNumber.split("\\s".toRegex())
        val fourthLotteryNumberList: List<String> = fourthLotteryNumber.split("\\s".toRegex())
        val fifthLotteryNumberList: List<String> = fifthLotteryNumber.split("\\s".toRegex())

        if (firstLotteryNumber.isNullOrEmpty() || firstLotteryNumber.length<5) {
            shortToast("1st prize lottery number length:- ${firstLotteryNumber.length}")
            return
        }
        if (secondLotteryNumberList.isNullOrEmpty() || secondLotteryNumberList.size<10) {
            shortToast("2nd prize lottery number count:- ${secondLotteryNumberList.size}")
            return
        }
        if (thirdLotteryNumberList.isNullOrEmpty() || thirdLotteryNumberList.size<10) {
            shortToast("3rd prize lottery number count:- ${thirdLotteryNumberList.size}")
            return
        }
        if (fourthLotteryNumberList.isNullOrEmpty() || fourthLotteryNumberList.size<10) {
            shortToast("4th prize lottery number count:- ${fourthLotteryNumberList.size}")
            return
        }
        if (fifthLotteryNumberList.isNullOrEmpty() || fifthLotteryNumberList.size<10) {
            shortToast("5th prize lottery number count:- ${fifthLotteryNumberList.size}")
            return
        }
        val winDate: String=CommonMethods.increaseDecreaseDaysUsingValue(0)
        val winTime: String=binding.resultPublishTimeDropDownTextView.text.toString()
        val winDayName: String=CommonMethods.getDayNameUsingDate(CommonMethods.increaseDecreaseDaysUsingValue(0))

//        uploadLotteryNumber(firstLotteryNumber,serialNumber,Constants.winTypeFirst,winTime,winDate,winDayName)
//        for (number in secondLotteryNumberList) {
//            uploadLotteryNumber(number,serialNumber,Constants.winTypeSecond,winTime,winDate,winDayName)
//        }
//        for (number in thirdLotteryNumberList) {
//            uploadLotteryNumber(number,serialNumber,Constants.winTypeThird,winTime,winDate,winDayName)
//        }
//        for (number in fourthLotteryNumberList) {
//            uploadLotteryNumber(number,serialNumber,Constants.winTypeFourth,winTime,winDate,winDayName)
//        }
//        for (number in fifthLotteryNumberList) {
//            uploadLotteryNumber(number,serialNumber,Constants.winTypeFifth,winTime,winDate,winDayName)
//        }
//        Coroutines.io {
//            val response=CommonMethods.sendNotification(resources.getString(R.string.result_publish_notification_title),resources.getString(R.string.result_publish_notification_description),null)
//        }


        val uploadAbleList: MutableList<LotteryNumberModel> = arrayListOf()
        uploadAbleList.add(LotteryNumberModel(null,firstLotteryNumber,serialNumber,Constants.winTypeFirst,winDate,winTime,winDayName))
        for (number in secondLotteryNumberList) {
            if ((!number.isNullOrEmpty() && !number.isNullOrBlank()) && number.length>=4) {
                uploadAbleList.add(LotteryNumberModel(null,number,serialNumber,Constants.winTypeSecond,winDate,winTime,winDayName))
            }
        }
        for (number in thirdLotteryNumberList) {
            if ((!number.isNullOrEmpty() && !number.isNullOrBlank()) && number.length>=4) {
                uploadAbleList.add(LotteryNumberModel(null,number,serialNumber,Constants.winTypeThird,winDate,winTime,winDayName))
            }
        }
        for (number in fourthLotteryNumberList) {
            if ((!number.isNullOrEmpty() && !number.isNullOrBlank()) && number.length>=4) {
                uploadAbleList.add(LotteryNumberModel(null,number,serialNumber,Constants.winTypeFourth,winDate,winTime,winDayName))
            }
        }
        for (number in fifthLotteryNumberList) {
            if ((!number.isNullOrEmpty() && !number.isNullOrBlank()) && number.length>=4) {
                uploadAbleList.add(LotteryNumberModel(null,number,serialNumber,Constants.winTypeFifth,winDate,winTime,winDayName))
            }
        }

        if (uploadAbleList.size==131) {
            uploadLotteryNumber(uploadAbleList)
        } else {
            shortToast("total lottery number in not correct.")
        }
    }

    private fun uploadLotteryNumber(rawData: MutableList<LotteryNumberModel>) {
        try {
            Coroutines.main {
                if (!rawData.isNullOrEmpty()) {
                    binding.spinKit.visibility=View.VISIBLE
                    Coroutines.io {
                        val response= (applicationContext as MyApplication).myApi.uploadLotteryNumber(rawData)
                        Coroutines.main {
                            binding.spinKit.visibility=View.GONE
                            try {
                                if (response.isSuccessful && response.code()==200) {
                                    if (response.body()!=null) {
                                        resetData()
                                        shortToast(resources.getString(R.string.data_uploaded))
                                        Coroutines.io {
                                            val winTime: String=binding.resultPublishTimeDropDownTextView.text.toString()
                                            if (winTime.equals("01:00 PM")){
                                                CommonMethods.sendNotification(resources.getString(R.string.notify_title_morning),resources.getString(R.string.notify_msg_common),null)
                                            }else if (winTime.equals("04:00 PM")){
                                                CommonMethods.sendNotification(resources.getString(R.string.notify_title_day),resources.getString(R.string.notify_msg_common),null)
                                            }else if (winTime.equals("08:00 PM")){
                                                CommonMethods.sendNotification(resources.getString(R.string.notify_title_evening),resources.getString(R.string.notify_msg_common),null)
                                            }

                                        }
                                    } else {
                                        shortToast(resources.getString(R.string.unknown_error))
                                    }
                                }
                            } catch (e: Exception) {

                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {

        }
    }




//    private fun uploadLotteryNumber(lotteryNumber: String, serialNumber: String, winType: String, winTime: String, winDate: String, winDayName: String) {
//        try {
//            Coroutines.main {
//                if (!lotteryNumber.isNullOrEmpty()) {
//                    binding.spinKit.visibility=View.VISIBLE
//                    Coroutines.io {
//                        val response= (applicationContext as MyApplication).myApi.uploadLotteryNumber(lotteryNumber,serialNumber,winType,winDate,winTime,winDayName)
//                        Coroutines.main {
//                            binding.spinKit.visibility=View.GONE
//                            if (response.isSuccessful && response.code()==200) {
//                                if (response.body()!=null) {
//                                    resetData()
//                                } else {
//                                    shortToast(resources.getString(R.string.unknown_error))
//                                }
//                            } else {
//                                shortToast("Failed:- ${response.errorBody().toString()}")
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {}
//    }



    private fun clearLotteryNumberListFromEditText(type: String) {
        when(type) {
            Constants.winTypeFirst -> {
                binding.firstLotteryNumbersEditText.text.clear()
                shortToast("data cleared successfully")
            }
            Constants.winTypeSecond -> {
                binding.secondLotteryNumbersEditText.text.clear()
                shortToast("data cleared successfully")
            }
            Constants.winTypeThird -> {
                binding.thirdLotteryNumbersEditText.text.clear()
                shortToast("data cleared successfully")
            }
            Constants.winTypeFourth -> {
                binding.fourthLotteryNumbersEditText.text.clear()
                shortToast("data cleared successfully")
            }
            Constants.winTypeFifth -> {
                binding.fifthLotteryNumbersEditText.text.clear()
                shortToast("data cleared successfully")
            }
        }
    }

    private fun pasteLotteryNumberInEditText(type: String) {
        if (!clipboardManager.hasPrimaryClip()) {
            shortToast("No data copied yet. please copy data and try again.")
            return
        }
        val lotteryNumberList: String=clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
        when(type) {
            Constants.winTypeFirst -> {
                binding.firstLotteryNumbersEditText.setText(lotteryNumberList)
                shortToast("data pasted successfully")
            }
            Constants.winTypeSecond -> {
                binding.secondLotteryNumbersEditText.setText(lotteryNumberList)
                shortToast("data pasted successfully")
            }
            Constants.winTypeThird -> {
                binding.thirdLotteryNumbersEditText.setText(lotteryNumberList)
                shortToast("data pasted successfully")
            }
            Constants.winTypeFourth -> {
                binding.fourthLotteryNumbersEditText.setText(lotteryNumberList)
                shortToast("data pasted successfully")
            }
            Constants.winTypeFifth -> {
                binding.fifthLotteryNumbersEditText.setText(lotteryNumberList)
                shortToast("data pasted successfully")
            }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.uploadLotteryNumberButton -> startLotteryNumberUploadOperation()
                R.id.firstLotteryNumberClearImageView -> clearLotteryNumberListFromEditText(Constants.winTypeFirst)
                R.id.secondLotteryNumberClearImageView -> clearLotteryNumberListFromEditText(Constants.winTypeSecond)
                R.id.thirdLotteryNumberClearImageView -> clearLotteryNumberListFromEditText(Constants.winTypeThird)
                R.id.fourthLotteryNumberClearImageView -> clearLotteryNumberListFromEditText(Constants.winTypeFourth)
                R.id.fifthLotteryNumberClearImageView -> clearLotteryNumberListFromEditText(Constants.winTypeFifth)
                R.id.firstLotteryNumberPasteImageView -> pasteLotteryNumberInEditText(Constants.winTypeFirst)
                R.id.secondLotteryNumberPasteImageView -> pasteLotteryNumberInEditText(Constants.winTypeSecond)
                R.id.thirdLotteryNumberPasteImageView -> pasteLotteryNumberInEditText(Constants.winTypeThird)
                R.id.fourthLotteryNumberPasteImageView -> pasteLotteryNumberInEditText(Constants.winTypeFourth)
                R.id.fifthLotteryNumberPasteImageView -> pasteLotteryNumberInEditText(Constants.winTypeFifth)
            }
        }
    }


}
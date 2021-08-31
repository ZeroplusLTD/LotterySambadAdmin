package com.lottery.sambad.admin.ui.pdf_and_image_upload

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.google.gson.JsonElement
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.databinding.ActivityPdfAndImageUploadBinding
import com.lottery.sambad.admin.ui.MyApplication
import com.lottery.sambad.admin.utils.CommonMethods
import com.lottery.sambad.admin.utils.Constants
import com.lottery.sambad.admin.utils.Coroutines
import com.lottery.sambad.admin.utils.MyExtensions.shortToast
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.utils.ContentUriUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class PdfAndImageUploadActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPdfAndImageUploadBinding
    private var pdfPath: String?=null
    private var imagePath: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPdfAndImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initAll()

        setUpDropDownTextView()


    }


    private fun initAll() {
        binding.pdfUploadButton.setOnClickListener(this)
        binding.imageUploadButton.setOnClickListener(this)
        binding.uploadDocumentButton.setOnClickListener(this)
        binding.spinKit.visibility=View.GONE
    }

    private fun setUpDropDownTextView() {
        binding.resultPublishTimeDropDownTextView.text=Constants.noonTime
        val resultPublishTime: ArrayList<String> = arrayListOf()
        resultPublishTime.add(Constants.noonTime)
        resultPublishTime.add(Constants.eveningTime)
        resultPublishTime.add(Constants.nightTime)
        binding.resultPublishTimeDropDownTextView.setOptions(resultPublishTime)
    }

    private fun choosePdf() {
        FilePickerBuilder
            .instance
            .setMaxCount(1)
            .enableImagePicker(false)
            .enableDocSupport(true)
            .pickFile(this, Constants.PDF_PICKER_REQUEST_CODE)
    }

    private fun chooseImage() {
        FilePickerBuilder
            .instance
            .setMaxCount(1)
            .enableImagePicker(true)
            .enableDocSupport(false)
            .pickPhoto(this, Constants.IMAGE_PICKER_REQUEST_CODE)
    }

    private fun startFileUploadOperation() {
        val DayName: String=CommonMethods.getDayNameUsingDate(CommonMethods.increaseDecreaseDaysUsingValue(0))
        val PublishTime: String=binding.resultPublishTimeDropDownTextView.text.toString()
        val PublishDate: String=CommonMethods.increaseDecreaseDaysUsingValue(0)

        val dayName: RequestBody= RequestBody.create(MediaType.parse("multipart/form-data"),DayName)
        val publishTime: RequestBody= RequestBody.create(MediaType.parse("multipart/form-data"),PublishTime)
        val publishDate: RequestBody= RequestBody.create(MediaType.parse("multipart/form-data"),PublishDate)
        var pdf: MultipartBody.Part?=null
        if (!pdfPath.isNullOrEmpty()) {
            val file: File= File(pdfPath)
            val requestFile: RequestBody= RequestBody.create(MediaType.parse("multipart/form-data"),file)
            pdf=MultipartBody.Part.createFormData("pdf",file.name,requestFile)
        } else {
//            shortToast(resources.getString(R.string.selected_file_is_not_valid))
//            return
        }
        var image: MultipartBody.Part?=null
        if (!imagePath.isNullOrEmpty()) {
            val file: File= File(imagePath)
            val requestFile: RequestBody= RequestBody.create(MediaType.parse("multipart/form-data"),file)
            image=MultipartBody.Part.createFormData("image",file.name,requestFile)
        } else {
            shortToast(resources.getString(R.string.selected_file_is_not_valid))
            return
        }
//        if (pdf!=null && image!=null) {
        if (image!=null) {
            Coroutines.main {
                binding.spinKit.visibility=View.VISIBLE
                val response =(applicationContext as MyApplication).myApi.uploadResultDocuments(dayName,publishTime,publishDate,image)
                binding.spinKit.visibility=View.GONE
                if (response.isSuccessful && response.code()==200) {
                    if (response.body()!=null) {
                        shortToast("Status:- ${response.body()?.message}")
                        if (response.body()!!.status!!.equals("success",true)) {
                            resetData()
                        }
                    } else {
                        shortToast(resources.getString(R.string.unknown_error))
                    }
                } else {
                    shortToast("Failed for:- ${response.errorBody().toString()}")
                }
            }
        } else {
            shortToast(resources.getString(R.string.file_creation_failed))
        }
    }

    private fun resetData() {
        binding.spinKit.visibility=View.GONE
        pdfPath=null
        imagePath=null
        binding.selectedPdfFileNameTextView.text=""
        binding.selectedImageFileNameTextView.text=""
        binding.resultPublishTimeDropDownTextView.text=Constants.noonTime
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK && data!=null) {
            if (requestCode==Constants.PDF_PICKER_REQUEST_CODE) {
                val list: ArrayList<Uri>? = data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_DOCS)
                if (list!=null) {
                    for (i in 0 until list.size) {
                        if (ContentUriUtils.getFilePath(this,list[i])!=null) {
                            pdfPath= ContentUriUtils.getFilePath(this,list[i])
                            binding.selectedPdfFileNameTextView.text="File path is:- $pdfPath"
                        }
                    }
                } else {
                    shortToast(resources.getString(R.string.selected_file_is_not_valid))
                    pdfPath=null
                    binding.selectedPdfFileNameTextView.text="File path is:- $pdfPath"
                }
            } else if (requestCode==Constants.IMAGE_PICKER_REQUEST_CODE) {
                val list: ArrayList<Uri>? = data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)
                if (list!=null) {
                    for (i in 0 until list.size) {
                        if (ContentUriUtils.getFilePath(this,list[i])!=null) {
                            imagePath= ContentUriUtils.getFilePath(this,list[i])
                            binding.selectedImageFileNameTextView.text="File path is:- $imagePath"
                        }
                    }
                } else {
                    shortToast(resources.getString(R.string.selected_file_is_not_valid))
                    imagePath=null
                    binding.selectedImageFileNameTextView.text="File path is:- $imagePath"
                }
            }
        } else {
            shortToast(resources.getString(R.string.selected_file_empty))
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.pdfUploadButton -> choosePdf()
                R.id.imageUploadButton -> chooseImage()
                R.id.uploadDocumentButton -> startFileUploadOperation()
            }
        }
    }


}
package com.lottery.sambad.admin.ui.tutorial

import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.databinding.ActivityHomeTutorialUploadBinding
import com.lottery.sambad.admin.ui.MyApplication
import com.lottery.sambad.admin.utils.Constants
import com.lottery.sambad.admin.utils.Coroutines
import com.lottery.sambad.admin.utils.MyExtensions.shortToast
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.utils.ContentUriUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class HomeTutorialUploadActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHomeTutorialUploadBinding
    private lateinit var clipboardManager: ClipboardManager
    private var imagePath: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeTutorialUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initAll()



    }


    private fun initAll() {
        clipboardManager=getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        binding.imageUploadButton.setOnClickListener(this)
        binding.uploadDocumentButton.setOnClickListener(this)
        binding.targetUrlPasteImageView.setOnClickListener(this)
        binding.targetUrlClearImageView.setOnClickListener(this)
        binding.spinKit.visibility= View.GONE
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
        val TargetUrl: String= binding.tutorialImageTargetUrlEditText.text.toString().trim()
        val targetUrl: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),TargetUrl)
        var image: MultipartBody.Part?=null
        if (!imagePath.isNullOrEmpty()) {
            val file: File = File(imagePath)
            val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
            image= MultipartBody.Part.createFormData("image",file.name,requestFile)
        } else {
            shortToast(resources.getString(R.string.selected_file_is_not_valid))
            return
        }
        if (image!=null) {
            Coroutines.main {
                binding.spinKit.visibility= View.VISIBLE
                val response =(applicationContext as MyApplication).myApi.uploadHomeTutorialDocument(targetUrl,image)
                binding.spinKit.visibility= View.GONE
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
        binding.spinKit.visibility= View.GONE
        imagePath=null
        binding.selectedImageFileNameTextView.text=""
        binding.tutorialImageTargetUrlEditText.text.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK && data!=null) {
            if (requestCode== Constants.IMAGE_PICKER_REQUEST_CODE) {
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
                R.id.imageUploadButton -> chooseImage()
                R.id.uploadDocumentButton -> startFileUploadOperation()

                R.id.targetUrlPasteImageView -> {
                    if (clipboardManager.hasPrimaryClip()) {
                        val targetUrl=clipboardManager.primaryClip?.getItemAt(0)?.text.toString().trim()
                        binding.tutorialImageTargetUrlEditText.setText("$targetUrl")
                        shortToast("Data copied successfully")
                    } else {
                        shortToast("data not copied yet.")
                    }
                }

                R.id.targetUrlClearImageView -> {
                    binding.tutorialImageTargetUrlEditText.text.clear()
                    shortToast("Data cleared successfully")
                }
            }
        }
    }



}
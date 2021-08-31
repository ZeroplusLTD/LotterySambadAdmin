package com.lottery.sambad.admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.lottery.sambad.admin.LockActivity.PreferenceHelper.adminPin
import com.lottery.sambad.admin.LockActivity.PreferenceHelper.customPreference
import com.lottery.sambad.admin.databinding.ActivityLockBinding
import com.lottery.sambad.admin.databinding.ActivityMainBinding
import com.lottery.sambad.admin.ui.lottery_number_upload.LotteryNumberUploadActivity
import com.lottery.sambad.admin.ui.main.MainActivity

class LockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockBinding
    val CUSTOM_PREF_NAME = "User_data_extra"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLockBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.pinKeyOne.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyOne.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyTwo.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyTwo.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyThree.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyThree.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyFour.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyFour.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyFive.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyFive.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeySix.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeySix.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeySeven.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeySeven.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyEight.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyEight.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyNine.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyNine.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyZero.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyZero.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyStrong.setOnClickListener{
            binding.PinviewText.text = "${binding.PinviewText.text}${binding.pinKeyStrong.text.toString()}"
            val prefs = customPreference(this, CUSTOM_PREF_NAME)
            if (binding.PinviewText.text.length.equals(6)){
                if (prefs.adminPin.equals(binding.PinviewText.text.toString())){
                    GetDashboard()
                }else{
                    binding.pinStatus.text = "Invalid PIN"
                    binding.lockImageview.setImageResource(R.drawable.invalid_pin)
                }
            }
        }
        binding.pinKeyClear.setOnClickListener{
            binding.PinviewText.text = ""
            binding.pinStatus.text = ""
            binding.lockImageview.setImageResource(R.drawable.pin_icon)
        }



    }


    private fun GetDashboard(){
        val intent: Intent
        intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    object PreferenceHelper {
        val ADMIN_PIN = "ADMIN_PIN"

        fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

        inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
            val editMe = edit()
            operation(editMe)
            editMe.apply()
        }

        var SharedPreferences.adminPin
            get() = getString(ADMIN_PIN, "506070")
            set(value) {
                editMe {
                    it.putString(ADMIN_PIN, value)
                }
            }



        var SharedPreferences.clearValues
            get() = { }
            set(value) {
                editMe {
                    it.clear()
                }
            }
    }



}
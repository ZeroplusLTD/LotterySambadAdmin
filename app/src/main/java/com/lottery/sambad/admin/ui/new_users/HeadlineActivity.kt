package com.lottery.sambad.admin.ui.new_users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.databinding.ActivityHeadlineBinding
import com.lottery.sambad.admin.databinding.ActivityNotificationBinding

class HeadlineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.headlineSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.headlineSwitch.text = "Headline On"
                binding.headlineText.setEnabled(true)

            } else {
                binding.headlineSwitch.text = "Headline Off"
                binding.headlineText.setEnabled(false)
            }
        })





    }
}
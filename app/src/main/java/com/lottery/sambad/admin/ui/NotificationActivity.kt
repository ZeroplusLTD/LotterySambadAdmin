package com.lottery.sambad.admin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.databinding.ActivityDisableUsersBinding
import com.lottery.sambad.admin.databinding.ActivityNotificationBinding
import com.lottery.sambad.admin.ui.users_account_info.UsersViewModel
import com.lottery.sambad.admin.ui.users_account_info.UsersViewModelFactory

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = getIntent()
        val myValue = intent.getStringExtra("Token")
        binding.deviceToken.setText(myValue)


    }
}
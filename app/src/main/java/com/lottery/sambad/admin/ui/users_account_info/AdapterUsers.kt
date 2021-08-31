package com.lottery.sambad.admin.ui.users_account_info

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.model.ModelUsers
import com.lottery.sambad.admin.databinding.UsersModelBinding
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import android.widget.Toast

import androidx.core.content.ContextCompat.startActivity
import com.lottery.sambad.admin.ui.NotificationActivity


class AdapterUsers(val context: Context, val list: MutableList<ModelUsers>): RecyclerView.Adapter<AdapterUsers.AdapterUserRecyclerViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterUserRecyclerViewHolder {
        val binding= UsersModelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdapterUserRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterUserRecyclerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class AdapterUserRecyclerViewHolder(val binding: UsersModelBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        fun bind(item: ModelUsers) {
            try {
                if (context is LoggedInUsersActivity) {
                    binding.PhoneNumber.text= item.phone
                    binding.RegisterDate.text= item.registrationDate
                    binding.NotifySendBtn.visibility = View.VISIBLE
                    binding.UrOnlineStatus.visibility = View.VISIBLE
                }else if (context is LoggedOutUsersActivity) {
                    binding.PhoneNumber.text= item.phone
                    binding.RegisterDate.text= item.registrationDate
                    binding.SmsSendBtn.visibility = View.VISIBLE
                    binding.UrOfflineStatus.visibility = View.VISIBLE
                }else if (context is StanderdUsersActivity) {
                    binding.PhoneNumber.text= item.phone
                    binding.RegisterDate.text= item.registrationDate
                    binding.SmsSendBtn.visibility = View.VISIBLE
                    binding.NotifySendBtn.visibility = View.VISIBLE
                    binding.freeUserView.visibility = View.VISIBLE
                }else if (context is PremiumUsersActivity) {
                    binding.PhoneNumber.text= item.phone
                    binding.RegisterDate.text= item.registrationDate
                    binding.NotifySendBtn.visibility = View.VISIBLE
                    binding.SmsSendBtn.visibility = View.VISIBLE
                    binding.paidUserView.visibility = View.VISIBLE
                }else if (context is DisableUsersActivity) {
                    binding.PhoneNumber.text= item.phone
                    binding.RegisterDate.text= item.registrationDate
                    binding.ReviewBtn.visibility = View.VISIBLE
                    binding.UrDisableStatus.visibility = View.VISIBLE
                }else{
                    binding.PhoneNumber.text= item.phone
                    binding.RegisterDate.text= item.registrationDate
                    binding.NotifySendBtn.visibility = View.VISIBLE
                    binding.SmsSendBtn.visibility = View.VISIBLE
                }
                binding.NotifySendBtn.setOnClickListener {
                    val notifyIntent: Intent
                    notifyIntent = Intent(context,NotificationActivity::class.java)
                    notifyIntent.putExtra("Token",list[adapterPosition].token)
                    context.startActivity(notifyIntent)
                }
                binding.SmsSendBtn.setOnClickListener {
                    try {
                        val smsIntent = Intent(Intent.ACTION_VIEW)
                        smsIntent.type = "vnd.android-dir/mms-sms"
                        smsIntent.putExtra("sms_body", item.phone)
                        context.startActivity(smsIntent)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "No SIM Found", Toast.LENGTH_LONG).show()
                    }
                }
                binding.rootClick.setOnClickListener(this)
            } catch (e: Exception) {}
        }

        override fun onClick(v: View?) {
            v?.let {
                val lotteryIntent: Intent

                lotteryIntent = Intent(context,DetailsUsersActivity::class.java)


                when (it.id) {
                    R.id.rootClick -> {

                        lotteryIntent.putExtra("Id",list[adapterPosition].id)
                        lotteryIntent.putExtra("Token",list[adapterPosition].token)
                        lotteryIntent.putExtra("Phone",list[adapterPosition].phone)
                        lotteryIntent.putExtra("paid_license",list[adapterPosition].paid_license)
                        lotteryIntent.putExtra("ac_position",list[adapterPosition].ac_position)
                        lotteryIntent.putExtra("registrationDate",list[adapterPosition].registrationDate)
                        lotteryIntent.putExtra("ActiveStatus",list[adapterPosition].activeStatus)
                        context.startActivity(lotteryIntent)

                    }
                }
            }
        }
    }




}
package com.lottery.sambad.admin.ui.users_account_info

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lottery.sambad.admin.R
import com.lottery.sambad.admin.databinding.ActivityDisableUsersBinding
import com.lottery.sambad.admin.databinding.ActivityLoggedOutUsersBinding
import com.lottery.sambad.admin.model.ModelUsers
import com.lottery.sambad.admin.ui.MyApplication
import com.lottery.sambad.admin.utils.Coroutines
import com.lottery.sambad.admin.utils.MyExtensions.shortToast

class DisableUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisableUsersBinding
    private lateinit var viewModel: UsersViewModel
    private var list: MutableList<ModelUsers> = arrayListOf()
    private lateinit var adapter: AdapterUsers
    private lateinit var layoutManager: LinearLayoutManager
    private var page_number: Int=1
    private var item_count: Int=30
    private lateinit var connectivityManager: ConnectivityManager
    //val CUSTOM_PREF_NAME = "User_data_extra"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDisableUsersBinding.inflate(layoutInflater)
        val factory= UsersViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(UsersViewModel::class.java)
        setContentView(binding.root)

        supportActionBar?.title = "Disabled"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        initAll()

        setupRecyclerView()

    }

    private fun initAll() {
        connectivityManager=getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        //binding.spinKit.visibility= View.GONE
    }

    private fun setupRecyclerView() {
        adapter= AdapterUsers(this,list)
        layoutManager= LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.adapter=adapter
    }
    private fun loadDuplicateLotteryNumber() {
        Coroutines.main {
            try {
                //binding.spinKit.visibility= View.VISIBLE
                val response=viewModel.getUsersList(page_number.toString(),item_count.toString(),"4")
                if (response.isSuccessful && response.code()==200) {
                    //binding.spinKit.visibility= View.GONE
                    if (response.body()!=null) {
                        if (response.body()?.status.equals("success",true)) {
                            val temporaryList=response.body()?.data!!
                            list.addAll(temporaryList)
                            adapter.notifyDataSetChanged()
                            //generateFinalList(temporaryList)
                            /*val Liveuserdb = FirebaseDatabase.getInstance().getReference("ActiveUsers")
                            val prefs = RegisterActivity.PreferenceHelper.customPreference(this, CUSTOM_PREF_NAME)
                            val map: HashMap<String, Any?> = HashMap()
                            map["phone"] = prefs.userPhone
                            map["activity"] = getString(R.string.middle_number)
                            Liveuserdb.child(prefs.userToken!!).setValue(map)*/
                        } else {
                            shortToast("message:- ${response.body()?.message}")
                        }
                    }
                } else {
                    //binding.spinKit.visibility= View.GONE
                }
            } catch (e: Exception) {
                //binding.spinKit.visibility= View.GONE
            }
        }
    }
    override fun onResume() {
        loadDuplicateLotteryNumber()
        super.onResume()
    }
    override fun onPause() {
        list.clear()
        super.onPause()
    }
}
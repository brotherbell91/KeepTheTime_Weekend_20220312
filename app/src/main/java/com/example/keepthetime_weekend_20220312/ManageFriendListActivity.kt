package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityManageFriendListBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageFriendListActivity : BaseActivity() {

    lateinit var binding : ActivityManageFriendListBinding

//    내 친구 목록을 담아줄 그릇
    val mMyFriendList = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_friend_list)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {

//        내 친구 목록에 데이터 채우기.
//         => 서버가 실제로 내려주는 친구목록을 채워보자. (API 통신과 결합)

        getMyFriendListFromServer()

    }

    fun getMyFriendListFromServer(){

        apiList.getRequestFriendList(
            "my"
        ).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })


    }
}
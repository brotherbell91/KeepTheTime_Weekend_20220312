package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.adapters.MyFriendAdapter
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

    lateinit var mAdapter : MyFriendAdapter

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

        mAdapter = MyFriendAdapter(mContext, R.layout.friend_list_item, mMyFriendList)
        binding.myFriendListView.adapter = mAdapter

    }

    fun getMyFriendListFromServer(){

        apiList.getRequestFriendList(
            "my"
        ).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!
                    mMyFriendList.addAll(br.data.friends) //서버가 주는 친구 목록을 > 화면의 ArrayList에 통째로 추가

//                    리스트뷰의 어댑터 설정보다, 목록에 데이터 추가가 더 늦게 이루어질 수도 있다.
                    mAdapter.notifyDataSetChanged() //리스트뷰으 내용물 새로고침

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })


    }
}
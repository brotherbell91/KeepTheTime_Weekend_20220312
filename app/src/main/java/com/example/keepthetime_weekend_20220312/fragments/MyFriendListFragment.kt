package com.example.keepthetime_weekend_20220312.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.SearchUserActivity
import com.example.keepthetime_weekend_20220312.adapters.MyFriendRecyclerAdapter
import com.example.keepthetime_weekend_20220312.databinding.FragmentMyFriendListBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFriendListFragment : BaseFragment() {

    lateinit var binding: FragmentMyFriendListBinding

    val mMyFriendList = ArrayList<UserData>()

    lateinit var mAdapter: MyFriendRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_my_friend_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

        binding.btnAddFriend.setOnClickListener {

            val myIntent = Intent(mContext, SearchUserActivity::class.java)
            startActivity(myIntent)
        }

    }

    override fun setValues() {

        getMyFriendListFromServer()

        mAdapter = MyFriendRecyclerAdapter(mContext, mMyFriendList)
        binding.myFriendRecyclerView.adapter = mAdapter

//        리싸이클러뷰는 어떤 모양으로 표시할건지도 설정해야함.
        binding.myFriendRecyclerView.layoutManager = LinearLayoutManager(mContext) //기본 세로 스크롤 (리스트뷰와 동일)

    }

    fun getMyFriendListFromServer() {

        apiList.getRequestFriendList(
            "my"
        ).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mMyFriendList.addAll( br.data.friends )

//                    리스트뷰처럼, 목록에 변화가 생기면 어댑터의 새로고침
                    mAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }


}
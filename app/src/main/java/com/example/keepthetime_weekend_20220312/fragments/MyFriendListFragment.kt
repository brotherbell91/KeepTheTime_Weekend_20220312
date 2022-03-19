package com.example.keepthetime_weekend_20220312.fragments

import android.os.Bundle
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.adapters.MyFriendRecyclerAdapter
import com.example.keepthetime_weekend_20220312.databinding.FragmentMyFriendListBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
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

    }

    override fun setValues() {

        getMyFriendListFromServer()

        mAdapter = MyFriendRecyclerAdapter(mContext, mMyFriendList)
        binding.myFriendRecyclerView.adapter = mAdapter

    }

    fun getMyFriendListFromServer() {

        apiList.getRequestFriendList(
            "my"
        ).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mMyFriendList.addAll( br.data.friends )

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }


}
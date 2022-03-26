package com.example.keepthetime_weekend_20220312

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keepthetime_weekend_20220312.adapters.StartingPointRecyclerAdapter
import com.example.keepthetime_weekend_20220312.databinding.ActivityManageStartingPointBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.datas.StartingPointData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageStartingPointActivity : BaseActivity() {

    lateinit var binding: ActivityManageStartingPointBinding

    val mStartingPointList = ArrayList<StartingPointData>()

    lateinit var mAdapter: StartingPointRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_starting_point)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        imgAdd.setOnClickListener {

            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        txtTitle.text = "출발지 목록 관리"

        imgAdd.visibility = View.VISIBLE

        getMyStartingPointFromServer()

        mAdapter = StartingPointRecyclerAdapter( mContext, mStartingPointList )
        binding.myStartingPointRecyclerView.adapter = mAdapter
        binding.myStartingPointRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    fun getMyStartingPointFromServer() {

        apiList.getRequestMyStartingPoint().enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                val br = response.body()!!  // JSONObject / JSONArray 등의 중간 형태 skip. 바로 일반 클래스로 담아줌.

                mStartingPointList.addAll( br.data.places )

                mAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }
}
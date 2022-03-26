package com.example.keepthetime_weekend_20220312

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityManageStartingPointBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.datas.StartingPointData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageStartingPointActivity : BaseActivity() {

    lateinit var binding: ActivityManageStartingPointBinding

    val mStartingPointList = ArrayList<StartingPointData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_starting_point)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        txtTitle.text = "출발지 목록 관리"

        getMyStartingPointFromServer()

    }

    fun getMyStartingPointFromServer() {

        apiList.getRequestMyStartingPoint().enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                val br = response.body()!!  // JSONObject / JSONArray 등의 중간 형태 skip. 바로 일반 클래스로 담아줌.

//                ArrayList의 for문 예시
                for ( myPlace in  br.data.places ) {
                    Log.d("내출발장소", myPlace.name)
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }
}
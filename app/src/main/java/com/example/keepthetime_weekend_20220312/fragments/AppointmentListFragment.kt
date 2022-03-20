package com.example.keepthetime_weekend_20220312.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.datas.AppointmentData
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppointmentListFragment : BaseFragment() {

    val mAppointmentList = ArrayList<AppointmentData>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appointment_list,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()

    }


    override fun setupEvents() {
    }

    override fun setValues() {
        super.onResume()

        getMyAppointmentListFromServer() //이 화면으로 돌아올때마다, 내 약속 목록 새로고침 (자동 새로고침)

    }

//    onResume 생명주기 : 이 화면이 나타날때마다 실행.

    fun getMyAppointmentListFromServer(){

        apiList.getRequestMyAppointment().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if(response.isSuccessful){

//                    기존에 들어있던 약속 목록 삭제.
                    mAppointmentList.clear()

//                    서버가 내려준 약속 목록 ArrayList에 등록
                    val br = response.body()!!
                    mAppointmentList.addAll( br.data.appointments )
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


}
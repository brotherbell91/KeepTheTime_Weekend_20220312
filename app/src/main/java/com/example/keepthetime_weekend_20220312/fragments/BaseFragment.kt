package com.example.keepthetime_weekend_20220312.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.keepthetime_weekend_20220312.api.APIList
import com.example.keepthetime_weekend_20220312.api.ServerAPI

//모든 프래그먼트들이 공통적으로 갖는 멤버변수 / 기능을 추가하는 클래스

abstract class BaseFragment : Fragment() {

//    프래그먼트는 requireContext()로 활용하는게 불편함
    lateinit var mContext : Context

//    프래그먼트에서도 서버 통신 수행.
    lateinit var apiList : APIList

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        동작 관련 코드 수행시, mContext 대입
        mContext = requireContext()

//        레트로핏 세팅 => apiList에 대입
        val retrofit = ServerAPI.getRetrofit()
        apiList = retrofit.create(APIList::class.java)

    }

//    모든 프래그먼트들도, 이벤트처리 코드 / 화면 데이터 출력 코드 분리.

    abstract fun setupEvents()
    abstract fun setValues()
}
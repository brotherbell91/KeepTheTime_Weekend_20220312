package com.example.keepthetime_weekend_20220312.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.databinding.FragmentMyProfileBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileFragment : BaseFragment() {

    lateinit var binding : FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile,container,false)
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

//      내 정보를 프래그먼트에서 받아와보자. => 프로필 사진 표시.
        apiList.getRequestMyInfo(ContextUtil.getToken(mContext)).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

//                    프래그먼트에서 id를 붙여둔 이미지뷰를 끌어오는 방법?
//                    프래그먼트의 데이터바인딩 세팅
                    
//                    정보를 받아온 사용자의 프사 > Glide 활용 > 이미지뷰에 반영
                    Glide.with(mContext).load(br.data.user.profile_img).into( binding.imgProfile )
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

    }
}
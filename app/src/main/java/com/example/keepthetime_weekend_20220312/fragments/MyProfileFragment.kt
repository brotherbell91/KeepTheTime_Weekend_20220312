package com.example.keepthetime_weekend_20220312.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.keepthetime_weekend_20220312.ManageFriendListActivity
import com.example.keepthetime_weekend_20220312.ManageStartingPointActivity
import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.SplashActivity
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

        binding.btnManageStartingPointList.setOnClickListener {

            val myIntent = Intent(mContext, ManageStartingPointActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnMyFriendsList.setOnClickListener {

            val myIntent = Intent(mContext, ManageFriendListActivity::class.java)
            startActivity(myIntent)
        }

        binding.btnLogout.setOnClickListener {

//            정말 로그아웃 할건지 ? 확인받기
//            안드로이드x로 선택
            val alert = AlertDialog.Builder(mContext)
                .setTitle("로그아웃")
                .setMessage("정말 로그아웃 하시겠습니까?")
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

//                    실제 로그아웃 처리
//                    로그아웃 : 저장되어있던 토큰값을 삭제. => 서버에 내가 누군지 알려주려면, 다시 로그인이 필요해짐.
//                    토큰값을 새로 받아와야하니까.

//                    토큰값 삭제 : 저장된 토큰값을 ""으로 세팅.

                    ContextUtil.setToken(mContext,"")

//                    화면종료 > 로딩화면으로 보내기.

                    val myIntent = Intent(mContext, SplashActivity::class.java)
                    startActivity(myIntent)

//                    임시코드 requireActivity() 는 myProfileFragment를 들고있는 화면
                    requireActivity().finish()

                })
                .setNegativeButton("취소", null)
                .show()

        }

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
                    binding.txtNickname.text = br.data.user.nick_name
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

    }
}
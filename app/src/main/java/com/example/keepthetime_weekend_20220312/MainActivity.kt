package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.api.APIList
import com.example.keepthetime_weekend_20220312.api.ServerAPI
import com.example.keepthetime_weekend_20220312.databinding.ActivityMainBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

//    binding : 어떤 xml을 접근하는지. 자료형으로 설정.
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setupEvent()
        setValues()
    }

    fun setupEvent(){

        binding.btnLogin.setOnClickListener {

            val inputId = binding.edtId.text.toString()
            val inputpw = binding.edtPassword.text.toString()

//            keepthetime.xyz/로그인 기능에, 아이디 / 비번 보내기

            val myRetrofit = ServerAPI.getRetrofit()
            val myApiList = myRetrofit.create( APIList::class.java)

//            callback 은 retrofit2로 , JSONObject 은 gooogle말고 다른것 선택
            myApiList.postRequestLogin(inputId, inputpw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
//                    로그인 결과가 성공이던 / 실패던 응답 자체는 돌아온 경우.
                    Log.d("응답확인", response.toString())
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//                    아예 물리적으로 연결 실패
                }
            }) //callback 은 retrofit2로 선택, json은 org.json

        }


    }

    fun setValues(){


    }
}
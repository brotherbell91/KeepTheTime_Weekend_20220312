package com.example.keepthetime_weekend_20220312

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.api.APIList
import com.example.keepthetime_weekend_20220312.api.ServerAPI
import com.example.keepthetime_weekend_20220312.databinding.ActivityLoginBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

//    binding : 어떤 xml을 접근하는지. 자료형으로 설정.
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        setupEvents()
        setValues()
    }

    override fun setupEvents(){

        binding.btnSignup.setOnClickListener {

            val myIntent = Intent(this,SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnLogin.setOnClickListener {

            val inputId = binding.edtId.text.toString()
            val inputpw = binding.edtPassword.text.toString()

//            keepthetime.xyz/로그인 기능에, 아이디 / 비번 보내기

//            callback 은 retrofit2로 , JSONObject 은 gooogle말고 다른것 선택
            apiList.postRequestLogin(inputId, inputpw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
//                    로그인 결과가 성공이던 / 실패던 응답 자체는 돌아온 경우.

//                    로그인에 성공까지 했다면, 그 응답의 본문은 BasicResponse 형태로 변화되어 있다.
                    if(response.isSuccessful) {

                        val br = response.body()!! //기본 분석 완료된 BasicResponse를 br변수에 담자.

//                        Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()

//                        data > token 변수 로그로 찍어보기
                        Log.d("토큰", br.data.token)

                        Toast.makeText(mContext, "${br.data.user.nick_name}님, 환영합니다!", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//                    아예 물리적으로 연결 실패
                }
            }) //callback 은 retrofit2로 선택, json은 org.json

        }


    }

    override fun setValues(){


    }
}
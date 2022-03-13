package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivitySignUpBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setupEvents()
        setValues()


    }

    override fun setupEvents() {

        binding.btnEmailCheck.setOnClickListener {

//            입력된 이메일 추출 > 서버의 중복확인 기능에 물어보자.
            val inputEmail = binding.edtEmail.text.toString()

//            실제로 API 기능 사용.
            apiList.getRequestDuplicatedCheck("EMAIL", inputEmail).enqueue(object :Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
//                    response 변수가, 서버의 응답을 들고 있다. => 그 내부 분석.
//                    1) 최종 성공? - 모든 중복을 피해서, 사용해도 되는 경우만.
//                    2) 본문에 들어있는 내용?

                    if(response.isSuccessful) {

                        val br =response.body()!!

                        Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }


            } )

        }

        binding.btnSignUp.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

//            레트로핏 세팅 > 회원가입 진행.
            apiList.putRequestSignUp(inputEmail, inputPw, inputNickname).enqueue( object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

//                        BaseActivity에서 미리 세팅해준 this에 해당하는, mContext 변수 활용.
                        Toast.makeText(mContext, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })

            finish()

        }
    }

    override fun setValues() {
    }
}
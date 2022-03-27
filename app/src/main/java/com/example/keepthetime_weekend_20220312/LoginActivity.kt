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
import com.example.keepthetime_weekend_20220312.utils.ContextUtil
import com.facebook.CallbackManager
import com.kakao.sdk.user.UserApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

//    binding : 어떤 xml을 접근하는지. 자료형으로 설정.
    lateinit var binding : ActivityLoginBinding

    //    페북 로그인 화면에 다녀오면, 할 일을 관리해주는 변수.
    lateinit var mCallbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        setupEvents()
        setValues()
    }

    override fun setupEvents(){

        //        페북 로고가 눌리면 > 페이스북 로그인.
        binding.imgFacebook.setOnClickListener {

//            페북로그인 기능에 관련된 코드 준비가 필요함. (준비 먼저 하고 로그인 실행)

        }

//        카톡 로고가 눌리면 > 카카오 로그인.
        binding.imgKakao.setOnClickListener {

//            카톡 앱이 깔려있으면? 앱으로 로그인, 아니면? 별도로 로그인
            
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)){

                Log.d("카톡로그인", "앱으로 로그인 가능")

                UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->

//                    카톡 앱으로 로그인 되었을 때 할 코드

                    getKakaoUserInfo()

                }

            }
            else{

                Log.d("카톡로그인", "앱으로 로그인 불가 - 별도 로그인 필요")

                UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->

//                    카톡 앱이 없어서, 다른 방식으로 로그인 되었을 때 할 코드

                    getKakaoUserInfo()

                }
                
            }

        }

//     OnCheckedChange 중괄호 있는것 선택
        binding.autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->

//            isChecked변수에, 지금 체크 되었는지? 해제되었는지? 알려줌.
//            Log.d("자동로그인", isChecked.toString()) //오류확인
//            알려주는 Boleen 값을, ContextUtil의 기능 활용해서 저장.

            ContextUtil.setAutoLogin(mContext, isChecked)

        }


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

//                        받아온 토큰값을 기기에 저장 => 나중에 많은 화면에서 활용.
                        ContextUtil.setToken(mContext, br.data.token)

                        Toast.makeText(mContext, "${br.data.user.nick_name}님, 환영합니다!", Toast.LENGTH_SHORT).show()

//                        메인화면으로 이동, 로그인화면 종료
                        val myIntent = Intent(mContext,MainActivity::class.java )
                        startActivity(myIntent)
                        finish()

                    }
                    else {
//                        로그인에 성공 아닌 경우. (비번 틀림, 아이디 틀림 등등..)
//                        BasicResponse 변환 X. => JSONObject로 받아내서 직접 파싱.

                        val jsonObj = JSONObject(  response.errorBody()!!.string()  ) // .toString() 아님!!

                        val message = jsonObj.getString("message")

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//                    아예 물리적으로 연결 실패
                }
            }) //callback 은 retrofit2로 선택, json은 org.json

        }


    }

    override fun setValues(){

        //        페북로그인 - 콜백 관리 기능 초기화.
        mCallbackManager = CallbackManager.Factory.create()

//        저장해둔 자동로그인 여부를, 체크박스의 isChecked 속성에 대입.
        binding.autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }

    //    페북로그인 화면에 다녀오면, 콜백매니저가 처리하도록.

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

//    카톡 앱이건, 다른 방식이건 카카오 로그인이 되었다면 실행할 함수.
//    로그인한 사용자의 고유정보 받아오기

    fun getKakaoUserInfo() {

        UserApiClient.instance.me { user, error ->

//            retrofit 처럼, 카카오가 변수에 모든것을 담아서 내려주는 형태.
            Log.d("카카오로그인", "사용자 id값: ${user!!.id}")
            Log.d("카카오로그인", "사용자 닉네임: ${user!!.kakaoAccount!!.profile!!.nickname}")

//            우리 API서버에 소셜로그인 API 호출. => 성공시 로그인 처리.

            apiList.postRequestSocialLogin(
                "kakao",
                user!!.id.toString(),
                user!!.kakaoAccount!!.profile!!.nickname!!,
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

                        val br = response.body()!!

                        Toast.makeText(
                            mContext,
                            "${br.data.user.nick_name}님 환영합니다!",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token )

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)

                        finish()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }


            })

        }

    }

}
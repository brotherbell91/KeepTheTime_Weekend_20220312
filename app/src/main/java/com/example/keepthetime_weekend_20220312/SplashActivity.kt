package com.example.keepthetime_weekend_20220312

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {

        getKeyHash()

//        API로, 토큰값을 이용해 내 정보 조회

        var isMyInfoOk = false // 내정보는 우선은 안불러와진다고 전제.

        apiList.getRequestMyInfo(ContextUtil.getToken(mContext)).enqueue( object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

//                응답이 성공적으로 돌아왔다면
                if (response.isSuccessful){
//                    내정보가 잘 불러와졌다고 기록.
                    isMyInfoOk = true
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

//        2.5초 후에 내 정보가 불러와졌는지? + 자동로그인을 한다고 했는지? 검사

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

//            자동로그인 한다고 했는지?
            val isAutoLogin = ContextUtil.getAutoLogin(mContext)

//            내 정보가 잘 불러와졌는지? => 이전 코드에서 검사

            
//            둘다 통과하면? 메인으로, 하나라도 틀리면? 로그인으로
            if (isAutoLogin && isMyInfoOk) {

                val myIntent = Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)

            }
            else {
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
            }

        }, 2500)


    }

//    내 앱/컴퓨터의 키 해쉬값 추출 함수

    fun getKeyHash(){

        val info = packageManager.getPackageInfo(
            "com.example.keepthetime_weekend_20220312",
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))

        }
    }

}
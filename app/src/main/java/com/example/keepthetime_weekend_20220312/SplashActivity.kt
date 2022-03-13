package com.example.keepthetime_weekend_20220312

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.keepthetime_weekend_20220312.utils.ContextUtil

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

//        API로, 토큰값을 이용해 내 정보 조회

//        2.5초 후에 내 정보가 불러와졌는지? + 자동로그인을 한다고 했는지? 검사

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

//            자동로그인 한다고 했는지?
            val isAutoLogin = ContextUtil.getAutoLogin(mContext)

//            내 정보가 잘 불러와졌는지?
            val isMyInfoOk = true //임시로 true
            
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
}
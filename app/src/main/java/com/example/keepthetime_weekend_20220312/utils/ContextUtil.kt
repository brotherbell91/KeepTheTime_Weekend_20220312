package com.example.keepthetime_weekend_20220312.utils

import android.content.Context

class ContextUtil {

//    어느 객체가 하는지상관없이, 단순히 저장/조회만 잘 되면 그만인 기능.
//    ContextUtil.기능() 형태로 활용.
    companion object{

//    일종의 메모장 파일 이름. -> 다른 클래스가 볼 필요x
        private val prefName = "KeepTheTimePref"

//    저장할 항목의 이름. (조회할때도 같은 이름 사용)
        private val AUTO_LOGIN = "AUTO_LOGIN"
        private val TOKEN = "TOKEN"

//    해당 항목에 저장 기능 / 조회 기능

//    저장 기능을 setter라고함  => 다른 클래스가 끌어다 사용.

        fun setAutoLogin( context : Context, isAutoLogin : Boolean ){
//            메모장 열땐 context 사용, isAutoLogin은 어떻게 할것인지?
//            메모장을 열고, 변수에 메모장 자체를 담아두자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            pref.edit().putBoolean(AUTO_LOGIN, isAutoLogin).apply()

        }

//    조회 기능 : getter => 다른 클래스가 끌어다 사용.
        fun getAutoLogin( context: Context ) : Boolean {

//            메모장을 열고, 저장된 변수를 리턴하자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

//            저장된 자동로그인 데이터가 없다면 내보내줄 기본값도 설정해야함.
            return pref.getBoolean( AUTO_LOGIN, false )

        }

//        TOKEN 항목에, String값을 저장 / 조회 기능
//           - 메모장이름은 이미 만들어져 있다. 항목명은 x. setter / getter 도 x

        fun setToken( context: Context, token : String) {

            val pref = context.getSharedPreferences( prefName, Context.MODE_PRIVATE)

            pref.edit().putString(TOKEN, token).apply()
        }

        fun getToken( context: Context) : String {

            val pref = context.getSharedPreferences( prefName, Context.MODE_PRIVATE)

            return pref.getString( TOKEN, "")!!

        }
    }

}
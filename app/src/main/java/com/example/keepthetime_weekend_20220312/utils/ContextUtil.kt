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

//    해당 항목에 저장 기능 / 조회 기능

//    저장 기능을 setter라고함  => 다른 클래스가 끌어다 사용.

        fun setAutoLogin( context : Context, isAutoLogin : Boolean ){
//            메모장 열땐 context 사용, isAutoLogin은 어떻게 할것인지?
//            메모장을 열고, 변수에 메모장 자체를 담아두자.
             val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            pref.edit().putBoolean(AUTO_LOGIN, isAutoLogin).apply()


        }


    }

}
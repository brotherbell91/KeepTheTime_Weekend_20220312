package com.example.keepthetime_weekend_20220312.fcm

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCM : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

//        화면에 앱이 켜져있는 상태로 알림이 오면 실행할 코드.

        val title = message.notification!!.title

        Log.d("푸시알림수신", title!!)

//        ex. 토스트로 제목 띄워보기.
//        서비스의 일종. => 백그라운드 처리 => 백그라운드가 UI를 건드리면, 위험동작으로 간주되어 앱 강제 종료됨
//        UI쓰레드에게 토스트 띄우기를 부탁해보자.
//        안드로이드 핸들러
        val myHandler = Handler(Looper.getMainLooper()) //UI쓰레드와 연결되는 핸들러 생성.
        myHandler.post {

            Toast.makeText(this, title!!, Toast.LENGTH_SHORT).show()

        }

    }
}
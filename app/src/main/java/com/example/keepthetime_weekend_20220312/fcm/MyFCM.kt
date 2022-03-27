package com.example.keepthetime_weekend_20220312.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCM : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

//        화면에 앱이 켜져있는 상태로 알림이 오면 실행할 코드.

        val title = message.notification!!.title

        Log.d("푸시알림수신", title!!)

    }
}
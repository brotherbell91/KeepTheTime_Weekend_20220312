package com.example.keepthetime_weekend_20220312.utils

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "1de18bb78fa2462e6cb089145cdca8ff")
    }

}
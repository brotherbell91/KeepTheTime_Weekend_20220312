package com.example.keepthetime_weekend_20220312.api


import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

// keepthetime.xyz 서버에 있는 기능에 접속하는 방법을 명시하는 인터페이스.

interface APIList {

//    로그인 기능 : POST - /user

    @POST("/user")
    fun postRequestLogin(
        @Field("email") id: String,
        @Field("password") pw : String,
    ) : Call<JSONObject> //JSONObject :에러 안나기 위한 임시 문구.
}
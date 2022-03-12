package com.example.keepthetime_weekend_20220312.api


import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

// keepthetime.xyz 서버에 있는 기능에 접속하는 방법을 명시하는 인터페이스.

interface APIList {

    //    로그인 기능 : POST - /user
    @FormUrlEncoded // POST / PUT / PATCH - formData(앱코드 : Field) 에 데이터 첨부시에 필요한 코드
    @POST("/user")
    fun postRequestLogin(
        @Field("email") id: String,
        @Field("password") pw: String,
    ): Call<BasicResponse> //서버의 응답 본문(body)을, BasicResponse 클래스 형태로 자동 변환.

    //    회원가입 기능 : PUT - /user
    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nick: String,
    ): Call<BasicResponse>

}
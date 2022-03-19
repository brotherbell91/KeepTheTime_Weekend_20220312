package com.example.keepthetime_weekend_20220312.api

import android.content.Context
import com.example.keepthetime_weekend_20220312.utils.ContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

//    Retrofit 클래스의 객체가, 서버와 통신을 주고 받는다.
//    하나의 객체만 있으면, 여러번 통신 가능. => 객체를 하나만 유지해서 공유하자.

    companion object {

//        서버통신담당 클래스 : 레트로핏 클래스 객체를 담아줄 변수.

        private var retrofit : Retrofit? = null //초기에는 만들어두지 않는다.

//        어느 서버에서 기능들을 활용할지. 기본 주소
        private val BASE_URL = "https://keepthetime.xyz"

//        레트로핏 객체를 받아내는 기능 (함수)
//        retrofit 변수에 null이 들어있다면? => 새로 Retrofit 객체를 생성.
//        이미 null이 아니라, 실체가 들어있다면 ? => 이미 들어있는 객체를 재활용

        fun getRetrofit( context : Context ) : Retrofit {

            if (retrofit == null ){

//                retrofit 객체 생성시에, 추가 세팅을 해서 생성.

//                모든 API 호출이 이뤄질때, 자동으로 토큰을 첨부하도록.
//                retrofit 변수를 통해서 API통신을 시작하기 직전에, Request 정보를 먼저 가로채자
//                가로챈 Request 정보에서, 무조건 헤더에 토큰을 첨부해두고 나서, 그 뒤로 나머지 작업을 이어가도록

                val interceptor = Interceptor{
                    with(it)  {

//                        기존에 진행하려던 Request에, 헤더 정보를 추가

                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getToken(context))
                            .build()

//                        다시, 원래 하려던 Request 호출을 이어가도록.
                        proceed(newRequest)

                    }
                }

//                만들어낸 인터셉터를 활용하도록, retrofit 변수를 세팅
//                레트로핏이 사용하는, OkHttp의 클라이언트 객체를 수정.

                val myClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL) //기본 주소가 어디인지 세팅
                    .addConverterFactory(GsonConverterFactory.create()) //서버가 주는 json 양식을, 일반 자료형 / 클래스로 쉽게 변환해주는 도구 세팅.
                    .client(myClient) //인터셉터를 부착해둔 클라이언트로 통신하도록
                    .build() //세팅이 모두 끝났으면, Retrofit 객체로 만들어달라.
            }

//            retrofit 변수는 절대 null일 수 없다.

            return retrofit!!

        }

    }


}
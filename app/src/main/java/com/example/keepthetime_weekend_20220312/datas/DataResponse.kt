package com.example.keepthetime_weekend_20220312.datas

class DataResponse(
//    val user : ?? 현재는 skip
    val user : UserData, //로그인 / 회원가입 API가 호출되면 사용할 데이터.
    val token : String,

    val friends : List< UserData >, // 친구목록 API가 호출되면 사용할 데이터.

    val users : List<UserData>, //검색 목록 API가 호출되면 사용할 데이터.
) {
}
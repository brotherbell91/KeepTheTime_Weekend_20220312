package com.example.keepthetime_weekend_20220312.datas

import java.io.Serializable

class AppointmentData(
    val id : Int,
    val user_id : Int,
    val title : String,
    val datetime : String, //실제 내용은 약속 일시 -> 추가 가공 필요
    val start_place : String,
    val start_latitude : Double,
    val start_longitude : Double,
    val place : String,
    val latitude : Double,
    val longitude : Double,
) : Serializable {
}
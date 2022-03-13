package com.example.keepthetime_weekend_20220312.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.example.keepthetime_weekend_20220312.datas.UserData

class MyFriendAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<UserData> // 자바 언어의 다형성 개념 활용 예제
) : ArrayAdapter<UserData>(mContext, resId, mList){
}
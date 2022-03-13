package com.example.keepthetime_weekend_20220312.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.datas.UserData

class MyFriendAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<UserData> // 자바 언어의 다형성 개념 활용 예제
) : ArrayAdapter<UserData>(mContext, resId, mList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if ( tempRow == null ) {

            tempRow = LayoutInflater.from(mContext).inflate(R.layout.friend_list_item,null)

        }

        val row = tempRow!!

        return row
    }
}
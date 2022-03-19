package com.example.keepthetime_weekend_20220312.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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

        val data = mList[position]

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)
        val txtEmail = row.findViewById<TextView>(R.id.txtEmail)

        txtNickname.text = data.nick_name
        txtEmail.text = data.email
        Glide.with(mContext).load(data.profile_img).into(imgProfile)


        return row
    }
}
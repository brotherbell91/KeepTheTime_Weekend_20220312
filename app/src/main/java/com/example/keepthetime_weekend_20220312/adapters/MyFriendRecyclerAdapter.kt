package com.example.keepthetime_weekend_20220312.adapters

import android.content.Context
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.keepthetime_weekend_20220312.R

class MyFriendRecyclerAdapter(
    val mContext : Context,
    val mList : List<UserData> //뷰 홀더 사용하면, resID를 받지 않아도 됨.
) : RecyclerView.Adapter<MyFriendRecyclerAdapter.MyViewHolder>() {

//    클래스 내부의 클래스(inner class) 제작 > MyFriendRecyclerAdapter가 혼자 사용.

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.friend_list_item, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size // 목록의 갯수가 리턴.

}
package com.example.keepthetime_weekend_20220312.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.api.APIList
import com.example.keepthetime_weekend_20220312.api.ServerAPI
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchedUserRecyclerAdapter(
    val mContext: Context,
    val mList: List<UserData>
) : RecyclerView.Adapter<SearchedUserRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = view.findViewById<TextView>(R.id.txtNickname)
        val btnRequestFriend = view.findViewById<Button>(R.id.btnRequestFriend)

        fun bind(data: UserData) {
            Glide.with(mContext).load(data.profile_img).into(imgProfile)

            txtNickname.text = data.nick_name

            btnRequestFriend.setOnClickListener {
//                어댑터 내부에서, 버튼이 눌리면 할 일 설정.

//                서버의 친구 요청 기능 실행.
//                어댑터 내부 : apiList 변수 상속x , 직접 만들어서 사용.

                val retrofit = ServerAPI.getRetrofit(mContext)
                val apiList = retrofit.create(APIList::class.java)

                apiList.postRequestAddFriend(
                    data.id
                ).enqueue(object :Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    }
                })



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.searched_user_list_item, parent, false)

        return MyViewHolder( view )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = mList[position]
        holder.bind(data)

    }

    override fun getItemCount() = mList.size

}
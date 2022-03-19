package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keepthetime_weekend_20220312.adapters.SearchedUserRecyclerAdapter
import com.example.keepthetime_weekend_20220312.databinding.ActivitySearchUserBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserActivity : BaseActivity() {

    lateinit var binding: ActivitySearchUserBinding


    val mSearchedUserList = ArrayList<UserData>()

    lateinit var mAdapter: SearchedUserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSearch.setOnClickListener {

            val inputNickname = binding.edtNickname.text.toString()

//            입력한 닉네임으로, 서버에 해당 닉네임의 사용자가 있는지? 요청

            apiList.getRequestSearchUser(
                inputNickname
            ).enqueue( object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {

//                        기존의 목록이 남아있는 채로, addAll() 하게 되면, 기존 목록이 누적되어 나타남.
//                        기존 목록은 전부 삭제하고, 서버가 주는 데이터 새로 채우기.

                        mSearchedUserList.clear()

                        val br = response.body()!!
                        mSearchedUserList.addAll( br.data.users )

                        mAdapter.notifyDataSetChanged()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            } )

        }

    }

    override fun setValues() {

        mAdapter = SearchedUserRecyclerAdapter( mContext, mSearchedUserList )
        binding.searchedUserRecyclerView.adapter = mAdapter
        binding.searchedUserRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }
}
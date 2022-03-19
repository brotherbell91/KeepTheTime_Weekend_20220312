package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.adapters.MainViewPager2Adapter
import com.example.keepthetime_weekend_20220312.adapters.MainViewPagerAdapter
import com.example.keepthetime_weekend_20220312.databinding.ActivityMainBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var mvp2a : MainViewPager2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

//        바텀 네비게이션의 이벤트 처리.
        binding.mainBottomNav.setOnItemSelectedListener {

//            it 변수 : 선택된 메뉴가 뭔지? 알려줌.
//            it변수의 id값에 따라, 페이지 이동.

            if (it.itemId == R.id.Home){
                binding.mainViewPager2.currentItem = 0
            }
            else{
                binding.mainViewPager2.currentItem = 1
            }

            return@setOnItemSelectedListener true
        }

    }

    override fun setValues() {

        mvp2a = MainViewPager2Adapter(this)

        binding.mainViewPager2.adapter = mvp2a

    }
}
package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
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

        setCustomActionBar()

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

//        바텀 네비게이션의 이벤트 처리.
        binding.mainBottomNav.setOnItemSelectedListener {

//            it 변수 : 선택된 메뉴가 뭔지? 알려줌.
//            it변수의 id값에 따라, 페이지 이동.

            binding.mainViewPager2.currentItem = when ( it.itemId) {
                R.id.Home -> 0
                else -> 1
            }

            return@setOnItemSelectedListener true
        }

//        페이지 이동시 > 바텀 네비게이션 메뉴 선택

        binding.mainViewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){

            //            추상 메쏘드가 아님. 이벤트 처리 함수를 직접 오버라이딩

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)


                binding.mainBottomNav.selectedItemId = when(position) {
                    0 -> R.id.Home
                    else -> R.id.profile
                }

            }

        })

    }

    override fun setValues() {

        mvp2a = MainViewPager2Adapter(this)

        binding.mainViewPager2.adapter = mvp2a

    }

    fun setCustomActionBar(){

        val defaultActionBar = supportActionBar!!
//        defaultActionBar.setDisplayShowCustomEnabled(true) // 위의 코드가 자동완성 안되면 활용.
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        resID라고나오면 R.layout.으로 작성해서 넣으라는 뜻
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)
//        툴바 좌우 여백 없애기
//        툴바 안드로이드x로 선택
        val toolbar = defaultActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)

    }
}
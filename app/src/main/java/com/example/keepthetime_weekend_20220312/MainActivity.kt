package com.example.keepthetime_weekend_20220312

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

//        상속받아온 imgAdd의 이벤트 처리.
        imgAdd.setOnClickListener {

            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)

        }
//        바텀 네비게이션의 이벤트 처리.
        binding.mainBottomNav.setOnItemSelectedListener {

//            it 변수 : 선택된 메뉴가 뭔지? 알려줌.
//            it변수의 id값에 따라, 페이지 이동.

            binding.mainViewPager2.currentItem = when ( it.itemId) {
                R.id.home -> {
                    imgAdd.visibility = View.VISIBLE
                    0
                }
                else ->
                {
                    imgAdd.visibility = View.GONE
                    1
                }

            }

            return@setOnItemSelectedListener true
        }

//        페이지 이동시 > 바텀 네비게이션 메뉴 선택

        binding.mainViewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){

            //            추상 메쏘드가 아님. 이벤트 처리 함수를 직접 오버라이딩

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)


                binding.mainBottomNav.selectedItemId = when(position) {
                    0 -> R.id.home
                    else -> R.id.profile
                }

            }

        })

    }

    override fun setValues() {

        mvp2a = MainViewPager2Adapter(this)

        binding.mainViewPager2.adapter = mvp2a

        //        상속받은 imgBack 숨김처리.

        imgBack.visibility = View.GONE

        imgAdd.visibility = View.VISIBLE


    }
}
package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityViewMapBinding

//도전 과제
//네이버 지도를 화면 가능 띄우기. (라이브러리는 이미 설치 됨)
// - 약속 장소의 좌표로 카메라 이동 / 마커 띄우기

class ViewMapActivity : BaseActivity() {

    lateinit var binding : ActivityViewMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_map)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {
    }
}
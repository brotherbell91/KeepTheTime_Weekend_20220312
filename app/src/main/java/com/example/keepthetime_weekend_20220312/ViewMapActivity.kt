package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityViewMapBinding
import com.example.keepthetime_weekend_20220312.datas.AppointmentData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker

//도전 과제
//네이버 지도를 화면 가득 띄우기. (라이브러리는 이미 설치 됨)
// - 약속 장소의 좌표로 카메라 이동 / 마커 띄우기

class ViewMapActivity : BaseActivity() {

    lateinit var binding : ActivityViewMapBinding

    lateinit var mAppointmentData : AppointmentData //새로추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_map)

        mAppointmentData = intent.getSerializableExtra("map") as AppointmentData

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {

        binding.mapView.getMapAsync {

            val lat = mAppointmentData.latitude

            val lng = mAppointmentData.longitude


            val naverMap = it
//            카메라 이동
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(lat, lng))
            naverMap.moveCamera(cameraUpdate)

//            마커
            val marker = Marker()
            marker.position = LatLng(lat, lng)
            marker.map = naverMap

        }

    }
}
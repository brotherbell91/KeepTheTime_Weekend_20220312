package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityViewMapBinding
import com.example.keepthetime_weekend_20220312.datas.AppointmentData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker

//도전 과제
//네이버 지도를 화면 가득 띄우기. (라이브러리는 이미 설치 됨)
// - 약속 장소의 좌표로 카메라 이동 / 마커 띄우기

class ViewMapActivity : BaseActivity() {

    lateinit var binding : ActivityViewMapBinding

    lateinit var mAppointmentData : AppointmentData //화면에 넘겨준 약속 자체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_map)

        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {

//        약속이름을 화면의 제목으로.
        txtTitle.text = mAppointmentData.title

//        지도 객체 얻어오기

        binding.mapView.getMapAsync {

            val naverMap = it

//            naverMap을 이용해서, 약속 장소 좌표 표시

//            약속 장소 => LatLng 클래스로 저장해두자.

            val latLng = LatLng( mAppointmentData.latitude,  mAppointmentData.longitude )

//            지도 조작 코드

            val cameraUpdate = CameraUpdate.scrollTo( latLng )

            naverMap.moveCamera( cameraUpdate )

            val marker = Marker()
            marker.position = latLng
            marker.map = naverMap

            //            정보창 띄우기

            val infoWindow = InfoWindow()


//            object : 추상클래스(생성자) {  }  => 추상 클래스 객체
//            object : 인터페이스 {   } => 인터페이스는 생성자 X
            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext) {

                override fun getText(p0: InfoWindow): CharSequence {
//                    CharSequence : String으로 생각해도 무방.
                    return mAppointmentData.place
                }

            }

            infoWindow.open(marker)

        }

    }
}
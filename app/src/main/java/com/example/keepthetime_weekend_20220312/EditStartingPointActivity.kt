package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityEditStartingPointBinding
import com.naver.maps.map.overlay.Marker

class EditStartingPointActivity : BaseActivity() {

    lateinit var binding: ActivityEditStartingPointBinding

//    하나의 마커가 계속 위치만 변경. =>  멤버변수
//    처음에는 안 찍혀있게. (마커가 없게) => null 로 초기값

    var pointMarker : Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_starting_point)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSave.setOnClickListener {

            val inputName = binding.edtStartingPointName.text.toString()

            if (inputName.isEmpty()) {

                Toast.makeText(mContext, "출발지 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

        }

    }

    override fun setValues() {

        binding.naverMapView.getMapAsync {

            val naverMap = it

            naverMap.setOnMapClickListener { pointF, latLng ->

//                latLng 변수가 클릭된 좌표. => 마커로 표시.

                if (pointMarker == null) {
                    pointMarker = Marker()
                }

                pointMarker!!.position =  latLng
                pointMarker!!.map = naverMap

            }

        }

    }
}
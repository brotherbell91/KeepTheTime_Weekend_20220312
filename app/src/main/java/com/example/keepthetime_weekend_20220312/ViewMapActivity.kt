package com.example.keepthetime_weekend_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.databinding.ActivityViewMapBinding
import com.example.keepthetime_weekend_20220312.datas.AppointmentData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import java.text.NumberFormat
import java.util.*

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

//            대중교통 길찾기 라이브러리 활용 => 소요 시간 + 비용 정보창 띄우기.

            val odSay = ODsayService.init(mContext, "hvTDdqC5yX4rd5jJykB8m9mL78tTFR64I3ExPwteLxk")

            odSay.requestSearchPubTransPath(
                mAppointmentData.start_longitude.toString(), // 출발지 X좌표 (경도)를 String으로
                mAppointmentData.start_latitude.toString(),
                mAppointmentData.longitude.toString(),  // 도착지 (약속장소) X좌표 (경도)를 String
                mAppointmentData.latitude.toString(),
                null,
                null,
                null,
                object : OnResultCallbackListener {
                    override fun onSuccess(p0: ODsayData?, p1: API?) {
                        // 길찾기 응답이 돌아오면 할 일.

                        val jsonObj = p0!!.json  // 길찾기 응답이 돌아온 JSONObject를 변수에 저장.
                        Log.d("길찾기응답", jsonObj.toString())

//                        jsonObj의 내부에서, => result라는 이름표를 가진 {  } 추출
//                        result가 JSONObject라고 명시 : resultObj로 변수 이름 설정.
                        val resultObj =  jsonObj.getJSONObject("result")

//                        result 안에서, path라는 이름의 [ ] 추출
//                        path가 JSONArray라고 명시 : path"Arr"로 변수 이름 설정.
                        val pathArr = resultObj.getJSONArray("path")

//                        0번칸 (맨 앞칸) 에 있는 경로만 사용 => {  } 추출
                        val firstPathObj = pathArr.getJSONObject(0)

                        Log.d("첫번째경로정보", firstPathObj.toString())

//                        첫 추천 경로의 정보사항 추출
                        val infoObj = firstPathObj.getJSONObject("info")

//                        시간 값 / 요금 값
                        val totalTime = infoObj.getInt("totalTime")
                        val payment = infoObj.getInt("payment")

//                        infoWindow (네이버 지도 기능)에 활용 + 로직 활용
                        val infoWindow = InfoWindow()

                        infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {

                            override fun getContentView(p0: InfoWindow): View {

//                                리스트뷰의 getView 함수와 비슷한 구조 (return 타입 View)
//                                LayoutInflater로 xml을 객체로 가져와서 => 리턴해보자.

                                val view = LayoutInflater.from(mContext).inflate(R.layout.place_info_window_content, null)

//                                view 변수 안에서, id를 가지고 태그들을 찾아서 (findViewById) => 변수에 저장.

                                val txtPlaceName = view.findViewById<TextView>(R.id.txtPlaceName)
                                val txtTotalTime = view.findViewById<TextView>(R.id.txtTotalTime)
                                val txtPayment = view.findViewById<TextView>(R.id.txtPayment)

                                txtPlaceName.text = mAppointmentData.place
                                txtTotalTime.text = "${totalTime}분"
                                txtPayment.text = "${ NumberFormat.getNumberInstance(Locale.KOREA).format(payment) }원"

                                return view

                            }

                        }

                        infoWindow.open(marker)

//                        출발지 ~ 도착지 까지의 경로선 표시.

                        val path = PathOverlay()

//                        어느 점들을 지나도록 하는지, 좌표 목록. => 임시 : 출발지 / 도착지만.
                        val pathPoints = ArrayList<LatLng>()
//                        출발지 먼저 추가
                        val startLatLng = LatLng( mAppointmentData.start_latitude,  mAppointmentData.start_longitude )
                        pathPoints.add( startLatLng )

//                        출발/도착지 사이에, 대중교통의 정거장 좌표들을 전부 추가. => 대중교통 길찾기 API의 또 다른 영역 파싱.
//                        첫번째 경로의 => 이동 경로 세부 목록 파싱

                        val subPathArr = firstPathObj.getJSONArray("subPath")

//                        subPathArr에 들어있는 내용물의 갯수직전까지 반복. (ex. 5개 들어있다 : 0,1,2,3,4번째 추출)
                        for ( i  in  0 until subPathArr.length() ) {

//                            subPath"Arr" 에서, 반복문을 도는 i변수값에 맞는 위치에 있는, JSONObject {  } 추출
                            val subPathObj = subPathArr.getJSONObject(i)

                            Log.d("세부경로", subPathObj.toString())

//                            세부 경로 중에서, 정거장 목록을 주는 세부경로만 추가 파싱.
//                            subPathObj 내부에, "passStopList"라는 이름표의 데이터가 있는지? 확인.
//                            JSONObject의 isNull 함수 : 해당 이름표에 데이터가 없는가? => NOT 연산 : 있는가?

                            if ( !subPathObj.isNull("passStopList") ) {

                                val passStopListObj = subPathObj.getJSONObject("passStopList")
                                Log.d("정거장목록", passStopListObj.toString())

                            }

                        }



//                        도착지 마지막에 추가
                        pathPoints.add( latLng )  // 지도 로딩 초반부에 만든 변수 재활용

                        path.coords = pathPoints
                        path.map = naverMap





                    }

                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }

                }
            )




        }

    }
}
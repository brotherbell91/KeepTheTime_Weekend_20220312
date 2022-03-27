package com.example.keepthetime_weekend_20220312

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.keepthetime_weekend_20220312.adapters.StartingPointSpinnerAdapter
import com.example.keepthetime_weekend_20220312.api.APIList
import com.example.keepthetime_weekend_20220312.databinding.ActivityEditAppointmentBinding
import com.example.keepthetime_weekend_20220312.datas.BasicResponse
import com.example.keepthetime_weekend_20220312.datas.StartingPointData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : BaseActivity() {

    lateinit var binding: ActivityEditAppointmentBinding

    //    선택한 약속일시를 저장하는 Calendar 변수
    val mSelectedDatetimeCal = Calendar.getInstance()  // 현재 일시가 기본 저장. (일시 + 초 + 1/1000초)

    //    로딩이 완료된 네이버맵을 담을 변수.
    var mNaverMap : NaverMap? = null // 처음에는 지도도 불러지지 않은 상태.

    //    선택한 출발지 자체를 저장할 변수
    var mSelectedStartPoint : StartingPointData? = null // 처음에는 출발지선택 X.

    //    출발지를 띄워줄 마커.
    var mStartMarker: Marker? = null  // 하나의 마커만 만들어서, 출발지를 변경할때마다 위치만 변경되게.

    //    지도에서 클릭한 목적지 좌표.
    var mAppointmentLatLng : LatLng? = null // 지도에서 클릭한 좌표. 처음에는 아직 없다.

    //    지도에 띄워줄 목적지 표시 마커.
    var myMarker : Marker? = null  // 처음에는 목적지 마커도 없는 상태.

    //    내가 만들어둔 출발지 목록 List
    val mStartingPointList = ArrayList<StartingPointData>()

    lateinit var mStartingPointSpinnerAdapter: StartingPointSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        스피너의 아이템 선택 이벤트 처리. (출발지 변경시 대응)

        binding.startingPointSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                mSelectedStartPoint = mStartingPointList[position]

//                선택한 출발지 > 지도의 빨간 마커 위치 이동. > naverMap변수를 받아내야 사용 가능.
//                출발/도착지 다시 그리기. 분리해둔 호출.

                setStartAndEndToNaverMap()  // 지도 로딩보다 먼저 실행된다면?

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }


//        지도 / 스크롤뷰의 상하 스크롤이 겹쳐서 지도에 문제 발생.
//        해결책 : 지도 위에 텍스트뷰를 덮어두고, 해당 텍스트뷰에 손이 닿으면 (touch) => 스크롤뷰의 스크롤 기능을 일시정지.

        binding.txtScrollHelp.setOnTouchListener { view, motionEvent ->

            binding.scrollView.requestDisallowInterceptTouchEvent(true)


//            리턴 처리 필요 : 손이 닿아도 밑에 깔린 지도의 이벤트도 실행.
            return@setOnTouchListener false

        }


        binding.txtDate.setOnClickListener {

//            날짜가 선택되면 할 일 저장

            val dsl = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

//                    year, month, dayOfMonth => 달력을 통해서 선택한 일자 정보.
//                    Toast.makeText(mContext, "${year}년 ${month}월 ${dayOfMonth}일", Toast.LENGTH_SHORT).show()

//                    선택된 일시를 저장할 변수에, 연/월/일 세팅.
                    mSelectedDatetimeCal.set(year, month, dayOfMonth)

//                    약속 일자 텍스트뷰의 문구를 "3월 5일" 형태로 가공해서 출력.
//                    Calendar(내부의 Date) 를 => String으로 가공 전문 클래스 (SimpleDateFormat) 활용.

                    val sdf = SimpleDateFormat("M월 d일")

//                    새 양식 : 2022-03-05 양식
//                   val sdf = SimpleDateFormat("yyyy-MM-dd")

//                    sdf로 format해낸 String을, txtDate의 문구로 반영
                    binding.txtDate.text =  sdf.format( mSelectedDatetimeCal.time )

                }

            }


//            실제로 달력 팝업 띄우기.

//            선택한 일시 (기본값 : 현재일시) 의 연/월/일을 띄워보자.

            val dpd = DatePickerDialog(
                mContext,
                dsl,
                mSelectedDatetimeCal.get( Calendar.YEAR ),  // 선택일시의 년도만 배치.
                mSelectedDatetimeCal.get( Calendar.MONTH ),
                mSelectedDatetimeCal.get( Calendar.DAY_OF_MONTH )
            ).show()

            Log.d("선택월", mSelectedDatetimeCal.get( Calendar.MONTH ).toString())

        }

        binding.txtTime.setOnClickListener {

            val tsl = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {

//                    선택된 일시에, 시간/분 저장 => 시간 항목에 hourOfDay, 분 항목에 minute
                    mSelectedDatetimeCal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    mSelectedDatetimeCal.set(Calendar.MINUTE,  minute)

//                    txtTime의 문구를 "오후 7시 5분" 양식으로 가공 => SimpleDateFormat 사용
                    val sdf = SimpleDateFormat("a h시 m분")

                    binding.txtTime.text = sdf.format( mSelectedDatetimeCal.time ) // Date형태인 time 변수 활용.

                }

            }

            val tpd = TimePickerDialog(
                mContext, // 어느 화면?
                tsl,
                12,
                30,
                false // 시계가 24시간 기준? 12시간 기준?
            ).show()

        }


        binding.btnSave.setOnClickListener {

//            입력한 값들 추출 => 서버에 전송

            val inputTitle = binding.edtTitle.text.toString()

//            받아낸 inputTile의 내용이 비어있다면? => 토스트로 제목 입력 안내. => 지금의 이벤트 처리 강제 종료.

            if (inputTitle.isEmpty()) {

                Toast.makeText(mContext, "제목을 입력해야 합니다.", Toast.LENGTH_SHORT).show()
//                실행중인 함수 강제 종료 => 결과 임의 설정.
                return@setOnClickListener

            }

//            약속 일시 가공전에, 일자 / 시간 모두 선택했는지 체크. 선택하지 않은 항목이 있다면 안내 + 함수 강제 종료.

            if (binding.txtDate.text == "약속 일자" || binding.txtTime.text == "약속 시간") {
                Toast.makeText(mContext, "일시를 모두 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            약속 일시 : mSelectedDatetimeCal 의 일시를 => "2022-03-20 14:19:50" 형태로 가공해서 첨부.

            val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val serverDateTimeStr = serverFormat.format( mSelectedDatetimeCal.time ) // 첨부할 약속 일시

            val inputPlaceName = binding.edtPlaceName.text.toString()

            if (inputPlaceName.isEmpty()) {
                Toast.makeText(mContext, "약속 장소 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            네이버 지도에 마커로 찍어둔 장소 > 서버에 전송?

//            myMarker가 실제로 만들어져있는지? 그렇지 않다면 장소 입력 안내 + 함수 종료.
            if (myMarker == null) {
                Toast.makeText(mContext, "지도를 클릭해서, 약속 장소를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            내가 찍어둔 마커가 있다고 전제하고 코딩.

            val lat = myMarker!!.position.latitude  // 찍힌 마커의 위도 추출.
            val lng = myMarker!!.position.longitude // 찍힌 마커의 경도 추출.


//            출발지 목록 Spinner에서, 어떤 출발지를 선택했는지 받아오자. => 출발지 정보로 서버에 첨부.

//            스피너의 선택 위치 추출
            val selectedPosition = binding.startingPointSpinner.selectedItemPosition
//            해당 위치에 맞는 출발지 데이터 가져오기
            val selectedStartingPoint =  mStartingPointList[selectedPosition]


//            서버에 파라미터값들 전송. (API 호출)

            apiList.postRequestAddAppointment(
                inputTitle,
                serverDateTimeStr,
                selectedStartingPoint.name,
                selectedStartingPoint.latitude,
                selectedStartingPoint.longitude,
                inputPlaceName,
                lat,
                lng
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {
//                        무조건 성공으로 처리. 화면 종료

                        Toast.makeText(mContext, "약속을 등록했습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }


            })

        }


    }

    override fun setValues() {

//        지도 객체를 얻어오면, 출발지/도착지 정보 활용, 별개의 함수 실행.
        binding.mapView.getMapAsync {

//            불러진 지도를 멤버변수에 저장.
            mNaverMap = it


//            네이버 지도의 클릭 이벤트

            mNaverMap!!.setOnMapClickListener { pointF, latLng ->

//                클릭된 좌표는, 목적지로 설정됨.
//                목적지를 멤버변수로 만들어서 => latLng를 그 목적지로 설정.
                mAppointmentLatLng = latLng

//                출발/도착지 그림을 그려주는 함수 재실행.
                setStartAndEndToNaverMap()


            }

//            지도가 불러지고 나서, 출발/도착지 새로 그리기
            setStartAndEndToNaverMap()

        }

        getMyStartingPointFromServer()

        mStartingPointSpinnerAdapter = StartingPointSpinnerAdapter(mContext, R.layout.starting_point_list_item, mStartingPointList)
        binding.startingPointSpinner.adapter = mStartingPointSpinnerAdapter

    }

//   네이버 지도를 가지고, 출발지/도착지 등을 그려주는 함수.

    fun setStartAndEndToNaverMap() {

//        혹시 지도가 안불러졌는지? 밑의 코드 실행 X. (안정성 보강)
//        스피너 이벤트처리 때문에, 지도로딩보다 먼저 실행되었는가?
        if (mNaverMap == null) {
            return
        }

//            mNaverMap은 null 아니다.
        val naverMap = mNaverMap!!

//        출발지가 선택되지 않았는지?
        if (mSelectedStartPoint == null) {
            return
        }

//            기본 지도의 시작 화면 : 서울시청. => 네이버지도의 시작 좌표 : 선택한 도착지 좌표

//        출발지 좌표 변수
        val startLatLng = LatLng(  mSelectedStartPoint!!.latitude, mSelectedStartPoint!!.longitude )

        val cameraUpdate =  CameraUpdate.scrollTo( startLatLng )
        naverMap.moveCamera( cameraUpdate )

//            출발지 위치에 마커를 찍자.
//        아직 마커가 없을때만 생성.

        if (mStartMarker == null) {
            mStartMarker = Marker()
        }

        mStartMarker!!.position = startLatLng
        mStartMarker!!.map = naverMap

//            마커 색상 변경
        mStartMarker!!.icon = MarkerIcons.BLACK // 이 위에 원하는 색 커스텀
        mStartMarker!!.iconTintColor = Color.parseColor("#FF0000") // 안드로이드가 주는 색

//            마커 크기 변경

        mStartMarker!!.width = 50
        mStartMarker!!.height = 80


//        출발지 세팅이 끝나면, 도착지도 있는지 검사.
//        도착지가 있어야 도착 관련 정보도 그려주자.
        if (mAppointmentLatLng == null) {
            return
        }

//        카메라도, 도착지로 다시 옮기자.
        val cameraUpdate2 = CameraUpdate.scrollTo(mAppointmentLatLng!!)
        naverMap.moveCamera(cameraUpdate2)

//        도착지 마커도 없으면 생성
        if (myMarker == null) {
            myMarker = Marker()
        }

//        위치 이동
        myMarker!!.position = mAppointmentLatLng!!
        myMarker!!.map = naverMap

//        출발지 / 도착지가 모두 반영되는 구조 완성.
//        길찾기 API 호출 => 결과 분석, 화면에 추가 반영. (선 긋기 / 정보 표시)

        val odSay = ODsayService.init(mContext, "hvTDdqC5yX4rd5jJykB8m9mL78tTFR64I3ExPwteLxk+RS7nNQ7RToDs9FjME")
        odSay.requestSearchPubTransPath(
            mSelectedStartPoint!!.longitude.toString(),
            mSelectedStartPoint!!.latitude.toString(),
            mAppointmentLatLng!!.longitude.toString(),
            mAppointmentLatLng!!.latitude.toString(),
            null,
            null,
            null,
            object : OnResultCallbackListener {
                override fun onSuccess(p0: ODsayData?, p1: API?) {

                    val jsonObj = p0!!.json

                    val resultObj = jsonObj.getJSONObject("result")

                    val pathArr = resultObj.getJSONArray("path")

                    val firstPathObj = pathArr.getJSONObject(0)

//                    정보 항목 추출 > InfoWindow 띄우기 (도착지의 마커에 띄우기)

                    val infoObj = firstPathObj.getJSONObject("info")

//                    실제 데이터들은 Obj / Arr 등의 이름을 덧붙이지 않음. (강사 개인 취향)
                    val totalTime = infoObj.getInt("totalTime")
                    val payment = infoObj.getInt("payment")

//                    네이버 지도의 정보창 기능에 연동.

                    val infoWindow = InfoWindow()

                    infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {
                        override fun getContentView(p0: InfoWindow): View {

                            val view = LayoutInflater.from(mContext).inflate(R.layout.place_info_window_content, null)

                            val txtPlaceName = view.findViewById<TextView>(R.id.txtPlaceName)
                            val txtTotalTime = view.findViewById<TextView>(R.id.txtTotalTime)
                            val txtPayment = view.findViewById<TextView>(R.id.txtPayment)

                            txtPlaceName.text = binding.edtPlaceName.text.toString()

                            txtTotalTime.text = "${totalTime}분 소요"

                            txtPayment.text = "${ NumberFormat.getNumberInstance(Locale.KOREA).format(payment) }원"

                            return view

                        }

                    }

                    infoWindow.open(myMarker!!) // 도착지 마커에 정보창 띄우기

//                    첫번째 경로의 > 세부 경로 파싱 > 경로선 기능으로 그려주기.

                    val subPathArr = firstPathObj.getJSONArray("subPath")

                    for (i  in  0 until subPathArr.length()) {

                        val subPathObj = subPathArr.getJSONObject(i)

                        if (!subPathObj.isNull("passStopList")) {

                            val passStopListObj = subPathObj.getJSONObject("passStopList")

                            val stationsArr = passStopListObj.getJSONArray("stations")

                            for (j in  0 until stationsArr.length()) {

                                val stationObj = stationsArr.getJSONObject(j)

                                val stationLat = stationObj.getString("y").toDouble()
                                val stationLng = stationObj.getString("x").toDouble()

//                                정거장 좌표를 네이버 좌표체계로 만들자.
                                val stationLatLng = LatLng( stationLat, stationLng )

                            }

                        }

                    }


                }

                override fun onError(p0: Int, p1: String?, p2: API?) {

                }

            }
        )



    }

//    내 출발지 목록이 어떤것들이 있는지 불러오자.

    fun getMyStartingPointFromServer() {

        apiList.getRequestMyStartingPoint().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                val br = response.body()!!

                mStartingPointList.addAll( br.data.places )

                mStartingPointSpinnerAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}
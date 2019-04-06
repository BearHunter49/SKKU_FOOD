package com.example.skku_food.menuActivity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.skku_food.R
import com.example.skku_food.myMethod.MyPermission
import kotlinx.android.synthetic.main.activity_map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapActivity : AppCompatActivity(), MapView.CurrentLocationEventListener {

    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val name = intent.getStringExtra("name")
        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")

        // Map 초기 설정
        val marker = MapPOIItem()
        marker.apply {
            itemName = name
            tag = 0
            mapPoint = MapPoint.mapPointWithGeoCoord(latitude.toDouble(), longitude.toDouble())
            markerType = MapPOIItem.MarkerType.RedPin
            isShowDisclosureButtonOnCalloutBalloon = false
        }
        map_view.apply {
            mapType = MapView.MapType.Standard
            setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude.toDouble(), longitude.toDouble()), true)
            setZoomLevelFloat(0.5f, true)
            addPOIItem(marker)
            setCurrentLocationEventListener(this@MapActivity)
        }


        // GPS Button
        fab_btn.setOnClickListener {

            // Re - Check!
            val chk = MyPermission.myPermissionCheck(this)
            if (!chk){
                MyPermission.myRequestPermission(chk, this)
            }
            else{
                // initial state
                if (flag == 0){
                    fab_btn.supportBackgroundTintList = getColorStateList(R.color.fab_clicked)
                    map_view.apply {
                        // Custom Marker
                        setCustomCurrentLocationMarkerTrackingImage(R.drawable.ic_custom_arrow, MapPOIItem
                            .ImageOffset(28, 28))
                        setCustomCurrentLocationMarkerDirectionImage(R.drawable.ic_custom_direction, MapPOIItem
                            .ImageOffset(65, 65))

                        currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
                        setZoomLevelFloat(0.5f, true)
                    }
                    flag = 1
                }
                // clicked state
                else{
                    fab_btn.supportBackgroundTintList = getColorStateList(R.color.fab_nonclick)
                    map_view.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
                    map_view.setShowCurrentLocationMarker(false)
                    flag = 0
                }
            }
        }


    }

    // Permission Result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MyPermission.PERMISSION_CODE){
            if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            }
            else{
                Toast.makeText(this, "위치 권한을 허용 하셔야 합니다!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
        map_view.setShowCurrentLocationMarker(false)
    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
    }

    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
    }
}

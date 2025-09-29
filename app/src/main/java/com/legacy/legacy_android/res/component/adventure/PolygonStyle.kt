package com.legacy.legacy_android.res.component.adventure

import com.google.android.gms.maps.model.LatLng
import kotlin.math.floor

data class Block(
    val blockId: String,
    val latitude: Double,
    val longitude: Double
)

object PolygonStyle {

    // 상수
    private val latPerPixel = 0.000724
    private val lonPerPixel = 0.000909
    private val defaultStrokeWidthToLatitude = 0.00000905
    private val defaultStrokeWidthToLongitude = 0.0000113625
    private val koreaTopLeftCorner = LatLng(43.00268544185012, 124.27407423789127)

    // 격자에 맞춘 TopLeft 좌표 반환
    fun getSortedTopLeft(latitude: Double, longitude: Double): LatLng {
        val subX = floor((koreaTopLeftCorner.latitude - latitude) / latPerPixel)
        val subY = floor((longitude - koreaTopLeftCorner.longitude) / lonPerPixel)
        val sortedLat = koreaTopLeftCorner.latitude - (latPerPixel * subX)
        val sortedLng = koreaTopLeftCorner.longitude + (lonPerPixel * subY)
        return LatLng(sortedLat, sortedLng)
    }

    fun getGridKey(latitude: Double, longitude: Double): Pair<Int, Int> {
        val subX = floor((koreaTopLeftCorner.latitude - latitude) / latPerPixel).toInt()
        val subY = floor((longitude - koreaTopLeftCorner.longitude) / lonPerPixel).toInt()
        return Pair(subX, subY)
    }


    // 해당 좌표 기준 사각형 polygon 반환
    fun getRectanglePoints(topLeft: LatLng): List<LatLng> {
        return listOf(
            LatLng(topLeft.latitude - defaultStrokeWidthToLatitude, topLeft.longitude + defaultStrokeWidthToLongitude),
            LatLng(
                topLeft.latitude - defaultStrokeWidthToLatitude,
                topLeft.longitude + lonPerPixel - defaultStrokeWidthToLongitude
            ),
            LatLng(
                topLeft.latitude - latPerPixel + defaultStrokeWidthToLatitude,
                topLeft.longitude + lonPerPixel - defaultStrokeWidthToLongitude
            ),
            LatLng(
                topLeft.latitude - latPerPixel + defaultStrokeWidthToLatitude,
                topLeft.longitude + defaultStrokeWidthToLongitude
            )
        )
    }

    fun getPolygonPointsFromLocation(latitude: Double, longitude: Double): List<LatLng> {
        val topLeft = getSortedTopLeft(latitude, longitude)
        return getRectanglePoints(topLeft)
    }

}

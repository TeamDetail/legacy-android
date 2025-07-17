package com.legacy.legacy_android.res.component.adventure

import com.google.android.gms.maps.model.LatLng
import com.legacy.legacy_android.feature.network.block.Get.GetBlockResponse
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

    // 위도/경도 → 격자 polygon 좌표 반환
    fun getPolygonPointsFromLocation(latitude: Double, longitude: Double): List<LatLng> {
        val topLeft = getSortedTopLeft(latitude, longitude)
        return getRectanglePoints(topLeft)
    }

    fun isPointInsideBlock(latitude: Double, longitude: Double, blockLatitude: Double, blockLongitude: Double): Boolean {
        val blockTopLeft = getSortedTopLeft(blockLatitude, blockLongitude)
        val blockPoints = getRectanglePoints(blockTopLeft)

        val minLat = blockPoints.minOf { it.latitude }
        val maxLat = blockPoints.maxOf { it.latitude }
        val minLng = blockPoints.minOf { it.longitude }
        val maxLng = blockPoints.maxOf { it.longitude }

        return latitude >= minLat && latitude <= maxLat && longitude >= minLng && longitude <= maxLng
    }

    fun isPointInsideAnyBlock(latitude: Double, longitude: Double, blocks: List<GetBlockResponse>): Boolean {
        return blocks.any { block ->
            isPointInsideBlock(latitude, longitude, block.latitude, block.longitude)
        }
    }
}

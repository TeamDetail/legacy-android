package com.legacy.legacy_android.res.component.adventure

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polygon

@Composable
fun Ruins(
    centerLatitude: Double,
    centerLongitude: Double,
    ruinsId: Long,
    sizeDegrees: Double = 0.001
) {
    val northEast = LatLng(centerLatitude + sizeDegrees, centerLongitude + sizeDegrees)
    val southEast = LatLng(centerLatitude - sizeDegrees, centerLongitude + sizeDegrees)
    val southWest = LatLng(centerLatitude - sizeDegrees, centerLongitude - sizeDegrees)
    val northWest = LatLng(centerLatitude + sizeDegrees, centerLongitude - sizeDegrees)
    Polygon(
        fillColor = androidx.compose.ui.graphics.Color(0x60FF0000),
        points = listOf(northEast, southEast, southWest, northWest),
        strokeColor = androidx.compose.ui.graphics.Color.Red,
        strokeWidth = 5f
    )
}

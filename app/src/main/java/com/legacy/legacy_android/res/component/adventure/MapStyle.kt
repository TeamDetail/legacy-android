package com.legacy.legacy_android.res.component.adventure

import com.google.android.gms.maps.model.MapStyleOptions

class MapStyle {
    val mapStyle = MapStyleOptions(
        """
        [
          {
            "featureType": "administrative",
            "elementType": "labels",
            "stylers": [{"visibility": "off"}]
          },
          {
            "featureType": "poi",
            "elementType": "all",
            "stylers": [{"visibility": "off"}]
          },
          {
            "featureType": "transit",
            "elementType": "all",
            "stylers": [{"visibility": "off"}]
          }
        ]
        """.trimIndent()
    )
}

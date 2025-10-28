package com.legacy.legacy_android.feature.network.user

import com.google.gson.annotations.SerializedName

data class InventoryItem(
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("itemType")
    val itemType: String,
    @SerializedName("itemName")
    val itemName: String,
    @SerializedName("itemDescription")
    val itemDescription: String,
    @SerializedName("itemCount")
    val itemCount: Int
)
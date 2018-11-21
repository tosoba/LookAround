package com.example.data.api.model

import com.google.gson.annotations.SerializedName

class ReverseGeocodingResponse(
    val city: String?,

    @SerializedName("staddress")
    val address: String?,

    @SerializedName("stnumber")
    val streetNumber: String?
) {
    val isValid: Boolean
        get() = city != null && address != null

    val formattedAddress: String
        get() = "${address!!.toLowerCase()} ${city!!.toLowerCase()}"
}
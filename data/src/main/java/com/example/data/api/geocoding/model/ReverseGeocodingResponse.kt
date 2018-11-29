package com.example.data.api.geocoding.model

import com.example.data.util.ext.eachWordCapitalized
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
        get() = if (isValid)
            "${address!!.eachWordCapitalized} ${if (streetNumber != null) "$streetNumber, ${city!!.eachWordCapitalized}" else city!!.eachWordCapitalized}"
        else throw IllegalStateException("Invalid response")
}


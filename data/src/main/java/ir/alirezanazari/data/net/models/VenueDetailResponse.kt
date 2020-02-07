package ir.alirezanazari.data.net.models


import com.google.gson.annotations.SerializedName
import ir.alirezanazari.data.net.models.detail.Response

data class VenueDetailResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: Response
)
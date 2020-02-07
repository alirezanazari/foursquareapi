package ir.alirezanazari.data.net.models


import com.google.gson.annotations.SerializedName
import ir.alirezanazari.data.net.models.explore.Response

data class ExploreVenuesResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: Response
)
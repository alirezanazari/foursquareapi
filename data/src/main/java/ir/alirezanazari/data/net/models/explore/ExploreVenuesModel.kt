package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class ExploreVenuesModel(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: Response
)
package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("formattedAddress")
    val formattedAddress: List<String>
)
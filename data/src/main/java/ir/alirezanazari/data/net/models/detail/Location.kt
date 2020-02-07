package ir.alirezanazari.data.net.models.detail


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("cc")
    val cc: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("formattedAddress")
    val formattedAddress: List<String>
)
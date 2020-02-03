package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("venue")
    val venue: Venue,
    @SerializedName("referralId")
    val referralId: String
)
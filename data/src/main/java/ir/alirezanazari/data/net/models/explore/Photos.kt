package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("count")
    val count: Int,
    @SerializedName("groups")
    val groups: List<PhotoGroup>
)
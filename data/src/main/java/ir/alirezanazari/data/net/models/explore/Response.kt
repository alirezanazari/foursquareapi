package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("groups")
    val groups: List<Group>
)
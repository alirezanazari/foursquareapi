package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class PhotoGroup(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("items")
    val items: List<ItemPhotoGroup>
)
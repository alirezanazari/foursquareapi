package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("items")
    val items: List<Item>
)
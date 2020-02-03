package ir.alirezanazari.data.net.models.explore


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name")
    val name: String,
    @SerializedName("icon")
    val icon: Icon
)
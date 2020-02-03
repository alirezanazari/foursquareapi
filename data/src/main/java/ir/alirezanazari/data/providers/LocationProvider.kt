package ir.alirezanazari.data.providers

interface LocationProvider {

    suspend fun isLocationChanged(lastLat: Double, lastLng: Double):Boolean
    suspend fun getLocationLatLng(): String

}
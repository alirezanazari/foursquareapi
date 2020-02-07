package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.data.net.models.ExploreVenuesResponse
import ir.alirezanazari.data.net.models.VenueDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("venues/explore")
    fun getRecommendedVenue(
        @Query("ll") latLng: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int ,
        @Query("sortByDistance") sort: Int = 1 //0 is false and 1 sorted result
    ): Single<ExploreVenuesResponse>

    @GET("venues/{id}")
    fun getVenueById(
        @Path("id") id: String
    ): Single<VenueDetailResponse>
}
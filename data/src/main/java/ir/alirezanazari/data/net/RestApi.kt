package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.data.net.models.explore.ExploreVenuesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("venues/explore")
    fun getRecommandedVenue(
        @Query("ll") latLng: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<ExploreVenuesModel>

}
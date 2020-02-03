package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.domain.entity.VenueEntity

interface NetworkDataManager {

    fun getRecommandedVenue(latLng: String, limit: Int, offset: Int): Single<List<VenueEntity>>

}
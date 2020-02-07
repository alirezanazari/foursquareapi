package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.domain.entity.VenueDetailEntity
import ir.alirezanazari.domain.entity.VenueEntity

interface NetworkDataManager {

    fun getRecommendedVenue(latLng: String, limit: Int, offset: Int): Single<List<VenueEntity>>
    fun getVenueById(id: String): Single<VenueDetailEntity>
}
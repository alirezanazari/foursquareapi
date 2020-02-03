package ir.alirezanazari.data.repository

import io.reactivex.Single
import ir.alirezanazari.data.net.NetworkDataManager
import ir.alirezanazari.domain.entity.VenueEntity
import ir.alirezanazari.domain.entity.VenueInputParams
import ir.alirezanazari.domain.repository.VenueRepository

class VenueRepositoryImpl(
    private val net: NetworkDataManager
) : VenueRepository {

    override fun getNearVenues(param: VenueInputParams): Single<List<VenueEntity>> {
        return net.getRecommandedVenue(param.latLng, param.limit, param.offset)
    }

}
package ir.alirezanazari.domain.repository

import io.reactivex.Single
import ir.alirezanazari.domain.entity.VenueDetailEntity
import ir.alirezanazari.domain.entity.VenueEntity
import ir.alirezanazari.domain.entity.VenueInputParams

interface VenueRepository {

    fun getNearVenues(param: VenueInputParams): Single<List<VenueEntity>>
    fun getVenueById(id: String): Single<VenueDetailEntity>

}
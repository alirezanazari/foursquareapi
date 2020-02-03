package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.data.net.models.explore.Item
import ir.alirezanazari.domain.entity.VenueEntity

class NetworkDataManagerImpl(
    private val api: RestApi
) : NetworkDataManager {

    override fun getRecommandedVenue(
        latLng: String,
        limit: Int,
        offset: Int
    ): Single<List<VenueEntity>> {
        return api.getRecommandedVenue(latLng, limit, offset).map { venueModel ->
            venueModel.response.groups[0].items.map { convertResponseToVenueEntity(it) }
        }
    }

    private fun convertResponseToVenueEntity(it: Item): VenueEntity {
        return VenueEntity(
            it.venue.id,
            it.venue.name,
            it.venue.location.lat,
            it.venue.location.lng,
            it.venue.location.address,
            it.venue.location.distance,
            it.venue.categories[0].name,
            "${it.venue.categories[0].icon.prefix}${it.venue.categories[0].icon.suffix}",
            "${it.venue.photos.groups[0].items[0].prefix}${it.venue.photos.groups[0].items[0].suffix}"
        )
    }

}
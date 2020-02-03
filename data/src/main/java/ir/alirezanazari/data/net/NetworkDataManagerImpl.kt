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

    private fun convertResponseToVenueEntity(item: Item): VenueEntity {
        return VenueEntity(
            item.venue.id,
            item.venue.name,
            item.venue.location.lat,
            item.venue.location.lng,
            item.venue.location.address,
            item.venue.location.distance,
            if (item.venue.categories.isNotEmpty()) item.venue.categories[0].name else "",
            if (item.venue.categories.isNotEmpty()) "${item.venue.categories[0].icon.prefix}${item.venue.categories[0].icon.suffix}" else "",
            if (item.venue.photos.groups.isNotEmpty() && item.venue.photos.groups[0].items.isNotEmpty()) "${item.venue.photos.groups[0].items[0].prefix}${item.venue.photos.groups[0].items[0].suffix}" else ""
        )
    }

}
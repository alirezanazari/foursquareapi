package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.data.net.models.detail.Venue
import ir.alirezanazari.data.net.models.explore.Item
import ir.alirezanazari.domain.entity.VenueDetailEntity
import ir.alirezanazari.domain.entity.VenueEntity

class NetworkDataManagerImpl(
    private val api: RestApi
) : NetworkDataManager {

    override fun getRecommendedVenue(
        latLng: String,
        limit: Int,
        offset: Int
    ): Single<List<VenueEntity>> {
        return api.getRecommendedVenue(latLng, limit, offset).map { venueModel ->
            venueModel.response.groups[0].items.map { convertExploreResponseToVenueEntity(it) }
        }
    }

    override fun getVenueById(id: String): Single<VenueDetailEntity> {
        return api.getVenueById(id).map {
            convertDetailResponseToVenueDetailEntity(it.response.venue)
        }
    }

    private fun convertDetailResponseToVenueDetailEntity(item: Venue): VenueDetailEntity {
        return VenueDetailEntity(
            item.id ,
            item.name ,
            item.location.lat ,
            item.location.lng ,
            getCompleteAddress(item.location.formattedAddress),
            if (item.categories.isNotEmpty()) item.categories[0].name else "",
            if (item.categories.isNotEmpty()) "${item.categories[0].icon.prefix}${item.categories[0].icon.suffix}" else "",
            "" , //server response no data for pictures
            item.likes.count
            )
    }

    private fun convertExploreResponseToVenueEntity(item: Item): VenueEntity {
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

    private fun getCompleteAddress(address: List<String>): String? {
        var result = ""
        address.forEach {
            result += "$it , "
        }
        return result
    }

}
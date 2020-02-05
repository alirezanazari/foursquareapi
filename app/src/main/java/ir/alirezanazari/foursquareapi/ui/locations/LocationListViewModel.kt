package ir.alirezanazari.foursquareapi.ui.locations

import io.reactivex.observers.DisposableSingleObserver
import ir.alirezanazari.data.providers.LocationProvider
import ir.alirezanazari.domain.entity.VenueEntity
import ir.alirezanazari.domain.entity.VenueInputParams
import ir.alirezanazari.domain.intractor.GetNearVenueUseCase
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Logger
import ir.alirezanazari.foursquareapi.internal.SingleLiveEvent
import ir.alirezanazari.foursquareapi.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationListViewModel(
    private val useCase: GetNearVenueUseCase,
    private val locationProvider: LocationProvider
) : BaseViewModel() {

    companion object{
        const val LOCATION_LIMIT_COUNT = 20
    }
    val responseListener = SingleLiveEvent<List<VenueEntity>>()
    var isLoadingData = false

    suspend fun getCurrentLocationLatlng(): String {
        return withContext(Dispatchers.IO) {
            return@withContext locationProvider.getLocationLatLng()
        }
    }

    fun getNearLocations(latLng: String, offset: Int) {
        isLoadingData = true
        if (offset == 0) setLoaderState(true)
        useCase.execute(object : DisposableSingleObserver<List<VenueEntity>>() {
            override fun onSuccess(response: List<VenueEntity>) {
                isLoadingData = false
                setLoaderState(false)
                responseListener.postValue(response)
                if (response.isEmpty()) errorVisibilityListener.postValue(true)
            }

            override fun onError(e: Throwable) {
                isLoadingData = false
                Logger.showLog(e.message)
                if (offset == 0) setLoaderState(false, isEffectRetry = true)
                errorListener.postValue(R.string.error_connection)
            }

        }, VenueInputParams(latLng, LOCATION_LIMIT_COUNT, offset))
    }

}

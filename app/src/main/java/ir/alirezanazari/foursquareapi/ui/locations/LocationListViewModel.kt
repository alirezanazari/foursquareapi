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

    private val LOCATION_LIMIT_COUNT = 20
    val responseListener = SingleLiveEvent<List<VenueEntity>>()

    suspend fun getCurrentLocationLatlng(): String {
        return withContext(Dispatchers.IO) {
            return@withContext locationProvider.getLocationLatLng()
        }
    }

    fun getNearLocations(latLng: String, offset: Int) {
        setLoaderState(true)
        useCase.execute(object : DisposableSingleObserver<List<VenueEntity>>() {
            override fun onSuccess(response: List<VenueEntity>) {
                setLoaderState(false)
                responseListener.postValue(response)
                if (response.isEmpty()) errorVisibilityListener.postValue(true)
            }

            override fun onError(e: Throwable) {
                Logger.showLog(e.message)
                setLoaderState(false, isEffectRetry = true)
                errorListener.postValue(R.string.error_connection)
            }

        }, VenueInputParams(latLng, LOCATION_LIMIT_COUNT, offset))
    }

}

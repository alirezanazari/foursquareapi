package ir.alirezanazari.foursquareapi.ui.locations

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ir.alirezanazari.data.db.dao.VenueDao
import ir.alirezanazari.data.db.entity.VenueDataEntity
import ir.alirezanazari.data.providers.LocationProvider
import ir.alirezanazari.domain.entity.VenueEntity
import ir.alirezanazari.domain.entity.VenueInputParams
import ir.alirezanazari.domain.intractor.GetNearVenueUseCase
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Logger
import ir.alirezanazari.foursquareapi.internal.SingleLiveEvent
import ir.alirezanazari.foursquareapi.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationListViewModel(
    private val useCase: GetNearVenueUseCase,
    private val locationProvider: LocationProvider ,
    private val db: VenueDao
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

    suspend fun isLocationChanged(lat: Double , lng: Double): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext locationProvider.isLocationChanged(lat , lng)
        }
    }

    fun getNearLocations(latLng: String, offset: Int) {
        isLoadingData = true
        if (offset == 0) {
            setLoaderState(true)
            clearDatabase()
        }
        useCase.execute(object : DisposableSingleObserver<List<VenueEntity>>() {
            override fun onSuccess(response: List<VenueEntity>) {
                handleVenueResult(response)
                if (offset == 0) saveDataToDb(response ,latLng)
            }

            override fun onError(e: Throwable) {
                isLoadingData = false
                Logger.showLog(e.message)
                if (offset == 0) setLoaderState(false, isEffectRetry = true)
                errorListener.postValue(R.string.error_connection)
            }

        }, VenueInputParams(latLng, LOCATION_LIMIT_COUNT, offset))
    }

    private fun handleVenueResult(response: List<VenueEntity>) {
        isLoadingData = false
        setLoaderState(false)
        responseListener.postValue(response)
        if (response.isEmpty()) errorVisibilityListener.postValue(true)
    }

    private fun saveDataToDb(response: List<VenueEntity> , userLatLng: String) {
        GlobalScope.launch(Dispatchers.IO) {
            response.map {
                val venue = VenueDataEntity(it.id , it.name , it.latitude , it.longitude , it.address , it.distance , it.categoryType , it.categoryIcon , it.picture , userLatLng)
                db.upsert(venue)
                venue
            }
        }
    }

    private fun clearDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            db.deleteAll()
        }
    }

    @SuppressLint("CheckResult")
    fun getLastLocationFromDb(onSuccess: (t: List<String>) -> Unit) {
        db.getLastLocation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( onSuccess , { Logger.showLog(it.message) })
    }

    @SuppressLint("CheckResult")
    fun getNearLocationsFromDb(lastLatlng: String , current: String) {
        db.getVenues(lastLatlng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                val venues = result.map {
                    VenueEntity(it.id , it.name , it.latitude , it.longitude , it.address , it.distance , it.categoryType , it.categoryIcon , it.picture)
                }
                handleVenueResult(venues)
            }, {
                Logger.showLog(it.message)
                getNearLocations(current , 0)
            })
    }

}

package ir.alirezanazari.foursquareapi.ui.details

import io.reactivex.observers.DisposableSingleObserver
import ir.alirezanazari.domain.entity.VenueDetailEntity
import ir.alirezanazari.domain.intractor.GetVenueUseCase
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Logger
import ir.alirezanazari.foursquareapi.internal.SingleLiveEvent
import ir.alirezanazari.foursquareapi.ui.BaseViewModel

class LocationDetailViewModel(
    private val useCase: GetVenueUseCase
) : BaseViewModel(useCase) {

    val responseListener = SingleLiveEvent<VenueDetailEntity>()

    fun getVenueById(id: String) {
        setLoaderState(true)
        useCase.execute(object : DisposableSingleObserver<VenueDetailEntity>() {
            override fun onSuccess(response: VenueDetailEntity) {
                setLoaderState(false)
                responseListener.postValue(response)
            }

            override fun onError(e: Throwable) {
                Logger.showLog(e.message)
                setLoaderState(false, isEffectRetry = true)
                errorListener.postValue(R.string.error_connection)
            }
        }, id)
    }
}

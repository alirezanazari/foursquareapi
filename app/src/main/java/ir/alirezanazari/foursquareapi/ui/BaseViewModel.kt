package ir.alirezanazari.foursquareapi.ui

import androidx.lifecycle.ViewModel
import ir.alirezanazari.domain.intractor.UseCase
import ir.alirezanazari.foursquareapi.internal.SingleLiveEvent


open class BaseViewModel(
    private val useCase: UseCase? = null
) : ViewModel() {

    val loaderVisibilityListener = SingleLiveEvent<Boolean>()
    val retryVisibilityListener = SingleLiveEvent<Boolean>()
    val errorVisibilityListener = SingleLiveEvent<Boolean>()
    val errorListener = SingleLiveEvent<String>()

    override fun onCleared() {
        useCase?.clearDisposable()
        super.onCleared()
    }

    fun setLoaderState(state: Boolean , isEffectRetry: Boolean){
        if (state){
            loaderVisibilityListener.value = true
            retryVisibilityListener.value = false
            errorVisibilityListener.value = false
        }else{
            loaderVisibilityListener.value = false
            errorVisibilityListener.value = true
            retryVisibilityListener.value = isEffectRetry
        }
    }

}
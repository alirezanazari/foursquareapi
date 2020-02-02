package ir.alirezanazari.foursquareapi.ui

import androidx.lifecycle.ViewModel
import ir.alirezanazari.domain.intractor.UseCase


open class BaseViewModel(
    private val useCase: UseCase? = null
) : ViewModel() {

    override fun onCleared() {
        useCase?.clearDisposable()
        super.onCleared()
    }

}
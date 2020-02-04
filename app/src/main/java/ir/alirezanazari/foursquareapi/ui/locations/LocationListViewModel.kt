package ir.alirezanazari.foursquareapi.ui.locations

import ir.alirezanazari.domain.intractor.GetNearVenueUseCase
import ir.alirezanazari.foursquareapi.ui.BaseViewModel

class LocationListViewModel(
    private val useCase: GetNearVenueUseCase
) : BaseViewModel() {
}

package ir.alirezanazari.foursquareapi.internal

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ir.alirezanazari.foursquareapi.ui.details.LocationDetailViewModel
import ir.alirezanazari.foursquareapi.ui.locations.LocationListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleDI = module {

    factory<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(get<Context>()) }

    viewModel { LocationListViewModel() }
    viewModel { LocationDetailViewModel() }
}
package ir.alirezanazari.foursquareapi.internal

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.alirezanazari.data.db.FourSquareDatabase
import ir.alirezanazari.data.net.ApiConfig
import ir.alirezanazari.data.net.NetworkDataManager
import ir.alirezanazari.data.net.NetworkDataManagerImpl
import ir.alirezanazari.data.net.RequestInterceptor
import ir.alirezanazari.data.providers.LocationProvider
import ir.alirezanazari.data.providers.LocationProviderImpl
import ir.alirezanazari.data.repository.VenueRepositoryImpl
import ir.alirezanazari.domain.intractor.GetNearVenueUseCase
import ir.alirezanazari.domain.intractor.GetVenueUseCase
import ir.alirezanazari.domain.repository.VenueRepository
import ir.alirezanazari.foursquareapi.ui.details.LocationDetailViewModel
import ir.alirezanazari.foursquareapi.ui.details.LocationPictureAdapter
import ir.alirezanazari.foursquareapi.ui.locations.LocationListViewModel
import ir.alirezanazari.foursquareapi.ui.locations.LocationsAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleDI = module {

    single { ImageLoader() }
    single { RequestInterceptor() }
    single { ApiConfig(get()) }
    single<NetworkDataManager> { NetworkDataManagerImpl(get()) }
    single { FourSquareDatabase(get()) }

    factory<VenueRepository> { VenueRepositoryImpl(get()) }
    factory<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(get<Context>()) }
    factory<LocationProvider> { LocationProviderImpl(get(), get()) }
    factory { GetNearVenueUseCase(get(), Schedulers.io(), AndroidSchedulers.mainThread()) }
    factory { GetVenueUseCase(get(), Schedulers.io(), AndroidSchedulers.mainThread()) }

    viewModel { LocationListViewModel(get(), get(), get<FourSquareDatabase>().getVenueDao()) }
    viewModel { LocationDetailViewModel(get()) }

    factory { LocationsAdapter(get()) }
    factory { LocationPictureAdapter(get()) }
}
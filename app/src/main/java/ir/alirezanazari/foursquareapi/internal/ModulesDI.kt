package ir.alirezanazari.foursquareapi.internal

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.dsl.module

val moduleDI = module {

    factory<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(get<Context>()) }

}
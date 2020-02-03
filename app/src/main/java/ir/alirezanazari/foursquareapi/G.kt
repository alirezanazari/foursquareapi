package ir.alirezanazari.foursquareapi

import android.app.Application
import ir.alirezanazari.foursquareapi.internal.moduleDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class G : Application() {

    override fun onCreate() {
        super.onCreate()

        //dependency injection
        startKoin {
            androidContext(this@G)
            modules(moduleDI)
        }
    }

}
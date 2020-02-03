package ir.alirezanazari.foursquareapi.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.LifecycleBoundLocationManager
import ir.alirezanazari.foursquareapi.internal.Navigator
import ir.alirezanazari.foursquareapi.ui.locations.LocationListFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val PERMISSION_ACCESS_COARSE_LOCATION_ID = 11
    private val fusedLocationProviderClient : FusedLocationProviderClient by inject()
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (hasLocationPermission()) {
            bindLocationManager()
            setupFragments()
        }else{
            requestLocationPermission()
        }

    }

    private fun setupFragments() {
        Navigator.openLocationsListFragment(supportFragmentManager)
    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(
            this ,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION) ,
            PERMISSION_ACCESS_COARSE_LOCATION_ID
        )
    }

    private fun hasLocationPermission() : Boolean{
        return ContextCompat.checkSelfPermission(this  ,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            PERMISSION_ACCESS_COARSE_LOCATION_ID ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    bindLocationManager()
                    setupFragments()
                }else {
                    Toast.makeText(this, R.string.location_permission_hint, Toast.LENGTH_LONG).show()
                    requestLocationPermission()
                }
            }
        }
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(
            this, fusedLocationProviderClient, locationCallback
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (fragment is LocationListFragment) {
                if (fragment.onBackPressed()) finish()
                return
            } else {
                finish()
                return
            }
        }
        super.onBackPressed()
    }
}

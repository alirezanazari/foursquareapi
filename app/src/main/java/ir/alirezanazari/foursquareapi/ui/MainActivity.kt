package ir.alirezanazari.foursquareapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Navigator
import ir.alirezanazari.foursquareapi.ui.locations.LocationListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragments()
    }

    private fun setupFragments() {
        Navigator.openLocationsListFragment(supportFragmentManager)
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

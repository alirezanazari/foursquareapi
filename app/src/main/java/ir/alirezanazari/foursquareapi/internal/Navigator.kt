package ir.alirezanazari.foursquareapi.internal

import androidx.fragment.app.FragmentManager
import ir.alirezanazari.foursquareapi.ui.details.LocationDetailFragment
import ir.alirezanazari.foursquareapi.ui.locations.LocationListFragment

class Navigator {

    companion object {

        fun openLocationsListFragment(fm: FragmentManager?) {
            NavigationUtil(fm, LocationListFragment()).setReplace(true).setPlayAnimation(false)
                .load()
        }

        fun openLocationDetailFragment(fm: FragmentManager?) {
            NavigationUtil(fm, LocationDetailFragment()).setReplace(true).load()
        }

    }

}
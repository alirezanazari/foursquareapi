package ir.alirezanazari.foursquareapi.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Logger
import ir.alirezanazari.foursquareapi.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LocationListFragment : BaseFragment() {

    private val viewModel: LocationListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentLocationAndFindVenues()
        setupListeners()
    }

    private fun setupListeners() {
        viewModel.responseListener.observe(viewLifecycleOwner, Observer {
            Logger.showLog("Received : ${it.size}")
        })
    }

    private fun getCurrentLocationAndFindVenues() {
        GlobalScope.launch(Dispatchers.Main) {
            val latlng = viewModel.getCurrentLocationLatlng()
            Logger.showLog(latlng)
            viewModel.getNearLocations(latlng, 0)
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}

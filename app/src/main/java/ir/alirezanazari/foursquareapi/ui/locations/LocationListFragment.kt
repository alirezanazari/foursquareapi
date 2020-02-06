package ir.alirezanazari.foursquareapi.ui.locations

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.domain.entity.VenueEntity
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Logger
import ir.alirezanazari.foursquareapi.ui.BaseFragment
import ir.alirezanazari.foursquareapi.ui.MainActivity.Companion.LOCATION_CHECKER_BROADCAST
import ir.alirezanazari.foursquareapi.ui.locations.LocationListViewModel.Companion.LOCATION_LIMIT_COUNT
import kotlinx.android.synthetic.main.location_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LocationListFragment : BaseFragment() {

    private val viewModel: LocationListViewModel by inject()
    private val mAdapter: LocationsAdapter by inject()
    private lateinit var mCurrentLatlng: String
    private var mOffset = 0
    private var isEndOfList: Boolean = false
    private lateinit var mLocationBroadcast: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecyclerView()
        setupLocationBroadcast()
        getCurrentLocationAndFindVenues()
    }

    private fun setupRecyclerView() {
        val lManager = LinearLayoutManager(rvLocations.context)
        rvLocations.apply {
            layoutManager = lManager
            adapter = mAdapter
        }

        var visibleItemCount: Int
        var totalItemCount: Int
        var pastItemCount: Int

        rvLocations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = lManager.childCount
                    totalItemCount = lManager.itemCount
                    pastItemCount = lManager.findFirstVisibleItemPosition()

                    if (!viewModel.isLoadingData && !isEndOfList) {
                        if ((visibleItemCount + pastItemCount) >= totalItemCount) {
                            viewModel.getNearLocations(mCurrentLatlng, mOffset)
                        }
                    }
                }
            }
        })
    }

    private fun setupListeners() {

        btnRetry.setOnClickListener {
            getCurrentLocationAndFindVenues()
        }

        viewModel.responseListener.observe(viewLifecycleOwner, Observer {
            handleVenusResponse(it)
        })

        viewModel.errorListener.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (mOffset == 0) tvNoData.setText(it)
                Logger.showToast(activity, it)
            }
        })

        viewModel.loaderVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                pbLoading.visibility = if (state) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                if (mOffset == 0) {
                    tvNoData.visibility = if (state) View.VISIBLE else View.GONE
                }
            }
        })

        viewModel.retryVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                btnRetry.visibility = if (state) View.VISIBLE else View.GONE
            }
        })
    }

    private fun handleVenusResponse(items: List<VenueEntity>) {
        if (mOffset != 0) {
            mAdapter.removeLoader()
        }

        mAdapter.setItems(items)
        mOffset = mAdapter.itemCount

        if (items.size == LOCATION_LIMIT_COUNT) {
            mAdapter.addLoader()
        } else {
            isEndOfList = true
        }

    }

    private fun getCurrentLocationAndFindVenues(isFromBroadcast: Boolean = false) {
        GlobalScope.launch(Dispatchers.Main) {
            mAdapter.clearItems()
          
            mOffset = 0
            isEndOfList = false
            viewModel.isLoadingData = false
            mCurrentLatlng = viewModel.getCurrentLocationLatlng()
            Logger.showLog(mCurrentLatlng)

            if (!isFromBroadcast) {
                viewModel.getLastLocationFromDb { getFindVenues(it) }
            }else{
                viewModel.getNearLocations(mCurrentLatlng, mOffset)
            }
        }
    }

    private fun getFindVenues(lastLocations: List<String>) {
        GlobalScope.launch(Dispatchers.Main) {
            if (lastLocations.isNotEmpty()) {
                val location = getConvertLocation(lastLocations[0])
                if (location != null) {
                    val isChanged = viewModel.isLocationChanged(location.first, location.second)
                    if (!isChanged){
                        viewModel.getNearLocationsFromDb(lastLocations[0] , mCurrentLatlng)
                        return@launch
                    }
                }
            }
            viewModel.getNearLocations(mCurrentLatlng, mOffset)
        }
    }

    private fun setupLocationBroadcast() {
        mLocationBroadcast = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                checkLocationAndUpdateIfNeeded()
            }
        }

        //register
        if (activity != null) {
            val intentFilter = IntentFilter(LOCATION_CHECKER_BROADCAST)
            LocalBroadcastManager.getInstance(activity!!)
                .registerReceiver(mLocationBroadcast, intentFilter)
        }
    }

    private fun checkLocationAndUpdateIfNeeded() {
        GlobalScope.launch(Dispatchers.Main) {
            val latlng = getConvertLocation(mCurrentLatlng) ?: return@launch
            val isChanged = viewModel.isLocationChanged(latlng.first , latlng.second)
            Logger.showLog("Location changed: $isChanged")
            if (isChanged) getCurrentLocationAndFindVenues(true)
        }
    }

    private fun getConvertLocation(location: String): Pair<Double , Double>? {
        return try {
            val latlng = location.split(",")
            Pair(latlng[0].toDouble() , latlng[1].toDouble())
        }catch (ex: Exception){
            null
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onDestroyView() {
        //unregister broadcast location
        if (activity != null) {
            LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(mLocationBroadcast)
        }
        super.onDestroyView()
    }
}

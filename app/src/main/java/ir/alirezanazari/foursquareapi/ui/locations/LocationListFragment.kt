package ir.alirezanazari.foursquareapi.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Logger
import ir.alirezanazari.foursquareapi.ui.BaseFragment
import kotlinx.android.synthetic.main.location_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LocationListFragment : BaseFragment() {

    private val viewModel: LocationListViewModel by inject()
    private val mAdapetr: LocationsAdapter by inject()

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
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvLocations.apply {
            layoutManager = LinearLayoutManager(rvLocations.context)
            adapter = mAdapetr
        }
    }

    private fun setupListeners() {
        viewModel.responseListener.observe(viewLifecycleOwner, Observer {
            mAdapetr.setItems(it)
        })

        viewModel.errorListener.observe(viewLifecycleOwner , Observer {
            it?.let {
                tvNoData.setText(it)
                Logger.showToast(activity , it)
            }
        })

        viewModel.loaderVisibilityListener.observe(viewLifecycleOwner , Observer {
            it?.let {state ->
                pbLoading.visibility = if (state) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorVisibilityListener.observe(viewLifecycleOwner , Observer {
            it?.let {state ->
                tvNoData.visibility = if (state) View.VISIBLE else View.GONE
            }
        })

        viewModel.retryVisibilityListener.observe(viewLifecycleOwner , Observer {
            it?.let {state ->
                btnRetry.visibility = if (state) View.VISIBLE else View.GONE
            }
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

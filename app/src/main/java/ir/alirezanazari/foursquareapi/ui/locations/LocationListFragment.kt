package ir.alirezanazari.foursquareapi.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.Logger
import ir.alirezanazari.foursquareapi.ui.BaseFragment
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
            mAdapter.clearItems()
            mOffset = 0
            isEndOfList = false
            viewModel.isLoadingData = false
            viewModel.getNearLocations(mCurrentLatlng, mOffset)
        }

        viewModel.responseListener.observe(viewLifecycleOwner, Observer {
            if (mOffset != 0) mAdapter.removeLoader()
            mAdapter.setItems(it)
            mOffset = mAdapter.itemCount
            if (it.size == LOCATION_LIMIT_COUNT) {
                mAdapter.addLoader()
            } else {
                isEndOfList = true
            }
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

    private fun getCurrentLocationAndFindVenues() {
        GlobalScope.launch(Dispatchers.Main) {
            mCurrentLatlng = viewModel.getCurrentLocationLatlng()
            Logger.showLog(mCurrentLatlng)
            viewModel.getNearLocations(mCurrentLatlng, 0)
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}

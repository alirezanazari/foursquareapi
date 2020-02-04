package ir.alirezanazari.foursquareapi.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.ui.BaseFragment
import org.koin.android.ext.android.inject

class LocationDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = LocationDetailFragment()
    }

    private val viewModel: LocationDetailViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}

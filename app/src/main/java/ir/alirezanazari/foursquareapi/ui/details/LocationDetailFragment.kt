package ir.alirezanazari.foursquareapi.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.ui.BaseFragment

class LocationDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = LocationDetailFragment()
    }

    private lateinit var viewModel: LocationDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}

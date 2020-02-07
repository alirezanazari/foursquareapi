package ir.alirezanazari.foursquareapi.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ir.alirezanazari.domain.entity.VenueDetailEntity
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.ui.BaseFragment
import kotlinx.android.synthetic.main.location_detail_fragment.*
import kotlinx.android.synthetic.main.location_list_fragment.btnRetry
import kotlinx.android.synthetic.main.location_list_fragment.pbLoading
import kotlinx.android.synthetic.main.location_list_fragment.tvNoData
import org.koin.android.ext.android.inject

class LocationDetailFragment : BaseFragment() {

    companion object {

        private const val FRAGMENT_VENUE_ID = "venueId"

        fun newInstance(id: String): LocationDetailFragment {
            val fragment = LocationDetailFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENT_VENUE_ID , id)
            fragment.arguments = bundle
            return fragment
        }

    }

    private lateinit var mVenueId: String
    private val viewModel: LocationDetailViewModel by inject()
    private val mPictureAdapter: LocationPictureAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        mVenueId = arguments?.getString(FRAGMENT_VENUE_ID) ?: return
        viewModel.getVenueById(mVenueId)
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            popupBackStack()
        }

        btnRetry.setOnClickListener {
            viewModel.getVenueById(mVenueId)
        }

        viewModel.responseListener.observe(viewLifecycleOwner, Observer {
            it?.let {
                handelResponse(it)
            }
        })

        viewModel.errorListener.observe(viewLifecycleOwner, Observer {
            it?.let {
                tvNoData.setText(it)
            }
        })

        viewModel.loaderVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                pbLoading.visibility = if (state) View.VISIBLE else View.GONE
                lytContent.visibility = if (!state) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                tvNoData.visibility = if (state) View.VISIBLE else View.GONE
                if (state) lytContent.visibility = View.GONE
            }
        })

        viewModel.retryVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                btnRetry.visibility = if (state) View.VISIBLE else View.GONE
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun handelResponse(data: VenueDetailEntity) {
        setupImageSlier(data.picture)
        tvName.text = data.name
        tvAddress.text = "${tvAddress.context.getString(R.string.address)} ${data.address}"
        tvLikes.text = "${data.likes} ${tvAddress.context.getString(R.string.liked)}"
    }

    private fun setupImageSlier(picture: String) {
        vpPictures.adapter = mPictureAdapter
        mPictureAdapter.setItems(listOf(picture , "")) //add empty for ui . server doesn't response any picture
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}

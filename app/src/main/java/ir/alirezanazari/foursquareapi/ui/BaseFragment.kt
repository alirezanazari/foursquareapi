package ir.alirezanazari.foursquareapi.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun popupBackStack(){
        activity?.onBackPressed()
    }

}
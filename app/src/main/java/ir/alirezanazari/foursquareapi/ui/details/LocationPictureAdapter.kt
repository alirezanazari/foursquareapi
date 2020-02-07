package ir.alirezanazari.foursquareapi.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.ImageLoader
import kotlinx.android.synthetic.main.view_picture.view.*


class LocationPictureAdapter(
    private val imageLoader: ImageLoader
) : PagerAdapter() {

    private val items = ArrayList<String>()

    fun setItems(data: List<String>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = LayoutInflater.from(container.context).inflate(R.layout.view_picture, container, false)
        layout.apply {
            imageLoader.load(ivPicture, items[position], R.drawable.place_holder)
        }
        container.addView(layout)
        return layout
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int = items.size

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View )
    }
}
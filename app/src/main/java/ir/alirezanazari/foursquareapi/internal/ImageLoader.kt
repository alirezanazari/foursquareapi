package ir.alirezanazari.foursquareapi.internal

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class ImageLoader {

    fun load(iv: ImageView, url: String, holder: Int) {
        Glide.with(iv.context)
            .load(url)
            .placeholder(holder)
            .error(holder)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(iv)

    }
}
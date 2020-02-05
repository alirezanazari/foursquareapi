package ir.alirezanazari.foursquareapi.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.domain.entity.VenueEntity
import ir.alirezanazari.foursquareapi.R
import ir.alirezanazari.foursquareapi.internal.ImageLoader
import kotlinx.android.synthetic.main.row_location_list.view.*


class LocationsAdapter(
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<VenueEntity?>()

    fun setItems(venues: List<VenueEntity>) {
        items.addAll(venues)
        notifyDataSetChanged()
    }

    fun addLoader(){
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoader(){
        if (items.size == 0) return
        val item = items[items.size - 1]
        if (item == null){
            items.remove(item)
            notifyItemRemoved(items.size -1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null)
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            LocationsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_location_list, parent, false))
        else
            LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_loader, parent, false))

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoaderViewHolder){
            holder.bind()
        }else if (holder is LocationsViewHolder){
            if (items[position] != null) holder.bind(items[position]!!)
        }
    }

    inner class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: VenueEntity) {
            itemView.apply {
                tvName.text = item.name
                tvAddress.text = if (item.address.isNullOrEmpty()) tvAddress.context.getString(R.string.no_address) else item.address
                tvDistance.text = "${item.distance / 1000} ${tvDistance.context.getString(R.string.km)}"
                tvType.text = item.categoryType
                //Note: Image are wrong some has picture but url doesn't show any picture in browser
                imageLoader.load(ivPicture, item.pictures , R.drawable.place_holder)
                imageLoader.load(icType, item.categoryIcon , R.drawable.ic_type)
            }
        }

    }

    inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(){
            itemView.apply {

            }
        }
    }
}
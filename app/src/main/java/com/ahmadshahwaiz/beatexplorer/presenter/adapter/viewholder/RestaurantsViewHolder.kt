package com.ahmadshahwaiz.beatexplorer.presenter.adapter.viewholder

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ahmadshahwaiz.beatexplorer.R
import com.ahmadshahwaiz.beatexplorer.databinding.ViewholderVenuesBinding
import com.ahmadshahwaiz.beatexplorer.presenter.adapter.callback.RestaurantCallback
import com.ahmadshahwaiz.beatexplorer.presenter.viewmodel.MapsViewModel
import com.ahmadshahwaiz.core.data.model.nearbyvenue.Venues
import com.bumptech.glide.Glide
import java.util.ArrayList

class RestaurantsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var viewHolderVenuesBinding: ViewholderVenuesBinding? = null

    init {
        viewHolderVenuesBinding = ViewholderVenuesBinding.bind(view)
    }
    fun bindRestaurants(context: Context, mapsViewModel: MapsViewModel, restaurantCallback: RestaurantCallback, venues: Venues, dataSource: ArrayList<Venues>){
        viewHolderVenuesBinding?.venueContainer?.setOnClickListener {
            mapsViewModel.removeSelectedVenueUseCase(venues, dataSource, restaurantCallback, adapterPosition)
        }
        viewHolderVenuesBinding?.labelTextView?.text = venues.name
        viewHolderVenuesBinding?.categoryIcon?.contentDescription = venues.name

        if(venues.categories?.isNotEmpty() == true) {
            val categoryIcon = venues.categories?.get(0)?.icon?.prefix+"32"+venues.categories?.get(0)?.icon?.suffix
            viewHolderVenuesBinding?.categoryIcon?.let {
                Glide.with(context)
                    .load(categoryIcon)
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .into(it)
            }
            viewHolderVenuesBinding?.valueTextView?.text = venues.categories?.get(0)?.name
        }
    }
}

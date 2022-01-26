package com.ahmadshahwaiz.beatexplorer.presenter.adapter

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadshahwaiz.beatexplorer.R
import com.ahmadshahwaiz.beatexplorer.presenter.adapter.callback.RestaurantCallback
import com.ahmadshahwaiz.beatexplorer.presenter.adapter.viewholder.RestaurantsViewHolder
import com.ahmadshahwaiz.beatexplorer.presenter.viewmodel.MapsViewModel
import com.ahmadshahwaiz.core.data.model.nearbyvenue.Venues
import java.util.*

class RestaurantAdapter(
    private var context: Context,
    private var mapsViewModel: MapsViewModel,
    private var restaurantCallback: RestaurantCallback,
    private var dataSource: ArrayList<Venues>,
    private var layoutInflater: LayoutInflater
) : RecyclerView.Adapter<RestaurantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val view = layoutInflater.inflate(R.layout.viewholder_venues, parent, false)
        return RestaurantsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.bindRestaurants(context, mapsViewModel, restaurantCallback, dataSource[position], dataSource)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}


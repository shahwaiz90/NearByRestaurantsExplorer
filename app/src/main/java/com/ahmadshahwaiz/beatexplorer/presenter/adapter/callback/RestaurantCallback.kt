package com.ahmadshahwaiz.beatexplorer.presenter.adapter.callback

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.ahmadshahwaiz.core.data.model.nearbyvenue.Venues

interface RestaurantCallback{
    fun onVenueClick(venues: Venues, position: Int, size: Int)
    fun setSelectedVenue(venues: Venues?)
    fun getSelectedVenue(): Venues?
}
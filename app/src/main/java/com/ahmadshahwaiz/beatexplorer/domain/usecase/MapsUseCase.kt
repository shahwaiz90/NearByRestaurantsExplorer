package com.ahmadshahwaiz.beatexplorer.domain.usecase

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.ahmadshahwaiz.beatexplorer.data.repo.MapsRepository
import com.ahmadshahwaiz.beatexplorer.data.wrapper.ResultWrapper
import com.ahmadshahwaiz.beatexplorer.presenter.adapter.callback.RestaurantCallback
import com.ahmadshahwaiz.core.data.model.nearbyvenue.Venues
import java.util.ArrayList
import javax.inject.Inject

class MapsUseCase @Inject constructor(
    private val mapsRepository: MapsRepository
){
    suspend fun findNearByRestaurants(latLong: String): ResultWrapper<*> {
        return mapsRepository.findNearByRestaurants(latLong)
    }

    fun removeSelectedVenueUseCase(venues: Venues, dataSource: ArrayList<Venues>, restaurantCallback: RestaurantCallback, adapterPosition: Int) {
        dataSource.removeAt(adapterPosition)
        restaurantCallback.getSelectedVenue()?.let {
            dataSource.add(it)
        }
        restaurantCallback.setSelectedVenue(venues)
        restaurantCallback.onVenueClick(venues, adapterPosition, dataSource.size)
    }
}
package com.ahmadshahwaiz.beatexplorer.presenter.viewmodel

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahmadshahwaiz.beatexplorer.data.di.ApplicationModule
import com.ahmadshahwaiz.beatexplorer.data.wrapper.ResultWrapper
import com.ahmadshahwaiz.beatexplorer.domain.constants.MapType
import com.ahmadshahwaiz.beatexplorer.domain.usecase.MapsUseCase
import com.ahmadshahwaiz.beatexplorer.presenter.adapter.callback.RestaurantCallback
import com.ahmadshahwaiz.core.data.model.nearbyvenue.NearByVenueResponse
import com.ahmadshahwaiz.core.data.model.nearbyvenue.Venues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val mapsUseCase: MapsUseCase,
    @ApplicationModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher) : BaseViewModel() {

    private var currentMapType = MutableLiveData<MapType>()
    private val errorMessage = MutableLiveData<String>()
    private val nearByVenueObserver = MutableLiveData<NearByVenueResponse>()

    fun nearByVenueObserver(): LiveData<NearByVenueResponse>{
        return nearByVenueObserver
    }

    fun errorMessageObserver(): LiveData<String>{
        return errorMessage
    }

    fun currentMapModelObserver(): LiveData<MapType>{
        return currentMapType
    }

    fun setCurrentMapMode(mapType: MapType){
        currentMapType.postValue(mapType)
    }

    fun getCurrentMapMode(): MapType? {
        if(currentMapType.value == null){
            currentMapType.postValue(MapType.EXPLORER)
        }
        return currentMapType.value
    }

    fun findNearByRestaurants(latLong: String){
        kotlin.runCatching {
            viewModelScope.launch(ioDispatcher) {
                mapsUseCase.findNearByRestaurants(latLong).let { response ->
                    when (response) {
                        is ResultWrapper.Success<*> -> {
                            nearByVenueObserver.postValue(response.data as NearByVenueResponse?)
                        }
                        is ResultWrapper.Failure -> {
                            errorMessage.postValue(response.exception?.message)
                        }
                    }
                }
            }
        }
    }

    fun removeSelectedVenueUseCase(venues: Venues, dataSource: ArrayList<Venues>, restaurantCallback: RestaurantCallback, adapterPosition: Int) {
        kotlin.runCatching {
            mapsUseCase.removeSelectedVenueUseCase(venues, dataSource, restaurantCallback, adapterPosition)
        }
    }
}
package com.ahmadshahwaiz.network

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.ahmadshahwaiz.core.data.model.nearbyvenue.NearByVenueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/venues/search")
    suspend fun findNearByRestaurants(@Query(NetworkConstants.Params.CLIENT_ID) clientId: String, @Query(NetworkConstants.Params.CLIENT_SECRET) clientSecret: String, @Query(NetworkConstants.Params.LATITUDE_LONGITUDE) latLong: String, @Query(NetworkConstants.Params.VERSION) version: String, @Query(NetworkConstants.Params.CATEGORY_ID) categoryId: String): NearByVenueResponse

}
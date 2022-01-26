package com.ahmadshahwaiz.core.data.model.nearbyvenue

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.google.gson.annotations.SerializedName

data class NearByVenueResponse (

    @SerializedName("meta") val meta : Meta,
    @SerializedName("response") val venueResponse : VenueResponse
)
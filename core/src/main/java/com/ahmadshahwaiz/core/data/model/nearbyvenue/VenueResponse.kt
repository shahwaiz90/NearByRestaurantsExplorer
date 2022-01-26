package com.ahmadshahwaiz.core.data.model.nearbyvenue

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.google.gson.annotations.SerializedName

data class VenueResponse (

    @SerializedName("venues") val venues : ArrayList<Venues>,
    @SerializedName("confident") val confident : Boolean
)
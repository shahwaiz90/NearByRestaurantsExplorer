package com.ahmadshahwaiz.core.data.model.nearbyvenue

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.google.gson.annotations.SerializedName

data class Location (

    @SerializedName("address") val address : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("labeledLatLngs") val labeledLatLngs : List<LabeledLatLngs>,
    @SerializedName("distance") val distance : String,
    @SerializedName("postalCode") val postalCode : String,
    @SerializedName("cc") val cc : String,
    @SerializedName("city") val city : String,
    @SerializedName("state") val state : String,
    @SerializedName("country") val country : String,
    @SerializedName("formattedAddress") val formattedAddress : List<String>
)
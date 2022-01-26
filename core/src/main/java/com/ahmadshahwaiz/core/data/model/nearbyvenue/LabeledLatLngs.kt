package com.ahmadshahwaiz.core.data.model.nearbyvenue

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.google.gson.annotations.SerializedName

data class LabeledLatLngs (

    @SerializedName("label") val label : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double
)
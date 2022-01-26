package com.ahmadshahwaiz.core.data.model.nearbyvenue

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.google.gson.annotations.SerializedName

data class Icon (

    @SerializedName("prefix") val prefix : String,
    @SerializedName("suffix") val suffix : String
)
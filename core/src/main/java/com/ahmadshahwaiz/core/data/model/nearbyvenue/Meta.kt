package com.ahmadshahwaiz.core.data.model.nearbyvenue

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.google.gson.annotations.SerializedName


data class Meta (

    @SerializedName("code") val code : Int,
    @SerializedName("requestId") val requestId : String
)
package com.ahmadshahwaiz.core.data.model.nearbyvenue

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.google.gson.annotations.SerializedName

data class Categories (

    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("pluralName") val pluralName : String,
    @SerializedName("shortName") val shortName : String,
    @SerializedName("icon") val icon : Icon,
    @SerializedName("primary") val primary : Boolean
)
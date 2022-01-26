package com.ahmadshahwaiz.network

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
class NetworkConstants {
    object Params {
        const val CLIENT_ID = "client_id"
        const val CLIENT_SECRET = "client_secret"
        const val LATITUDE_LONGITUDE = "ll"
        const val VERSION="v"
        const val CATEGORY_ID="categoryId"
    }

    enum class HttpResponse(var code: Int) {
        OK(200)
    }
}
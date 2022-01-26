package com.ahmadshahwaiz.network

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import okhttp3.OkHttpClient
import javax.inject.Inject

class NetworkApiSetup private constructor(builder: Builder) {
    val baseUrl: String = builder.getBaseUrl()
    val okHttpClient: OkHttpClient = builder.getOkHttpClient()

    class Builder @Inject constructor() {
        private var baseUrl: String = ""
        private lateinit var okHttpClient: OkHttpClient
        fun setBaseUrl(baseUrl: String): Builder {
            return apply {
                this.baseUrl = baseUrl
            }
        }
        fun setOkHttpClient(okHttpClient: OkHttpClient): Builder {
            return apply {
                this.okHttpClient = okHttpClient
            }
        }
        fun getOkHttpClient(): OkHttpClient {
            return this.okHttpClient
        }
        fun getBaseUrl(): String {
            return this.baseUrl
        }
        fun build(): NetworkApiSetup {
            return NetworkApiSetup(this)
        }
    }
}
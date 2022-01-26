package com.ahmadshahwaiz.beatexplorer.data.global

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.ahmadshahwaiz.beatexplorer.BuildConfig

class AppConfiguration{
    val logsEnabled: Boolean = BuildConfig.LOGS_ENABLED
    val baseURL = BuildConfig.BASE_URL
}
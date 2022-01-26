package com.ahmadshahwaiz.beatexplorer.data.global

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
object Keys {
    init {
        System.loadLibrary("beatexplorer")
    }
    external fun fourSquareClientId(): String
    external fun fourSquareClientSecret(): String
}
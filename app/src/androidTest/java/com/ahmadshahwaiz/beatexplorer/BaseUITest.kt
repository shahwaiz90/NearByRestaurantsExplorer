package com.ahmadshahwaiz.beatexplorer


import org.junit.Ignore

const val twoSeconds: Long = 2000
const val tenSeconds: Long = 10000

// Note: it is better to turn off the animations from the developer options of the device
// For more information please check: https://developer.android.com/training/testing/espresso/setup.html#set-up-environment

@Ignore("this is a helper class, it is not for testing")
open class BaseUiTest {

    @Ignore
    fun sleep(milliseconds: Long) {
        try {
            Thread.sleep(milliseconds)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
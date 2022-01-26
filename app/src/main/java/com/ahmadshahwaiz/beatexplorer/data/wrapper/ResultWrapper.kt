package com.ahmadshahwaiz.beatexplorer.data.wrapper

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.ahmadshahwaiz.beatexplorer.domain.errorhandling.BaseException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T?) : ResultWrapper<T>()
    data class Failure(val exception: BaseException? = null) : ResultWrapper<Nothing>()
}
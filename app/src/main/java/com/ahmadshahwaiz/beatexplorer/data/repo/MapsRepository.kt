package com.ahmadshahwaiz.beatexplorer.data.repo

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.ahmadshahwaiz.beatexplorer.data.global.Keys
import com.ahmadshahwaiz.beatexplorer.data.wrapper.ResultWrapper
import com.ahmadshahwaiz.beatexplorer.domain.constants.Constants.Companion.FOOD_CATEGORY
import com.ahmadshahwaiz.beatexplorer.domain.errorhandling.GenericException
import com.ahmadshahwaiz.beatexplorer.domain.errorhandling.SocketTimeOutException
import com.ahmadshahwaiz.network.ApiService
import com.ahmadshahwaiz.network.NetworkConstants
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MapsRepository @Inject constructor(
    private val apiService: ApiService,
){
    suspend fun findNearByRestaurants(latLong:  String): ResultWrapper<*> {
        kotlin.runCatching {
            apiService.findNearByRestaurants(Keys.fourSquareClientId(), Keys.fourSquareClientSecret(), latLong, getCurrentDateForVersion(), FOOD_CATEGORY).let {
                // to check Http header code, we can implement CustomCallAdapter and handle that logic there.
                // for now ill just check the code coming from the response instead of header.
                return if (it.meta.code == NetworkConstants.HttpResponse.OK.code) {
                    ResultWrapper.Success(it)
                } else {
                    ResultWrapper.Failure(GenericException())
                }
            }
        }
        return ResultWrapper.Failure(SocketTimeOutException())
    }

    private fun getCurrentDateForVersion(): String {
        val versionDateFormatterPattern = "yyyyMMDD"
        // locale must always be english, since we need to send date digits for english language to server as version.
        // some people miss this, it could be potential bug on another language.
        val versionDateFormatter = SimpleDateFormat(versionDateFormatterPattern, Locale.ENGLISH)
        return versionDateFormatter.format(Date())
    }
}
package com.ahmadshahwaiz.beatexplorer.domain.errorhandling

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
class SocketTimeOutException: BaseException() {
    override var message: String = "Something went wrong, please check your internet and try again later."
}
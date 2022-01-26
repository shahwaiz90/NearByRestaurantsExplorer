package com.ahmadshahwaiz.beatexplorer.domain.errorhandling

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
class GenericException: BaseException() {
    override var message: String = "Its not you, its us! Please try again after a while. :)"
}
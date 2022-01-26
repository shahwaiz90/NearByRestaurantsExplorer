package com.ahmadshahwaiz.beatexplorer.domain.errorhandling

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
abstract class BaseException : RuntimeException(){
    override var message: String = "Its not you, its us! Please try again after a while. :)"
}
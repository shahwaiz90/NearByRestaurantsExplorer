package com.ahmadshahwaiz.beatexplorer.presenter.viewmodel

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import androidx.lifecycle.ViewModel
import com.ahmadshahwaiz.beatexplorer.data.di.ApplicationModule
import com.ahmadshahwaiz.beatexplorer.domain.usecase.MapsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class BaseViewModel : ViewModel()
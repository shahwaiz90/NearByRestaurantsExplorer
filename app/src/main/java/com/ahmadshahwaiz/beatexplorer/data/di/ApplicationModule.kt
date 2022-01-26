package com.ahmadshahwaiz.beatexplorer.data.di

/**
 * @author Ahmad Shahwaiz https://www.linkedin.com/in/ahmadshahwaiz/
 */
import com.ahmadshahwaiz.beatexplorer.data.global.AppConfiguration
import com.ahmadshahwaiz.beatexplorer.data.repo.MapsRepository
import com.ahmadshahwaiz.beatexplorer.domain.usecase.MapsUseCase
import com.ahmadshahwaiz.network.ApiService
import com.ahmadshahwaiz.network.BuildConfig
import com.ahmadshahwaiz.network.NetworkApiSetup
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

    @BaseUrl
    @Singleton
    @Provides
    fun provideBaseUrl() = com.ahmadshahwaiz.beatexplorer.BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideAppConfiguration() : AppConfiguration = AppConfiguration()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(appConfiguration: AppConfiguration): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (appConfiguration.logsEnabled) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun providesOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkApi(@BaseUrl baseUrl: String, okHttpClient: OkHttpClient): NetworkApiSetup {
        return NetworkApiSetup.Builder()
            .setBaseUrl(baseUrl)
            .setOkHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(networkApiSetup: NetworkApiSetup): ApiService {
        return Retrofit.Builder()
            .baseUrl(networkApiSetup.baseUrl)
            .client(networkApiSetup.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    fun providesMapsUseCase(mapsRepository: MapsRepository): MapsUseCase{
        return MapsUseCase(mapsRepository)
    }

    @Singleton
    fun providesMapsRepository(apiService: ApiService): MapsRepository {
        return MapsRepository(apiService)
    }

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Singleton
    @Provides
    fun provideDispatcher(): Dispatcher {
        return Dispatcher()
    }

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}